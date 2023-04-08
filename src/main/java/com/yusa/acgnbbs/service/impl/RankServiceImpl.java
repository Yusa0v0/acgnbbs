package com.yusa.acgnbbs.service.impl;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.RankService;
import com.yusa.acgnbbs.service.ScoreService;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.yusa.acgnbbs.constants.SystemConstants.*;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ScoreService scoreService;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;



    @Override
    public ResponseResult getScoreRankList(int start,int end) {
        redisZSetRankUtil.init(USER_SCORE_SET,1);
        Set<ZSetOperations.TypedTuple<String>> rankListWithScore = redisZSetRankUtil.getRankListWithScore(start, end);
        return new ResponseResult(200,"OK",rankListWithScore);
    }

    @Override
    public ResponseResult getSignNumRankList(int start, int end) {
        redisZSetRankUtil.init(USER_SIGN_NUM_SET,1);
        Set<ZSetOperations.TypedTuple<String>> rankListWithScore = redisZSetRankUtil.getRankListWithScore(start, end);
        return new ResponseResult(200,"OK",rankListWithScore);
    }

    @Override
    public ResponseResult getFanNumRankList(int start, int end) {
        redisZSetRankUtil.init(USER_FAN_NUM_SET,1);
        Set<ZSetOperations.TypedTuple<String>> rankListWithScore = redisZSetRankUtil.getRankListWithScore(start, end);
        return new ResponseResult(200,"OK",rankListWithScore);
    }

    @Override
    public ResponseResult getPageViewRankList(int start, int end) {
        redisZSetRankUtil.init(USER_PAGE_VIEW_SET,1);
        Set<ZSetOperations.TypedTuple<String>> rankListWithScore = redisZSetRankUtil.getRankListWithScore(start, end);
        return new ResponseResult(200,"OK",rankListWithScore);
    }

    @Override
    public ResponseResult getPostNumRankList(int start, int end) {
        redisZSetRankUtil.init(USER_POST_NUM_SET,1);
        Set<ZSetOperations.TypedTuple<String>> rankListWithScore = redisZSetRankUtil.getRankListWithScore(start, end);
        return new ResponseResult(200,"OK",rankListWithScore);

    }
}
