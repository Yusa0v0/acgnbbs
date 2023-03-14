package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Follow;
import com.yusa.acgnbbs.mapper.FollowMapper;
import com.yusa.acgnbbs.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowMapper followMapper;

    @Override
    public ResponseResult follow(int userId, int followedId) {
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Follow::getUserId,userId);
        lambdaQueryWrapper.eq(Follow::getFollowedId,followedId);
        Follow followInfo = followMapper.selectOne(lambdaQueryWrapper);
        // 如果没查询到关注信息
        if(Objects.isNull(followInfo)){
            return new ResponseResult<>(403,"failed");
        }
        if(followInfo.getStatus()==0){
            followInfo.setStatus(1);
            followMapper.updateById(followInfo);
            return new ResponseResult<>(200,"OK");
        }
        // 如果查到过关注信息，但是status为0
        Follow follow =new Follow();
        follow.setUserId(userId);
        follow.setFollowedId(followedId);
        follow.setStatus(1);
        followMapper.insert(follow);
        return  new ResponseResult<>(200,"OK");
    }

    @Override
    public ResponseResult cancelFollow(int userId, int followedId) {
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Follow::getUserId,userId);
        lambdaQueryWrapper.eq(Follow::getFollowedId,followedId);
        Follow followInfo = followMapper.selectOne(lambdaQueryWrapper);
        // 如果没查询到关注信息
        if(Objects.isNull(followInfo)){
            return new ResponseResult<>(403,"failed");
        }
        if(followInfo.getStatus()==1){
            followInfo.setStatus(0);
            followMapper.updateById(followInfo);
            return new ResponseResult<>(200,"OK");
        }
        return new ResponseResult<>(403,"failed");
    }

    @Override
    public ResponseResult getFollowList(int currentPage,int pageSize,int userId) {
        PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Follow::getUserId,userId);
        List<Follow> followList = followMapper.selectList(lambdaQueryWrapper);

        return new ResponseResult<>(200,"OK",followList);
    }

    @Override
    public ResponseResult getFansList(int currentPage,int pageSize,int userId) {
        PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Follow::getFollowedId,userId);
        List<Follow> followList = followMapper.selectList(lambdaQueryWrapper);
        return new ResponseResult<>(200,"OK",followList);
    }
}
