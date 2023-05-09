package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.LoginAdmin;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.entity.Admin;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.AdminMapper;
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

public class AdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户
        LambdaQueryWrapper<Admin> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getAdminName, username);

        Admin admin = adminMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(admin)){
            throw new RuntimeException("用户名错误");
        }

//        System.out.println(loginUSer);
        // TODO 查询权限信息
        List<String> list =new ArrayList<>(Arrays.asList("aAdmin","cAdmin","gAdmin","nAdmin"));

        LoginAdmin loginAdmin= new LoginAdmin(admin,list);
        return loginAdmin;
    }
}