package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答卷实体类
 * @TableName answer_sheet
 */
@TableName(value ="answer_sheet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSheet {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 问卷ID
     */
    private Long questionnaireId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 答卷状态：IN_PROGRESS(填写中)、COMPLETED(已完成)、TERMINATED(已终止)
     */
    private String status;

    /**
     * 开始填写时间
     */
    private Date startTime;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 填写时长（分钟）
     */
    private Integer duration;

    /**
     * 调查员姓名
     */
    private String surveyorName;

    /**
     * 调查员电话
     */
    private String surveyorPhone;

    /**
     * 调查地点省份
     */
    private String surveyProvince;

    /**
     * 调查地点城市
     */
    private String surveyCity;

    /**
     * 调查地点县区
     */
    private String surveyDistrict;

    /**
     * 调查地点具体位置
     */
    private String surveyLocation;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDeleted;
}