package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;

public interface UserService {
    public ResponseResult setUserInfo(User user);
    public ResponseResult getUserInfo(int id);
    public String getUserAvatar(int id);
    String getUsername(int id);
    public ResponseResult sign();
    public ResponseResult getSigned(int userId);
    ResponseResult signCount(int userId);
    ResponseResult signMonthCount(int userId,String date);
    ResponseResult signYearCount(int userId,int year);

}
