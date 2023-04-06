package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface SignService {
    ResponseResult sign();
    ResponseResult getSigned(int userId);
    ResponseResult signCount(int userId);
    ResponseResult signMonthCount(int userId,String date);
    ResponseResult signYearCount(int userId,int year);
    ResponseResult signTotalCount(int userId);
}
