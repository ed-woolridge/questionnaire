package com.kevin.dto;

import java.util.List;

public class QuestionWithOptionsDTO {
    private Long questionnaireId;
    private String content;
    private String type;
    private List<String> options;

    public Long getQuestionnaireId() {
        return questionnaireId;
    }
    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
} 