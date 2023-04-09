package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Follow;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.FollowMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.FollowService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import com.yusa.acgnbbs.vo.UserInfoTotalVO;
import com.yusa.acgnbbs.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_FAN_NUM_SET;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowMapper followMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;

    @Override
    public ResponseResult follow(int userId, int followedId) {
        if(userId==followedId){
            return  new ResponseResult<>(200,"can not follow yourself");
        }
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Follow::getUserId,userId);
        lambdaQueryWrapper.eq(Follow::getFollowedId,followedId);
        Follow followInfo = followMapper.selectOne(lambdaQueryWrapper);
        System.out.println(followInfo);
        // 如果没查询到关注信息，则插入信息
        if(Objects.isNull(followInfo)){
            Follow follow =new Follow();
            follow.setUserId(userId);
            follow.setFollowedId(followedId);
            follow.setStatus(1);
            Date date = new Date();
            follow.setCreateTime(date);
            follow.setUpdateTime(date);
            followMapper.insert(follow);
        }
        // 如果查到过关注信息，但是status为0，0表示未关注
        else if(followInfo.getStatus()==0){
            followInfo.setStatus(1);
            Date date = new Date();
            followInfo.setUpdateTime(date);
            followMapper.updateById(followInfo);
        }
        redisZSetRankUtil.init(USER_FAN_NUM_SET,followedId);
        redisZSetRankUtil.incrementScore(1);
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
            Date date = new Date();
            followInfo.setUpdateTime(date);
            followMapper.updateById(followInfo);
            redisZSetRankUtil.init(USER_FAN_NUM_SET,followedId);
            redisZSetRankUtil.incrementScore(-1);
            return new ResponseResult<>(200,"OK");
        }
        return new ResponseResult<>(403,"failed");
    }

    @Override
    public ResponseResult getIsFollowed(int fromId, int ToId) {
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Follow::getUserId,fromId);
        lambdaQueryWrapper.eq(Follow::getFollowedId,ToId);
        Follow followInfo = followMapper.selectOne(lambdaQueryWrapper);
        // 如果没查询到关注信息，则插入信息
        if(Objects.isNull(followInfo)){
            return ResponseResult.okResult(false);
        }
        if(followInfo.getStatus()==0){
            return ResponseResult.okResult(false);
        }
        return ResponseResult.okResult(true);

    }

    @Override
    public ResponseResult getFollowList(int userId,int currentPage,int pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);

        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Follow::getCreateTime).eq(Follow::getUserId,userId).eq(Follow::getStatus,1);
        List<Follow> followList = followMapper.selectList(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("total"+total);
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        for (Follow follow:followList) {
            Integer followedId = follow.getFollowedId();
            User user = userMapper.selectById(followedId);
            UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user, UserInfoVO.class);
            userInfoVOList.add(userInfoVO);
        }
        UserInfoTotalVO userInfoTotalVO = new UserInfoTotalVO(userInfoVOList, total);
        return new ResponseResult<>(200,"OK",userInfoTotalVO);
    }

    @Override
    public ResponseResult getFansList(int userId,int currentPage,int pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Follow::getCreateTime).eq(Follow::getFollowedId,userId).eq(Follow::getStatus,1);
        List<Follow> followList = followMapper.selectList(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("total"+total);
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        for (Follow follow:followList) {
            Integer fansId = follow.getUserId();
            User user = userMapper.selectById(fansId);
            UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user, UserInfoVO.class);
            userInfoVOList.add(userInfoVO);
        }
        UserInfoTotalVO userInfoTotalVO = new UserInfoTotalVO(userInfoVOList, total);
        return new ResponseResult<>(200,"OK",userInfoTotalVO);
    }
}
