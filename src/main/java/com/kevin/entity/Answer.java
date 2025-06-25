package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName answer
 */
@TableName(value ="answer")
@Data
public class Answer implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "answer_sheet_id")
    private Long answerSheetId;

    /**
     * 
     */
    @TableField(value = "question_id")
    private Long questionId;

    /**
     * 
     */
    @TableField(value = "option_id")
    private Long optionId;

    /**
     * 
     */
    @TableField(value = "text_answer")
    private String textAnswer;

    /**
     * 
     */
    @TableField(value = "number_answer")
    private java.math.BigDecimal numberAnswer;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}