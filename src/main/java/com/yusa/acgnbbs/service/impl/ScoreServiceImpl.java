package com.yusa.acgnbbs.service.impl;

import com.alibaba.fastjson.JSON;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.ScoreService;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import com.yusa.acgnbbs.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;

public class ScoreServiceImpl implements ScoreService {
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Override
    public ResponseResult addUserScore(int userId,int score) {
        RedisZSetRankUtil redisZSetRankUtil = new RedisZSetRankUtil();
        redisZSetRankUtil.incrementScore(score);
        return null;
    }
    @Override
    public ResponseResult getUserScore(int userId) {
        return null;
    }

}
