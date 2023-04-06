package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;

public interface UserService {
     ResponseResult setUserInfo(User user);
     ResponseResult getUserInfo(int id);
     String getUserAvatar(int id);
     String getUsername(int id);




}
