package com.yusa.acgnbbs;

import com.alibaba.fastjson.JSON;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.mapper.CommentMapper;
import com.yusa.acgnbbs.service.*;
import com.yusa.acgnbbs.utils.RedisCache;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.UserInfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.yusa.acgnbbs.constants.SystemConstants.*;

@SpringBootTest
class AcgnbbsApplicationTests {
    @Autowired
    SecurityUitl securityUitl;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ScoreService scoreService;
    @Autowired
    UserService userService;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Autowired
    FollowService followService;
    @Test
    void contextLoads() {
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RedisCache redisCache;
    @Autowired
    SyncService syncService;
    @Test
    public void t(){
        // 签到
        int userId = 1;
        //2：获取当前日期。
        LocalDateTime now = LocalDateTime.now();
        //2.1获取当前日期中的  年和月
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        //3：拼接key  (当前用户id + 年月)
        String key = USER_SIGN_KEY + userId + keySuffix;
        //5：存入redis   setbit key offset 1
        for (int i=0;i<31;i++) {
            if(i%2==0) continue;
            stringRedisTemplate.opsForValue().setBit(key, i, true);
        }
    }
    @Test
    public void t3(){
        int userId=3;
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        // 5.获取本月截止今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202203 GET u14 0
        Long count = stringRedisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
        assert count != null;
//        return count.intValue();
        System.out.println(count.intValue());
    }
    @Test
    public void te(){
        /**
         * 随机生成浏览量
         */
        for (int year=2018;year<2023;year++) {
            for (int month = 1; month <= 12; month++) {
                int max = 31;
                //1.3.5.7.8.10.12
                //4.6.9.11
                //2
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    max = 30;
                } else if (month == 2) {
                    if(year%4==0) {
                        max = 29;
                    }
                    else {
                        max=28;
                    }
                }
                for (int day = 1; day <= max; day++) {
                    String key = "dailyPageViews-" + year + "-" + month + "-" + day;
                    Random r=new Random();
                    redisCache.setCacheObject(key,r.nextInt(9000));
                }
            }
        }
        //1.3.5.7.8.10.12


    }
    @Test
    // 批量新增
    public void batchAdd() {

    }
    @Test
    public void testZremSet() {
        Set<ZSetOperations.TypedTuple<UserInfoVO>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 1; i < 11; i++) {
            DefaultTypedTuple<UserInfoVO> tuple = new DefaultTypedTuple<>( (UserInfoVO) userService.getUserInfo(i).getData(), 1D + i*10);
            tuples.add(tuple);
        }
        System.out.println("循环时间:" +( System.currentTimeMillis() - start));
        Long num = redisTemplate.opsForZSet().add(USER_SCORE_SET, tuples);
        System.out.println("批量新增时间:" +(System.currentTimeMillis() - start));
        System.out.println("受影响行数：" + num);
    }
    @Test
    public void batchSign(){
        for(int userId=1;userId<=10;++userId) {
            String keySuffix = (":202304");
            String key = USER_SIGN_KEY + userId + keySuffix;
            //4：获取今天是当月的第几天
            for(int dayOfMonth =1;dayOfMonth<=6;++dayOfMonth) {
                //5：存入redis   setbit key offset 1
                stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
                // add score
                scoreService.addUserScore(userId, 20);
                // select USER_SIGN_NUM_SET
                redisZSetRankUtil.init(USER_SIGN_NUM_SET, userId);
                // add one
                redisZSetRankUtil.incrementScore(1);
            }
        }
    }
    @Test
    public void batchFollow(){
        for(int userId=1;userId<=10;++userId) {
            //4：获取今天是当月的第几天
            for(int followedId =1;followedId<=10;++followedId) {
                followService.follow(userId,followedId);
            }
        }
    }
    @Test
    public void sync(){
        syncService.SyncCommentNum();
        syncService.SyncPostNum();
        syncService.SyncFanNum();
    }
    @Autowired
    CommentMapper commentMapper;
    @Test void batchAddComment(){
        for (int i=1;i<=10;++i){
            Comment comment = new Comment();
            comment.setUserId(i);
            comment.setPostId(i+1);
            comment.setContent("我是水军，我来评论了");
            comment.setLikeTimes(0);
            comment.setDelFlag(0);
            int frontId = comment.getUserId();
            comment.setUserId(frontId);
            comment.setLikeTimes(0);
            comment.setDelFlag(0);
            commentMapper.insert(comment);
            redisZSetRankUtil.init(USER_COMMENT_NUM_SET,frontId);
            redisZSetRankUtil.incrementScore(1);
        }
    }


    @Autowired
    StatisticsService statisticsService;
    @Test
    public void s() {
        statisticsService.increaseDailyStatistics(DAILY_COMMENT_STATISTICS_KEY);
        System.out.println(statisticsService.getLastWeekStatistics(DAILY_COMMENT_STATISTICS_KEY).getData());
        System.out.println(statisticsService.getLastWeekStatistics(DAILY_USER_STATISTICS_KEY).getData());
    }
    @Test
    public void batchAddDaily() {
        LocalDateTime now = LocalDateTime.now();
        // 获取当前日期所在星期的第一天（星期一）的日期
        LocalDateTime firstDayOfCurrentWeek = now.with(DayOfWeek.MONDAY);
        // 获取当前日期所在星期的第一天（星期一）的前一星期的第一天（即上一星期的星期一）的日期
        LocalDateTime firstDayOfPreviousWeek = firstDayOfCurrentWeek.minusWeeks(1);
        // 输出上一星期的每一天的日期
        Random rand=new Random();
        int min = 1000;
        int max = 10000;
        for (int i = 0; i < 7; i++) {
            LocalDateTime currentDay = firstDayOfPreviousWeek.plusDays(i);
            String keySuffix = currentDay.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            // 清零
            redisTemplate.opsForValue().set(DAILY_COMMENT_STATISTICS_KEY+keySuffix,0);
            redisTemplate.opsForValue().set(DAILY_USER_STATISTICS_KEY+keySuffix,0);
            int i1 =  rand.nextInt(max - min + 1) + min;
            statisticsService.increaseDailyStatisticsWithValue(DAILY_COMMENT_STATISTICS_KEY,keySuffix,i1);
            int i2 =  rand.nextInt(max - min + 1) + min;
            statisticsService.increaseDailyStatisticsWithValue(DAILY_USER_STATISTICS_KEY,keySuffix,i2);

        }
    }
}
