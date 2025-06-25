package com.kevin.service;

import jakarta.servlet.http.HttpServletResponse;

public interface ExportService {
    void exportToExcel(Long questionnaireId, HttpServletResponse response);
}
