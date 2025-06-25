package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 问题实体类
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问卷ID
     */
    @TableField(value = "questionnaire_id")
    private Long questionnaireId;

    /**
     * 问题内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 问题类型：SINGLE_CHOICE(单选)、MULTIPLE_CHOICE(多选)、TEXT(填空)、NUMBER(数字)、SCREENING(筛选题)
     */
    @TableField(value = "type")
    private String type;

    /**
     * 排序号
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 是否必填：0-否，1-是
     */
    @TableField(value = "is_required")
    private Integer isRequired;

    /**
     * 是否为筛选题：0-否，1-是
     */
    @TableField(value = "is_screening")
    private Integer isScreening;

    /**
     * 筛选题终止条件（JSON格式，如{"option":"B","action":"terminate"}）
     */
    @TableField(value = "screening_condition")
    private String screeningCondition;

    /**
     * 问题说明
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;
}