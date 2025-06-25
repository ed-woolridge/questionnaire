package com.kevin.service;

import com.kevin.entity.AnswerSheet;
import com.kevin.dto.AnswerSheetDTO;

public interface FillService {
    void submit(Long questionnaireId, AnswerSheet answerSheet);
    void saveAnswerSheet(AnswerSheetDTO dto);
}