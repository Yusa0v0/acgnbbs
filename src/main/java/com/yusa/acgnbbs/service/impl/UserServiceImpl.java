package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.ScoreService;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_SCORE_SET;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SecurityUitl securityUitl;
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
            User originUser = userMapper.selectById(serverUserId);
            redisZSetRankUtil.init(USER_SCORE_SET,serverUserId);

            // 修改信息
            int updateNum = userMapper.updateById(user);
            User updateUser = userMapper.selectById(serverUserId);

            if(updateNum>0){
                redisZSetRankUtil.updateUserInfo(originUser,updateUser);
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
    public String getUsername(int id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        String username = user.getUsername();
        return username;
    }


}
