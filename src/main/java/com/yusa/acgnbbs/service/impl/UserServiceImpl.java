package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.utils.SecurityUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SecurityUitl securityUitl;
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
        return new ResponseResult(200,"OK",user);
    }
}
