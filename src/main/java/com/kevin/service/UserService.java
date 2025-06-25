package com.kevin.service;

import com.kevin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author woolridge
* @description 针对表【user】的数据库操作Service
* @createDate 2025-06-24 15:02:54
*/
public interface UserService extends IService<User> {

    /**
     * 根据手机号获取或创建用户
     */
    User getOrCreateByPhone(String phone, String location);

    /**
     * 根据手机号获取或创建用户（简化版）
     */
    Long getOrCreateUser(String phone);

    /**
     * 更新用户状态
     */
    boolean updateUserStatus(String phone, String status);

    /**
     * 根据手机号获取用户
     */
    User getUserByPhone(String phone);

    /**
     * 检查用户是否已认证
     */
    boolean isUserVerified(String phone);

}
