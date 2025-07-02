package com.kevin.controller;

import com.kevin.common.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/user")
    public String getUser(@RequestParam(name = "username", required = false) String name) {
        return "Hello, " + name;
    }

    @PostMapping("/create")
    public Result create(@RequestBody String phone) {
        return Result.success("用户创建成功");
    }
}
