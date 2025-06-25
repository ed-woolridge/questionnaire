package com.kevin.controller;

import com.kevin.service.SmsService;
import com.kevin.service.UserService;
import com.kevin.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户认证", description = "手机号验证码发送、验证、用户登录等相关接口")
public class AuthController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    @Operation(summary = "发送验证码", description = "向指定手机号发送验证码")
    public Result sendCode(
            @Parameter(description = "手机号", required = true) @RequestParam String phone,
            @Parameter(description = "验证码类型：LOGIN(登录)、REGISTER(注册)、QUESTIONNAIRE(问卷验证)", required = true) @RequestParam String type) {
        try {
            boolean success = smsService.sendCode(phone, type);
            if (success) {
                return Result.success("验证码发送成功");
            } else {
                return Result.error("验证码发送失败");
            }
        } catch (Exception e) {
            return Result.error("验证码发送失败：" + e.getMessage());
        }
    }

    /**
     * 验证验证码
     */
    @PostMapping("/verifyCode")
    @Operation(summary = "验证验证码", description = "验证手机号收到的验证码")
    public Result verifyCode(
            @Parameter(description = "手机号", required = true) @RequestParam String phone,
            @Parameter(description = "验证码", required = true) @RequestParam String code,
            @Parameter(description = "验证码类型", required = true) @RequestParam String type) {
        try {
            boolean success = smsService.verifyCode(phone, code, type);
            if (success) {
                // 验证成功后，更新用户状态为已认证
                userService.updateUserStatus(phone, "VERIFIED");
                return Result.success("验证成功");
            } else {
                return Result.error("验证码错误或已过期");
            }
        } catch (Exception e) {
            return Result.error("验证失败：" + e.getMessage());
        }
    }

    /**
     * 用户注册/登录（通过手机号）
     */
    @PostMapping("/loginByPhone")
    @Operation(summary = "手机号登录", description = "用户通过手机号登录或注册")
    public Result loginByPhone(
            @Parameter(description = "手机号", required = true) @RequestParam String phone) {
        try {
            // 检查用户是否存在，不存在则创建
            Long userId = userService.getOrCreateUser(phone);
            return Result.success(userId);
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }
} 