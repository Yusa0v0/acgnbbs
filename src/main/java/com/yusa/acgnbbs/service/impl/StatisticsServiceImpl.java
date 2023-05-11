package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.mapper.CommentMapper;
import com.yusa.acgnbbs.service.StatisticsService;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SIGN_KEY;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void increaseDailyStatistics(String key) {
        LocalDateTime now = LocalDateTime.now();
        //2.1获取当前日期中的  年和月
        String keySuffix = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //3：拼接key  年月日
        key = key + keySuffix;
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public void increaseDailyStatisticsWithValue(String key, String day,long value) {
        key = key + day;
        redisTemplate.opsForValue().increment(key,value);
    }

    @Override
    public ResponseResult getDailyStatistics(String key,String day) {
        key = key + day;
        if(redisTemplate.hasKey(key)) {
            Integer integer = Integer.valueOf(redisTemplate.opsForValue().get(key).toString());
            return ResponseResult.okResult(integer);
        }
        return ResponseResult.okResult(0);
    }

    @Override
    public ResponseResult getLastWeekStatistics(String key) {
        LocalDateTime now = LocalDateTime.now();
        // 获取当前日期所在星期的第一天（星期一）的日期
        LocalDateTime firstDayOfCurrentWeek = now.with(DayOfWeek.MONDAY);
        // 获取当前日期所在星期的第一天（星期一）的前一星期的第一天（即上一星期的星期一）的日期
        LocalDateTime firstDayOfPreviousWeek = firstDayOfCurrentWeek.minusWeeks(1);
        // 输出上一星期的每一天的日期
        List<Integer> lastWeekStatistics = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDateTime currentDay = firstDayOfPreviousWeek.plusDays(i);
            String keySuffix = currentDay.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
             Integer dailyCommentStatistics =(Integer) getDailyStatistics(key,keySuffix).getData();
            lastWeekStatistics.add(dailyCommentStatistics);
        }
        //2.1获取当前日期中的  年和月
        return ResponseResult.okResult(lastWeekStatistics);
    }
}
