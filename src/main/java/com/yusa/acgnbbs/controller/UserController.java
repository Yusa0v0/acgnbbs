package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.service.MessageService;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.service.impl.EmailServiceImpl;
import com.yusa.acgnbbs.utils.RandomUtils;
import com.yusa.acgnbbs.utils.RegexValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;    //注入redis
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/user/userInfo/{id}")
    public ResponseResult getUserInfo(@PathVariable int id){
        return userService.getUserInfo(id);
    }
    @PostMapping("/user/userInfo")
    public ResponseResult setUserInfo(@RequestBody User user){
        return userService.setUserInfo(user);
    }

    @GetMapping("/user/userInfoList/{currentPage}/{pageSize}")
    public ResponseResult getUserInfoList(@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize){
        return userService.getUserInfoList(currentPage,pageSize);
    }
    @GetMapping("/user/banUser/{id}")
    public ResponseResult banUser(@PathVariable int id){
        return userService.banUser(id);
    }
    @GetMapping("/user/cancelBanUser/{id}")
    public ResponseResult cancelBanUser(@PathVariable int id){
        return userService.cancelBanUser(id);
    }
    // username
    @GetMapping("/user/sendCaptcha/{username}")
    public ResponseResult sendCaptcha(@PathVariable String username){
        boolean checkEmail = RegexValidateUtil.checkEmail(username);
        boolean checkPhone = RegexValidateUtil.checkPhone(username);
        //1、从redis中获取验证码，如果获取到就直接返回
        String code = redisTemplate.opsForValue().get(username);
        if(!StringUtils.isEmpty(code)) {
            return new ResponseResult(200,"OK",null);
        }
        //2、如果获取不到，就进行阿里云发送或者邮箱发送
        code = RandomUtils.getFourBitRandom();//生成验证码的随机值
        if(checkPhone) {
            Map<String, Object> param = new HashMap<>();
            param.put("code", code);
            //调用方法
            boolean isSend = messageService.send(param, username);
            if (isSend) {
                //往redis中设置数据：key、value、过期值、过期时间单位  MINUTES代表分钟
                redisTemplate.opsForValue().set(username, code, 5, TimeUnit.MINUTES);
                return new ResponseResult(200, "OK", null);
            } else {
                return new ResponseResult(200, "OK", null);
            }
        }
        else if(checkEmail){
            String subject = "Verification Code";
            String text = "Your verification code is " + code;
            emailService.sendEmail(username, subject, text);
            redisTemplate.opsForValue().set(username, code,5, TimeUnit.MINUTES);
            return new ResponseResult(200,"OK",null);
        }
        else {
            return new ResponseResult(200,"OK",null);
        }
    }

}
