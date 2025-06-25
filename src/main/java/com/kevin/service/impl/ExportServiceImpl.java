package com.kevin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.kevin.entity.AnswerSheet;
import com.kevin.mapper.AnswerSheetMapper;
import com.kevin.service.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ExportServiceImpl implements ExportService {

    @Autowired
    private AnswerSheetMapper answerSheetMapper;

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

}
