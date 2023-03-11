package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.service.LoginService;
import com.yusa.acgnbbs.service.MessageService;
import com.yusa.acgnbbs.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController  {

    @Autowired
    LoginService loginService;
    @Autowired
    MessageService messageService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }
    @PostMapping("/user/loginByCaptcha")
    public ResponseResult loginByCaptcha(@RequestBody User user){
        return loginService.loginByCaptcha(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult logout( ){
        return loginService.logout();
    }


}
