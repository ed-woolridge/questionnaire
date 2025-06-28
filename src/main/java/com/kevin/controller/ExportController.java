package com.kevin.controller;

import com.kevin.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "数据导出", description = "问卷数据导出相关接口")
public class ExportController {
    @Autowired
    private ExportService exportService;

    @GetMapping("/admin/questionnaire/export/{id}")
    @Operation(summary = "导出问卷数据", description = "将指定问卷的填写数据导出为Excel文件")
    public void export(
            @Parameter(description = "问卷ID", required = true) @PathVariable Long id,
            HttpServletResponse response) {
        exportService.exportToExcel(id, response);
    }

    @GetMapping("/admin/answers/export")
    @Operation(summary = "导出所有答卷", description = "导出所有用户答卷为Excel，按用户名和题目列展示")
    public void exportAnswers(HttpServletResponse response) {
        exportService.exportAnswersToExcel(response);
    }
}
