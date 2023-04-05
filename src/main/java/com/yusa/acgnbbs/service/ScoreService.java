package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.vo.UserInfoVO;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;

public interface ScoreService {
    ResponseResult addUserScore(int userId,int score);
    ResponseResult getUserScore(int userId);
}

