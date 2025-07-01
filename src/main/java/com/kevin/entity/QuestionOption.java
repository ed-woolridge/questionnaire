package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName question_option
 */
@TableName(value ="question_option")
@Data
public class QuestionOption {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 问题ID
     */
    private Long questionId;

    /**
     * 问题选项
     */
    private String content;

    /**
     * 是否有子选项或者输入框
     * 0：false 1：true
     */
    private Boolean showInput;
}