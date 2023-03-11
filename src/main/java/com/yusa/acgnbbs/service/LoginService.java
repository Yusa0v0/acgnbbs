package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    public ResponseResult login(User user);
    public ResponseResult loginByCaptcha(User user);
    ResponseResult logout();
}
