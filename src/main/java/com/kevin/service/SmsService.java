package com.kevin.service;

/**
 * 短信服务接口
 */
public interface SmsService {
    
    /**
     * 发送验证码
     */
    boolean sendCode(String phone, String type);
    
    /**
     * 验证验证码
     */
    boolean verifyCode(String phone, String code, String type);
    
    /**
     * 发送问卷邀请短信（预留接口）
     */
    boolean sendQuestionnaireInvitation(String phone, String questionnaireUrl);
    
    /**
     * 批量发送问卷短信（预留接口）
     */
    boolean batchSendQuestionnaire(String[] phones, String questionnaireUrl);
} 