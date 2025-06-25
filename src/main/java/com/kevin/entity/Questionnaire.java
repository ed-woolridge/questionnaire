package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

/**
 * 问卷实体类
 * @TableName questionnaire
 */
@TableName(value ="questionnaire")
@Data
@Schema(description = "问卷实体")
public class Questionnaire {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "问卷ID")
    private Long id;

    /**
     * 问卷标题
     */
    @Schema(description = "问卷标题", required = true)
    private String title;

    /**
     * 问卷描述
     */
    @Schema(description = "问卷描述")
    private String description;

    /**
     * 问卷类型：TOURISM_SURVEY(旅游调查问卷)
     */
    @Schema(description = "问卷类型：TOURISM_SURVEY(旅游调查问卷)")
    private String type;

    /**
     * 问卷状态：DRAFT(草稿)、PUBLISHED(已发布)、CLOSED(已关闭)
     */
    @Schema(description = "问卷状态：DRAFT(草稿)、PUBLISHED(已发布)、CLOSED(已关闭)")
    private String status;

    /**
     * 有效期开始时间
     */
    @Schema(description = "有效期开始时间")
    private Date validStartTime;

    /**
     * 有效期结束时间
     */
    @Schema(description = "有效期结束时间")
    private Date validEndTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createBy;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @Schema(description = "是否删除：0-未删除，1-已删除")
    private Integer isDeleted;
}