package com.kevin.controller;

import com.kevin.entity.Question;
import com.kevin.service.QuestionService;
import com.kevin.dto.QuestionWithOptionsDTO;
import com.kevin.entity.QuestionOption;
import com.kevin.service.QuestionOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/question")
@Tag(name = "问题管理", description = "问卷问题的增删改查等管理接口")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionOptionService questionOptionService;

    @PostMapping("/create")
    @Operation(summary = "创建问题", description = "创建新的问卷问题")
    public String create(@Parameter(description = "问题信息", required = true) @RequestBody Question question) {
        return questionService.create(question);
    }

    @PutMapping("/update")
    @Operation(summary = "更新问题", description = "更新现有问题信息")
    public void update(@Parameter(description = "问题信息", required = true) @RequestBody Question question) {
        questionService.update(question);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除问题", description = "根据ID删除问题")
    public void delete(@Parameter(description = "问题ID", required = true) @PathVariable Long id) {
        questionService.delete(id);
    }

    @GetMapping("/list/{questionnaireId}")
    @Operation(summary = "获取问卷问题列表", description = "根据问卷ID获取该问卷的所有问题")
    public List<Question> list(@Parameter(description = "问卷ID", required = true) @PathVariable Long questionnaireId) {
        return questionService.listByQuestionnaireId(questionnaireId);
    }

    @PostMapping("/createWithOptions")
    @Operation(summary = "创建问题并批量添加选项", description = "创建新问题并批量添加选项")
    public String createWithOptions(@RequestBody QuestionWithOptionsDTO dto) {
        // 1. 创建问题
        Question question = new Question();
        question.setQuestionnaireId(dto.getQuestionnaireId());
        question.setContent(dto.getContent());
        question.setType(dto.getType());
        questionService.create(question);
        // 2. 批量插入选项
        if (("SINGLE_CHOICE".equals(dto.getType()) || "MULTIPLE_CHOICE".equals(dto.getType())) && dto.getOptions() != null) {
            for (String opt : dto.getOptions()) {
                QuestionOption option = new QuestionOption();
                option.setQuestionId(question.getId());
                option.setContent(opt);
                questionOptionService.create(option);
            }
        }
        return "success";
    }
}

