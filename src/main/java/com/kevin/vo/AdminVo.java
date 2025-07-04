package com.kevin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author: woolridge
 * @Date: 2021/7/31 10:01 下午
 * @Description: 管理员信息返回vo
 */
@Data
public class AdminVo {

    /**
     * 管理员ID
     */
    private Long id;

    /**
     * 管理员用户名
     */
    private String username;

    /**
     * 管理员realName
     */
    private String realName;

    /**
     * 管理员账号状态
     */
    private String status;

    /**
     * 登录令牌
     */
    private String token;
}
