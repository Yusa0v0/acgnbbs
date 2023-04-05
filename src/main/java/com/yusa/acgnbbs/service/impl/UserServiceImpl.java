package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;
import static com.yusa.acgnbbs.constants.SystemConstants.USER_SIGN_KEY;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SecurityUitl securityUitl;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Override
    public ResponseResult setUserInfo(User user) {
        // 获取前端传的id
        int id = user.getId();
        // 获取token中的id
        int serverUserId = securityUitl.getUserId();
        //对比，如果相同，说明用户没有修改过localStorage
        if(id==serverUserId){
            // 修改信息
            int update = userMapper.updateById(user);
            if(update>0){
                return new ResponseResult(200,"修改成功");
            }
        }
        return new ResponseResult(200,"修改失败");
    }

    @Override
    public ResponseResult getUserInfo(int id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user,UserInfoVO.class);
        return new ResponseResult(200,"OK",userInfoVO);
    }
    public String getUserAvatar(int id){
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        String avatar = user.getAvatar();
        return avatar;
    }

    @Override
    public ResponseResult sign() {
        //1：获取当前登录用户。
        int userId = securityUitl.getUserId();
        System.out.println(userId);
        //2：获取当前日期。
        LocalDateTime now = LocalDateTime.now();
        //2.1获取当前日期中的  年和月
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        //3：拼接key  (当前用户id + 年月)
        String key = USER_SIGN_KEY + userId + keySuffix;
        System.out.println(key);
        //4：获取今天是当月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //5：存入redis   setbit key offset 1
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        redisZSetRankUtil.init(USER_SIGN_KEY,userId);
        redisZSetRankUtil.incrementScore(20);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getSigned(int userId) {
        LocalDateTime now = LocalDateTime.now();
        //2.1获取当前日期中的  年和月
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        //3：拼接key  (当前用户id + 年月)
        String key = USER_SIGN_KEY + userId + keySuffix;
        System.out.println(key);
        //4：获取今天是当月的第几天
        int dayOfMonth = now.getDayOfMonth();
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        System.out.println(result);
        if (result == null || result.isEmpty()) {
            // 没有任何签到结果
            return ResponseResult.okResult(false);
        }
        Long num = result.get(0);
        if (num == null || num == 0) {
            return ResponseResult.okResult(false);
        }
        // 6.循环遍历
        int count = 0;
        // 6.1.让这个数字与1做与运算，得到数字的最后一个bit位  // 判断这个bit位是否为0
        if ((num & 1) == 0) {
            // 如果为0，说明未签到，结束
            return ResponseResult.okResult(false);
        }
        return ResponseResult.okResult(true);
    }

    // 获取连续签到天数
    @Override
    public ResponseResult signCount(int userId) {
        // 1.获取当前登录用户
        System.out.println(userId);
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 5.获取本月截止今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202203 GET u14 0
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        System.out.println(result);
        if (result == null || result.isEmpty()) {
            // 没有任何签到结果
            return ResponseResult.okResult(0);
        }
        Long num = result.get(0);
        if (num == null || num == 0) {
            return ResponseResult.okResult(0);
        }
        // 6.循环遍历
        int count = 0;
        while (true) {
            // 6.1.让这个数字与1做与运算，得到数字的最后一个bit位  // 判断这个bit位是否为0
            if ((num & 1) == 0) {
                // 如果为0，说明未签到，结束
                break;
            }else {
                // 如果不为0，说明已签到，计数器+1
                count++;
            }
            // 把数字右移一位，抛弃最后一个bit位，继续下一个bit位
            num >>>= 1;
        }
        return ResponseResult.okResult(count);
    }

    // 月总签到数
    @Override
    public ResponseResult signMonthCount(int userId,String date) {
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        LocalDateTime dateOfSign = StringParseLocalDateTime(date);
        String keySuffix = dateOfSign.format(DateTimeFormatter.ofPattern(":yyyyMM"));
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
//        System.out.println(count.intValue());
        return ResponseResult.okResult(count.intValue());
    }

    @Override
    public ResponseResult signYearCount(int userId, int year) {
        int count =0;
        for (int month=1;month<=12;++month) {
            int day = 31;
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                day = 30;
            } else if (month == 2) {
                if(year%4==0) {
                    day = 29;
                }
                else {
                    day=28;
                }
            }
            String date = ""+year;
            if(month<10){
                date=date+"0";
            }
            date=date+month+day;
            System.out.println(date);
            Integer monthCount=(Integer) signMonthCount(userId, date).getData();
            if(!Objects.isNull(monthCount)) {
                count = count +monthCount ;
            }
        }
        return ResponseResult.okResult(count);
    }



    @Override
    public String getUsername(int id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        String username = user.getUsername();
        return username;
    }
    /**
     * 字符串日期转换成LocalDateTime
     *
     * @param date
     * @return
     * @throws
     */
    private LocalDateTime StringParseLocalDateTime(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date parse = simpleDateFormat.parse(date);
            Instant instant = parse.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            return instant.atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("日期格式转换报错");
        }
    }

}
