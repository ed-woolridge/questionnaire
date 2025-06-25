package com.kevin.dto;
import lombok.Data;
import java.util.List;

@Data
public class AnswerSheetDTO {
    private Long questionnaireId;
    private List<AnswerDTO> answers;
} 