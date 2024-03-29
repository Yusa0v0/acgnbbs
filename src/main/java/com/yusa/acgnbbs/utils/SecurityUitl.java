package com.yusa.acgnbbs.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SecurityUitl {
    @Autowired
    UserMapper userMapper;
    public int getUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String s = principal.toString();
        System.out.println(s);
        if(s.equals("anonymousUser")){
            return 0;
        }
        LoginUser loginuser = (LoginUser)principal;
        String username = loginuser.getUsername();
        System.out.println(username);
        boolean checkPhone = RegexValidateUtil.checkPhone(username);
        boolean checkEmail = RegexValidateUtil.checkEmail(username);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(checkPhone){
            lambdaQueryWrapper.eq(User::getPhone, username);
        }else if (checkEmail){
            lambdaQueryWrapper.eq(User::getEmail, username);
        }else {
            return 0;
        }
        User user = userMapper.selectOne(lambdaQueryWrapper);
        return user.getId();
    }
    public User getUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String s = principal.toString();
//        System.out.println(s);
        if(s.equals("anonymousUser")){
            return null;
        }
        LoginUser loginuser = (LoginUser) principal;
        String username = loginuser.getUsername();
        System.out.println(username);
        boolean checkPhone = RegexValidateUtil.checkPhone(username);
        boolean checkEmail = RegexValidateUtil.checkEmail(username);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(checkPhone){
            lambdaQueryWrapper.eq(User::getPhone, username);
        }else if (checkEmail){
            lambdaQueryWrapper.eq(User::getEmail, username);
        }else {
            return new User();
        }
        User user = userMapper.selectOne(lambdaQueryWrapper);
        return user;
    }
    public Boolean checkUserId(int userId){
        int serverId = getUserId();
        return (serverId==userId);
    }
}
