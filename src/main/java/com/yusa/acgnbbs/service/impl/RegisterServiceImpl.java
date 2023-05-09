package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.MessageService;
import com.yusa.acgnbbs.service.RegisterService;
import com.yusa.acgnbbs.utils.RedisCache;
import com.yusa.acgnbbs.utils.RegexValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisCache redisCache;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
//          name就是name
        String username = user.getUsername();
//        phone 为邮箱或手机号
        String phone =user.getPhone();
//        password就是password
        String password = user.getPassword();
//        bio字段是验证码
        String userCaptcha =user.getBio();
//        gender为性别
        Integer gender = user.getGender();
        // false为手机号，true为邮箱
        boolean registerType=false;
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        String serverCaptcha = redisCache.getCacheObject(phone).toString();
        //如果验证码过期
        if(StringUtils.isEmpty(serverCaptcha)) {
            return new ResponseResult(200,"验证码过期",null);
        }
        //校验手机号和邮箱格式正确性
        boolean checkPhone = RegexValidateUtil.checkPhone(phone);
        boolean checkEmail = RegexValidateUtil.checkEmail(phone);
        if(checkPhone){
            lambdaQueryWrapper.eq(User::getPhone, phone);
            registerType=false;
        }
        else if(checkEmail){
            lambdaQueryWrapper.eq(User::getEmail, phone);
            registerType=true;
        }
        else {
            return new ResponseResult<>(200,"手机号或邮箱格式错误",null);
        }
         User user1 = userMapper.selectOne(lambdaQueryWrapper);
         //若未注册过，则判断验证码是否正确
        if(Objects.isNull(user1)) {
            //验证码校验通过，开始注册
            if (userCaptcha.equals(serverCaptcha)) {
                user.setUsername(username);
                if (registerType){
                    user.setEmail(user.getPhone());
                    user.setPhone("");
                }
                else {
                    user.setPhone(user.getPhone());
                }
                user.setGender(gender);
                String encode = passwordEncoder.encode(password);
                user.setBio("你还没有个人简介哦~快去填写一下吧");
                user.setPassword(encode);
                user.setIsBanned(0);
                user.setDelFlag(0);
                userMapper.insert(user);
            }
            //验证码错误
            else {
                return new ResponseResult<>(200, "验证码错误", null);
            }
        }
        //若注册过，则返回
        else {
            if(registerType) {
                return new ResponseResult<>(200, "该邮箱已被注册", null);
            }
            else {
                return new ResponseResult<>(200, "该手机号已被注册", null);
            }
        }

        return new ResponseResult<>(200,"注册成功",null);
    }
}
