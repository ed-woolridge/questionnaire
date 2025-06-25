package com.kevin.service;

import com.kevin.entity.Admin;

/**
 * 管理员服务接口
 */
public interface AdminService {
    
    /**
     * 管理员登录
     */
    Admin login(String username, String password);
    
    /**
     * 根据ID获取管理员信息
     */
    Admin getById(Long id);
    
    /**
     * 修改密码
     */
    boolean changePassword(Long adminId, String oldPassword, String newPassword);
    
    /**
     * 创建管理员
     */
    boolean createAdmin(Admin admin);
    
    /**
     * 更新管理员信息
     */
    boolean updateAdmin(Admin admin);
} 