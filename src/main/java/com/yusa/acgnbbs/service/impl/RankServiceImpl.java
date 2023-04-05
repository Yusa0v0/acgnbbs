package com.yusa.acgnbbs.service.impl;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public ResponseResult getSignNumRank() {
//        redisTemplate.opsForZSet()

        return null;
    }

    @Override
    public ResponseResult getScoreRank() {
        return null;
    }

    @Override
    public ResponseResult getFanNumRank() {
        return null;
    }

    @Override
    public ResponseResult getPageViewRank() {
        return null;
    }
}
