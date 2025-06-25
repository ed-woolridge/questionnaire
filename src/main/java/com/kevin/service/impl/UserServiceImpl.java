package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.entity.User;
import com.kevin.service.UserService;
import com.kevin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
* @author woolridge
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-06-24 15:02:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getOrCreateByPhone(String phone, String location) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        user = getUser(phone, user);
        return user;
    }

    private User getUser(String phone, User user) {
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setRole("USER");
            user.setStatus("UNVERIFIED");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setIsDeleted(0);
            userMapper.insert(user);
        }
        return user;
    }

    @Override
    public Long getOrCreateUser(String phone) {
        User user = getUserByPhone(phone);
        user = getUser(phone, user);
        return user.getId();
    }

    @Override
    public boolean updateUserStatus(String phone, String status) {
        User user = getUserByPhone(phone);
        if (user != null) {
            user.setStatus(status);
            user.setUpdateTime(new Date());
            return userMapper.updateById(user) > 0;
        }
        return false;
    }

    @Override
    public User getUserByPhone(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        queryWrapper.eq("is_deleted", 0);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean isUserVerified(String phone) {
        User user = getUserByPhone(phone);
        return user != null && "VERIFIED".equals(user.getStatus());
    }
}




