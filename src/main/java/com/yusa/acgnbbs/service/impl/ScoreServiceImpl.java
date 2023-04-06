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
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Override
    public ResponseResult addUserScore(int userId,int score) {
        redisZSetRankUtil.init(USER_SCORE_SET,userId);
        redisZSetRankUtil.incrementScore(score);
        return ResponseResult.okResult();
    }
    @Override
    public ResponseResult getUserScore(int userId) {
        redisZSetRankUtil.init(USER_SCORE_SET,userId);
        Double userScore = redisZSetRankUtil.getUserScore();
        return new ResponseResult(200,"OK",userScore);
    }
}
