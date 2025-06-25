package com.kevin.controller;

import com.kevin.common.Result;
import com.kevin.entity.Admin;
import com.kevin.service.AdminService;
import com.kevin.vo.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "管理员管理", description = "管理员登录、信息管理等相关接口")
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 管理员登录
     */
    @PostMapping("/login")
    @Operation(summary = "管理员登录", description = "管理员通过用户名和密码登录系统")
    public Result login(@RequestBody LoginVo loginVo) {
        try {
            Admin admin = adminService.login(loginVo.getUsername(), loginVo.getPassword());
            if (admin != null) {
                return Result.success(admin);
            } else {
                return Result.error("用户名或密码错误");
            }
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 获取管理员信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "获取管理员信息", description = "根据管理员ID获取管理员详细信息")
    public Result getAdminInfo(
            @Parameter(description = "管理员ID", required = true) @PathVariable Long id) {
        try {
            Admin admin = adminService.getById(id);
            if (admin != null) {
                return Result.success(admin);
            } else {
                return Result.error("管理员不存在");
            }
        } catch (Exception e) {
            return Result.error("获取信息失败：" + e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    @Operation(summary = "修改密码", description = "管理员修改登录密码")
    public Result changePassword(
            @Parameter(description = "管理员ID", required = true) @RequestParam Long adminId,
            @Parameter(description = "原密码", required = true) @RequestParam String oldPassword,
            @Parameter(description = "新密码", required = true) @RequestParam String newPassword) {
        try {
            boolean success = adminService.changePassword(adminId, oldPassword, newPassword);
            if (success) {
                return Result.success("密码修改成功");
            } else {
                return Result.error("原密码错误");
            }
        } catch (Exception e) {
            return Result.error("密码修改失败：" + e.getMessage());
        }
    }
} 