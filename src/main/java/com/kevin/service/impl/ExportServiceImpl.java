package com.kevin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.entity.Answer;
import com.kevin.entity.AnswerSheet;
import com.kevin.entity.Question;
import com.kevin.entity.QuestionOption;
import com.kevin.entity.User;
import com.kevin.mapper.AnswerMapper;
import com.kevin.mapper.AnswerSheetMapper;
import com.kevin.mapper.QuestionMapper;
import com.kevin.mapper.UserMapper;
import com.kevin.service.ExportService;
import com.kevin.service.QuestionOptionService;
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
    @Autowired
    private QuestionOptionService questionOptionService;

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

            // 3. 批量查询所有选项，建立ID到字母的映射缓存（性能优化）
            Map<Long, String> optionIdToLetterMap = buildOptionIdToLetterMap();

            // 4. 构建表头
            List<List<String>> head = new ArrayList<>();
            head.add(Collections.singletonList("用户名"));
            for (Question q : questions) {
                head.add(Collections.singletonList(q.getContent()));
            }

            // 5. 构建数据
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

                        // 多选题拼接 - 使用缓存的映射
                        StringBuilder sb = getStringBuilderWithCache(ansList, optionIdToLetterMap);
                        row.add(sb.toString());
                    }
                }
                data.add(row);
            }
            // 6. 导出
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

    private StringBuilder getStringBuilder(List<Answer> ansList) {
        StringBuilder sb = new StringBuilder();
        for (Answer ans : ansList) {
            if (ans.getTextAnswer() != null) {
                sb.append(ans.getTextAnswer());
            } else if (ans.getNumberAnswer() != null) {
                sb.append(ans.getNumberAnswer());
            } else if (ans.getOptionId() != null) {
                // 将选项ID转换为字母标识
                String letterOption = convertOptionIdToLetter(ans.getOptionId().toString());
                sb.append(letterOption);
            }
        }
        return sb;
    }

    /**
     * 将选项ID转换为字母标识
     * @param optionId 选项ID，可能是单个ID或多个ID（用逗号分隔）
     * @return 字母标识，如"A"或"A,C"
     */
    private String convertOptionIdToLetter(String optionId) {
        if (optionId == null || optionId.trim().isEmpty()) {
            return "";
        }
        
        // 如果是多个选项（多选题）
        if (optionId.contains(",")) {
            String[] ids = optionId.split(",");
            return Arrays.stream(ids)
                .map(this::convertSingleOptionIdToLetter)
                .collect(Collectors.joining(","));
        }
        
        // 单个选项
        return convertSingleOptionIdToLetter(optionId);
    }

    /**
     * 批量构建选项ID到字母的映射缓存
     * @return 选项ID到字母的映射Map
     */
    private Map<Long, String> buildOptionIdToLetterMap() {
        Map<Long, String> optionIdToLetterMap = new HashMap<>();
        
        // 批量查询所有选项
        List<QuestionOption> allOptions = questionOptionService.list();
        
        for (QuestionOption option : allOptions) {
            String letter = convertOptionIdToLetterFromContent(option);
            optionIdToLetterMap.put(option.getId(), letter);
        }
        
        return optionIdToLetterMap;
    }

    /**
     * 从选项内容中提取字母标识
     * @param option 选项对象
     * @return 字母标识
     */
    private String convertOptionIdToLetterFromContent(QuestionOption option) {
        if (option != null && option.getContent() != null) {
            // 如果选项内容包含字母标识（如"A. 旅游"），提取字母部分
            if (option.getContent().matches("^[A-Z]\\..*")) {
                return option.getContent().substring(0, 1); // 返回"A"
            }
        }
        
        // 如果没有找到或内容格式不对，使用数字转字母的方式
        Long id = option.getId();
        if (id != null && id >= 1 && id <= 26) {
            return String.valueOf((char)('A' + id.intValue() - 1));
        }
        
        return id != null ? id.toString() : "";
    }

    /**
     * 使用缓存的映射构建答案字符串
     * @param ansList 答案列表
     * @param optionIdToLetterMap 选项ID到字母的映射缓存
     * @return 答案字符串
     */
    private StringBuilder getStringBuilderWithCache(List<Answer> ansList, Map<Long, String> optionIdToLetterMap) {
        StringBuilder sb = new StringBuilder();
        for (Answer ans : ansList) {
            if (ans.getTextAnswer() != null) {
                sb.append(ans.getTextAnswer());
            } else if (ans.getNumberAnswer() != null) {
                sb.append(ans.getNumberAnswer());
            } else if (ans.getOptionId() != null) {
                // 从缓存中获取字母标识
                String letterOption = optionIdToLetterMap.getOrDefault(ans.getOptionId(), ans.getOptionId().toString());
                sb.append(letterOption);
            }
        }
        return sb;
    }

    /**
     * 将单个选项ID转换为字母标识
     * @param optionId 单个选项ID
     * @return 字母标识
     */
    private String convertSingleOptionIdToLetter(String optionId) {
        try {
            // 先尝试通过数据库查询获取选项的字母标识
            Long id = Long.parseLong(optionId.trim());
            QuestionOption option = questionOptionService.getById(id);
            if (option != null) {
                // 如果选项内容包含字母标识（如"A. 旅游"），提取字母部分
                String content = option.getContent();
                if (content != null && content.matches("^[A-Z]\\..*")) {
                    return content.substring(0, 1); // 返回"A"
                }
            }
            
            // 如果没有找到或内容格式不对，使用数字转字母的方式
            int num = id.intValue();
            if (num >= 1 && num <= 26) {
                return String.valueOf((char)('A' + num - 1));
            }
            
            return optionId; // 如果转换失败，返回原值
        } catch (NumberFormatException e) {
            return optionId; // 如果转换失败，返回原值
        }
    }
}
