package com.kevin.service.impl;

import com.kevin.dto.AnswerDTO;
import com.kevin.dto.AnswerSheetDTO;
import com.kevin.entity.Answer;
import com.kevin.entity.AnswerSheet;
import com.kevin.mapper.AnswerMapper;
import com.kevin.mapper.AnswerSheetMapper;
import com.kevin.service.FillService;
import com.kevin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class FillServiceImpl implements FillService {

    @Autowired
    private AnswerSheetMapper answerSheetMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionService questionService;

    @Override
    public void submit(Long questionnaireId, AnswerSheet answerSheet) {

        answerSheet.setQuestionnaireId(questionnaireId);

        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        answerSheet.setSubmitTime(date);

        answerSheetMapper.insert(answerSheet);
        // 这里可以继续插入Answer表
    }

    @Override
    public void saveAnswerSheet(AnswerSheetDTO dto) {
        // 1. 保存答卷主表
        AnswerSheet sheet = new AnswerSheet();
        sheet.setQuestionnaireId(dto.getQuestionnaireId());
        sheet.setSubmitTime(new Date());
        answerSheetMapper.insert(sheet);
        // 2. 保存每个答案
        for (AnswerDTO ans : dto.getAnswers()) {
            Object value = ans.getAnswer();
            // 跳过未填写的答案
            if (value == null) continue;
            if (value instanceof String && ((String) value).trim().isEmpty()) continue;
            if (value instanceof java.util.List && ((java.util.List<?>) value).isEmpty()) continue;
            String type = questionService.getTypeById(ans.getQuestionId());
            if ("SINGLE_CHOICE".equals(type)) {
                // 单选题，answer为字符串或数字
                Answer answer = new Answer();
                answer.setAnswerSheetId(sheet.getId());
                answer.setQuestionId(ans.getQuestionId());
                Long optionId = parseOptionId(value);
                answer.setOptionId(optionId);
                answerMapper.insert(answer);
            } else if ("MULTIPLE_CHOICE".equals(type)) {
                // 多选题，answer为数组
                if (value instanceof java.util.List<?>) {
                    java.util.List<?> list = (java.util.List<?>) value;
                    for (Object obj : list) {
                        Answer answer = new Answer();
                        answer.setAnswerSheetId(sheet.getId());
                        answer.setQuestionId(ans.getQuestionId());
                        answer.setOptionId(parseOptionId(obj));
                        answerMapper.insert(answer);
                    }
                } else {
                    // 兼容前端误传单个值
                    Answer answer = new Answer();
                    answer.setAnswerSheetId(sheet.getId());
                    answer.setQuestionId(ans.getQuestionId());
                    answer.setOptionId(parseOptionId(value));
                    answerMapper.insert(answer);
                }
            } else if ("NUMBER".equals(type)) {
                // 数字题
                Answer answer = new Answer();
                answer.setAnswerSheetId(sheet.getId());
                answer.setQuestionId(ans.getQuestionId());
                if (value != null) {
                    try {
                        answer.setNumberAnswer(new java.math.BigDecimal(value.toString()));
                    } catch (Exception e) {
                        answer.setNumberAnswer(null);
                    }
                }
                answerMapper.insert(answer);
            } else if ("TEXT".equals(type)) {
                // 填空题
                Answer answer = new Answer();
                answer.setAnswerSheetId(sheet.getId());
                answer.setQuestionId(ans.getQuestionId());
                answer.setTextAnswer(value != null ? value.toString() : null);
                answerMapper.insert(answer);
            } else {
                // 其它类型直接toString存textAnswer
                Answer answer = new Answer();
                answer.setAnswerSheetId(sheet.getId());
                answer.setQuestionId(ans.getQuestionId());
                answer.setTextAnswer(value != null ? value.toString() : null);
                answerMapper.insert(answer);
            }
        }
    }

    private Long parseOptionId(Object answer) {
        if (answer == null) return null;
        if (answer instanceof Number) return ((Number) answer).longValue();
        try { return Long.parseLong(answer.toString()); } catch (Exception e) { return null; }
    }

    private String parseStringList(Object answer) {
        if (answer instanceof java.util.List<?>) {
            java.util.List<?> list = (java.util.List<?>) answer;
            java.util.List<String> result = new java.util.ArrayList<>();
            for (Object obj : list) {
                result.add(obj == null ? "" : obj.toString());
            }
            return String.join(",", result);
        }
        // 如果前端误传了单个字符串，也兼容
        return answer != null ? answer.toString() : "";
    }

}
