package com.kevin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 短信验证码实体类
 * @TableName sms_code
 */
@TableName(value ="sms_code")
@Data
public class SmsCode {
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
     * 验证码
     */
    private String code;

    /**
     * 验证码类型：LOGIN(登录)、REGISTER(注册)、QUESTIONNAIRE(问卷验证)
     */
    private String type;

    /**
     * 验证码状态：UNUSED(未使用)、USED(已使用)、EXPIRED(已过期)
     */
    private String status;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 使用时间
     */
    private Date usedTime;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDeleted;
} 