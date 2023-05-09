package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Admin;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public ResponseResult login(@RequestBody Admin admin){
        return loginService.adminLogin(admin);
    }
}
