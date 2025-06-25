package com.kevin.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/user")
    public String getUser(@RequestParam(name = "username", required = false) String name) {
        return "Hello, " + name;
    }
}
