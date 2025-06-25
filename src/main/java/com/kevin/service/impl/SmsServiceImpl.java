package com.kevin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.entity.SmsCode;
import com.kevin.mapper.SmsCodeMapper;
import com.kevin.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;

/**
 * 短信服务实现类
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

    @Override
    public boolean sendCode(String phone, String type) {
        try {
            // 生成6位随机验证码
            String code = generateCode();
            
            // 检查是否在1分钟内已经发送过验证码
            QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            queryWrapper.eq("type", type);
            queryWrapper.eq("status", "UNUSED");
            queryWrapper.gt("expire_time", new Date());
            
            SmsCode existingCode = smsCodeMapper.selectOne(queryWrapper);
            if (existingCode != null) {
                // 如果存在未过期的验证码，检查发送时间是否在1分钟内
                long timeDiff = System.currentTimeMillis() - existingCode.getSendTime().getTime();
                if (timeDiff < 60000) { // 1分钟 = 60000毫秒
                    return false; // 发送过于频繁
                }
            }
            
            // 创建新的验证码记录
            SmsCode smsCode = new SmsCode();
            smsCode.setPhone(phone);
            smsCode.setCode(code);
            smsCode.setType(type);
            smsCode.setStatus("UNUSED");
            smsCode.setSendTime(new Date());
            
            // 设置过期时间为5分钟后
            Date expireTime = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
            smsCode.setExpireTime(expireTime);
            smsCode.setIsDeleted(0);
            
            // 保存到数据库
            smsCodeMapper.insert(smsCode);
            
            // TODO: 这里应该调用实际的短信发送接口
            // 目前只是模拟发送，实际项目中需要集成短信服务商的API
            System.out.println("发送验证码到手机号: " + phone + ", 验证码: " + code);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verifyCode(String phone, String code, String type) {
        try {
            QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            queryWrapper.eq("code", code);
            queryWrapper.eq("type", type);
            queryWrapper.eq("status", "UNUSED");
            queryWrapper.gt("expire_time", new Date());
            queryWrapper.eq("is_deleted", 0);
            
            SmsCode smsCode = smsCodeMapper.selectOne(queryWrapper);
            if (smsCode != null) {
                // 标记验证码为已使用
                smsCode.setStatus("USED");
                smsCode.setUsedTime(new Date());
                smsCodeMapper.updateById(smsCode);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendQuestionnaireInvitation(String phone, String questionnaireUrl) {
        // TODO: 实现问卷邀请短信发送
        // 这里需要集成短信服务商的API
        System.out.println("发送问卷邀请短信到手机号: " + phone + ", 链接: " + questionnaireUrl);
        return true;
    }

    @Override
    public boolean batchSendQuestionnaire(String[] phones, String questionnaireUrl) {
        // TODO: 实现批量发送问卷短信
        // 这里需要集成短信服务商的API
        for (String phone : phones) {
            System.out.println("批量发送问卷短信到手机号: " + phone + ", 链接: " + questionnaireUrl);
        }
        return true;
    }

    /**
     * 生成6位随机验证码
     */
    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
} 