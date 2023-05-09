package com.yusa.acgnbbs.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yusa.acgnbbs.domain.LoginAdmin;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.service.impl.AdminDetailsServiceImpl;
import com.yusa.acgnbbs.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

//自定义AuthenticationProvider
@Component
public class AdminCustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AdminDetailsServiceImpl adminDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        log.info("enter into custom AuthenticationProvider");
        //
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new UsernameNotFoundException("username用户名不可以为空");
        }
        if(StringUtils.isBlank(password)){
            throw new BadCredentialsException("密码不可以为空");
        }
        //获取用户信息
        LoginAdmin loginAdmin = (LoginAdmin) adminDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        //比较前端传入的密码明文和数据库中加密的密码是否相等
        if (!passwordEncoder.matches(password, loginAdmin.getPassword())) {
            //发布密码不正确事件
            throw new BadCredentialsException("password密码不正确");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = loginAdmin.getAuthorities();
        System.out.println("Authorities"+loginAdmin.getAuthorities());
        return new CustomAuthenticationToken(loginAdmin, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

