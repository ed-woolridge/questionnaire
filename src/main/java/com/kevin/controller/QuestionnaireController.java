package com.kevin.controller;

import com.kevin.common.Result;
import com.kevin.entity.Questionnaire;
import com.kevin.service.QuestionnaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/questionnaire")
@Tag(name = "问卷管理", description = "问卷的增删改查等管理接口")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping("/create")
    @Operation(summary = "创建问卷", description = "创建新的问卷")
    public Result create(@Parameter(description = "问卷信息", required = true) @RequestBody Questionnaire questionnaire) {
         return questionnaireService.create(questionnaire);
    }

    @PutMapping("/update")
    @Operation(summary = "更新问卷", description = "更新现有问卷信息")
    public Result update(@Parameter(description = "问卷信息", required = true) @RequestBody Questionnaire questionnaire) {
        return questionnaireService.update(questionnaire);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除问卷", description = "根据ID删除问卷")
    public Result delete(@Parameter(description = "问卷ID", required = true) @PathVariable Long id) {
        return questionnaireService.delete(id);
    }

    @GetMapping("/list")
    @Operation(summary = "获取问卷列表", description = "获取所有问卷列表")
    public Result list() {
        List<Questionnaire> list = questionnaireService.list();
        return Result.success(list);
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "根据ID获取问卷", description = "根据问卷ID获取问卷详细信息")
    public Result getById(@Parameter(description = "问卷ID", required = true) @PathVariable Long id) {
        Questionnaire questionnaire = questionnaireService.getById(id);
        return Result.success(questionnaire);
    }
}
