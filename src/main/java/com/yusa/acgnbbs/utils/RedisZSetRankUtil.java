package com.yusa.acgnbbs.utils;

import com.alibaba.fastjson.JSON;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;

@Component
public class RedisZSetRankUtil {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    private int userId;
    private String key;
    private UserInfoVO userInfoVO;

    public RedisZSetRankUtil() {
    }
    public void init(String key, int userId){
        this.userId = userId;
        this.key=key;
        ResponseResult userInfo = userService.getUserInfo(this.userId);
        this.userInfoVO= (UserInfoVO) userService.getUserInfo(this.userId).getData();
        System.out.println(this.userInfoVO);
    }

    // 批量新增
    public void batchAdd() {
        Set<ZSetOperations.TypedTuple<UserInfoVO>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 1; i < 10; i++) {
            DefaultTypedTuple<UserInfoVO> tuple = new DefaultTypedTuple<>( (UserInfoVO) userService.getUserInfo(i).getData(), 1D + i);
            tuples.add(tuple);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        Long num = redisTemplate.opsForZSet().add(this.key, tuples);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);
    }
    public void batchDel(){
        redisTemplate.opsForZSet().removeRange(USER_SCORE_SET,0,10);
    }

    // 获取排名（start从0开始）
    public  Set<String> getRankListWithoutScore(int start,int end) {
        Set<String> range = redisTemplate.opsForZSet().reverseRange(this.key, start, end);
        String s = JSON.toJSONString(range);
        System.out.println("获取到的排行列表:" + s);
        return range;
    }
    public Set<ZSetOperations.TypedTuple<String>>  getRankListWithScore(int start,int end) {
        System.out.println(this.key+start+end);
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(this.key, start, end);
        String s = JSON.toJSONString(rangeWithScores);
        System.out.println("获取到的排行和分数列表:" + s);
        return rangeWithScores;
    }
    // 获取单人排行
    public Double getUserScore(){
        Double score = redisTemplate.opsForZSet().score(this.key, this.userInfoVO);
        System.out.println("单人分数:" + score);
        return score;
    }
    public Long getUserRankNum(){
        Long rankNum = redisTemplate.opsForZSet().reverseRank(this.key, this.userInfoVO);
        System.out.println("单人排名：" + rankNum);
        return rankNum;
    }
    public Double fetchScore(Double score){
        redisTemplate.opsForZSet().add(this.key,  this.userInfoVO, score);
        System.out.println("增加的分数为：" + score);
        System.out.println("分数增加后：" + score);
        return score;
    }
    // 增加积分
    public Double incrementScore(int addScore){
        Double score = redisTemplate.opsForZSet().incrementScore(this.key,  this.userInfoVO, addScore);
        System.out.println("增加的分数为：" + score);
        System.out.println("分数增加后：" + score);
        return score;
    }


}
