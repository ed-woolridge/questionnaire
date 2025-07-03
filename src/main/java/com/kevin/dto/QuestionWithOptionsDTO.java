package com.kevin.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionWithOptionsDTO {
    private Long questionnaireId;
    private String content;
    private String type;
    private List<String> options;

}