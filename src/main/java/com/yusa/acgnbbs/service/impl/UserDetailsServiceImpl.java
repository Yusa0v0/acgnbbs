package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.utils.RegexValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        boolean isPhone = RegexValidateUtil.checkPhone(username);
        boolean isEmail = RegexValidateUtil.checkEmail(username);
        boolean loginMethod;
        //手机号登录
        if(isPhone) {
            lambdaQueryWrapper.eq(User::getPhone, username);
            loginMethod=false;
        }
        else if(isEmail){
            lambdaQueryWrapper.eq(User::getEmail, username);
            loginMethod=true;
        }
        else {
            throw new RuntimeException("not phone or email");
        }

        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名错误");
        }

//        System.out.println(loginUSer);
        // TODO 查询权限信息
        List<String> list =new ArrayList<>(Arrays.asList("aAdmin","cAdmin","gAdmin","nAdmin"));

        LoginUser loginUser= new LoginUser(user,list,loginMethod);
        return loginUser;
    }
}
