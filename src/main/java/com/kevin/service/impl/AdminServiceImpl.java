package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.entity.Admin;
import com.kevin.mapper.AdminMapper;
import com.kevin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.Date;

/**
 * 管理员服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("username", username);
        queryWrapper.eq("is_deleted", 0);

        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin != null) {
            // 验证密码（这里使用MD5加密，实际项目中建议使用更安全的加密方式）
            String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            if (encryptedPassword.equals(admin.getPassword())) {
                // 更新最后登录时间
                admin.setLastLoginTime(new Date());
                adminMapper.updateById(admin);
                return admin;
            }
        }
        return null;
    }

    @Override
    public Admin getById(Long id) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_deleted", 0);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean changePassword(Long adminId, String oldPassword, String newPassword) {
        Admin admin = getById(adminId);
        if (admin == null) {
            return false;
        }
        
        // 验证原密码
        String encryptedOldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!encryptedOldPassword.equals(admin.getPassword())) {
            return false;
        }
        
        // 更新新密码
        String encryptedNewPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        admin.setPassword(encryptedNewPassword);
        admin.setUpdateTime(new Date());
        
        return adminMapper.updateById(admin) > 0;
    }

    @Override
    public boolean createAdmin(Admin admin) {
        admin.setCreateTime(new Date());
        admin.setUpdateTime(new Date());
        admin.setStatus("ACTIVE");
        admin.setIsDeleted(0);
        
        // 加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes());
        admin.setPassword(encryptedPassword);
        
        return adminMapper.insert(admin) > 0;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        admin.setUpdateTime(new Date());
        return adminMapper.updateById(admin) > 0;
    }
} 