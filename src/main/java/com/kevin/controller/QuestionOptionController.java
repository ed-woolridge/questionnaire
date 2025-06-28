package com.kevin.controller;

import com.kevin.entity.QuestionOption;
import com.kevin.service.QuestionOptionService;
import com.kevin.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/option")
@Tag(name = "选项管理", description = "问题选项的增删改查等管理接口")
public class QuestionOptionController {

    @Autowired
    private QuestionOptionService questionOptionService;

    @PostMapping("/create")
    @Operation(summary = "创建选项", description = "创建新的问题选项")
    public void create(@Parameter(description = "选项信息", required = true) @RequestBody QuestionOption questionOption) {
        questionOptionService.create(questionOption);
    }

    @PutMapping("/update")
    @Operation(summary = "更新选项", description = "更新现有选项信息")
    public void update(@Parameter(description = "选项信息", required = true) @RequestBody QuestionOption questionOption) {
        questionOptionService.update(questionOption);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除选项", description = "根据ID删除选项")
    public void delete(@Parameter(description = "选项ID", required = true) @PathVariable Long id) {
        questionOptionService.delete(id);
    }

    @GetMapping("/list/{questionId}")
    @Operation(summary = "获取问题选项列表", description = "根据问题ID获取该问题的所有选项")
    public Result list(@Parameter(description = "问题ID", required = true) @PathVariable Long questionId) {
        return Result.success(questionOptionService.listByQuestionId(questionId));
    }
}
