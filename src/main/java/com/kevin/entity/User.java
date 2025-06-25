package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 用户实体类
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户角色：ADMIN(管理员)、USER(普通用户)
     */
    private String role;

    /**
     * 用户状态：UNVERIFIED(未认证)、VERIFIED(已认证)、BLOCKED(已封禁)
     */
    private String status;

    /**
     * 常驻地省份
     */
    private String province;

    /**
     * 常驻地城市
     */
    private String city;

    /**
     * 常驻地县区
     */
    private String district;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDeleted;
}