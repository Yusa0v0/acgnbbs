package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;

public interface UserService {
    public ResponseResult setUserInfo(User user);
    public ResponseResult getUserInfo(int id);
}
