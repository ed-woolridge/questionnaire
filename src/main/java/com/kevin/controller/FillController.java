package com.kevin.controller;

import com.kevin.common.Result;
import com.kevin.dto.AnswerSheetDTO;
import com.kevin.service.FillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questionnaire")
@Tag(name = "问卷填写", description = "用户填写问卷相关接口")
public class FillController {
    @Autowired
    private FillService fillService;

    @PostMapping("/submit")
    @Operation(summary = "提交问卷", description = "用户提交填写的问卷答案")
    public Result submit(@RequestBody AnswerSheetDTO dto) {
        fillService.saveAnswerSheet(dto);
        return Result.success("提交问卷成功，感谢您的参与！");
    }
}
