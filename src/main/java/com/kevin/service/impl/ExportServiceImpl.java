package com.kevin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.entity.Answer;
import com.kevin.entity.AnswerSheet;
import com.kevin.entity.Question;
import com.kevin.entity.User;
import com.kevin.mapper.AnswerMapper;
import com.kevin.mapper.AnswerSheetMapper;
import com.kevin.mapper.QuestionMapper;
import com.kevin.mapper.UserMapper;
import com.kevin.service.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExportServiceImpl implements ExportService {

    @Autowired
    private AnswerSheetMapper answerSheetMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public void exportToExcel(Long questionnaireId, HttpServletResponse response) {
        List<AnswerSheet> data = answerSheetMapper.selectList(null);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=survey.xlsx");
        try {
            EasyExcel.write(response.getOutputStream(), AnswerSheet.class).sheet("问卷结果").doWrite(data);
        } catch (IOException e) {
            log.info("导出失败",e);
        }
    }

    @Override
    public void exportAnswersToExcel(HttpServletResponse response) {
        try {
            // 1. 查询所有答卷
            List<AnswerSheet> sheets = answerSheetMapper.selectList(null);
            // 2. 查询所有题目（按sort_order排序）
            List<Question> questions = questionMapper.selectList(null);
            questions = questions.stream().sorted(Comparator.comparing(Question::getSortOrder)).toList();
            // 3. 构建表头
            List<List<String>> head = new ArrayList<>();
            head.add(Collections.singletonList("用户名"));
            for (Question q : questions) {
                head.add(Collections.singletonList(q.getContent()));
            }
            // 4. 构建数据
            List<List<String>> data = new ArrayList<>();
            for (AnswerSheet sheet : sheets) {
                List<String> row = new ArrayList<>();
                // 用户名（脱敏手机号）
                String username = "";
                if (sheet.getUserId() != null) {
                    User user = userMapper.selectById(sheet.getUserId());
                    if (user != null && user.getPhone() != null && user.getPhone().length() == 11) {
                        username = user.getPhone().substring(0, 3) + "****" + user.getPhone().substring(7);
                    }
                }
                row.add(username);
                // 查询该答卷所有答案
                List<Answer> answers = answerMapper.selectList(
                    new QueryWrapper<Answer>()
                        .eq("answer_sheet_id", sheet.getId())
                        .eq("is_deleted", 0)
                );

                Map<Long, List<Answer>> answerMap = answers.stream().collect(Collectors.groupingBy(Answer::getQuestionId));
                // 按题目顺序填充
                for (Question q : questions) {
                    List<Answer> ansList = answerMap.get(q.getId());
                    if (ansList == null || ansList.isEmpty()) {
                        row.add("");
                    } else {
                        // 多选题拼接
                        StringBuilder sb = new StringBuilder();
                        for (Answer ans : ansList) {
                            if (ans.getTextAnswer() != null) {
                                sb.append(ans.getTextAnswer());
                            } else if (ans.getNumberAnswer() != null) {
                                sb.append(ans.getNumberAnswer().toString());
                            } else if (ans.getOptionId() != null) {
                                sb.append(ans.getOptionId().toString());
                            }
                        }
                        row.add(sb.toString());
                    }
                }
                data.add(row);
            }
            // 5. 导出
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=answers.xlsx");
            EasyExcel.write(response.getOutputStream())
                .head(head)
                .sheet("答卷数据")
                .doWrite(data);
        } catch (Exception e) {
            log.info("导出答卷失败", e);
        }
    }
}
