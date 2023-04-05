package com.yusa.acgnbbs;

import com.alibaba.fastjson.JSON;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.UserService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;
import static com.yusa.acgnbbs.constants.SystemConstants.USER_SIGN_KEY;

@SpringBootTest
class AcgnbbsApplicationTests {
    @Autowired
    SecurityUitl securityUitl;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;

    @Test
    void contextLoads() {
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RedisCache redisCache;
    @Test
    public void testBcrypt(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        String encode2 = passwordEncoder.encode("jcy112");
        System.out.println(encode2);
        boolean matches = passwordEncoder.matches("jcy112", "$2a$10$5KWQD/B76oAaz0zszIo8euYUfhqlUpmdozMmnFvct6GY26RhwnwVe");
        System.out.println(matches);
    }

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
        redisZSetRankUtil.init(USER_SCORE_SET,11);
        redisZSetRankUtil.getUserScore();
    }

}
