package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Favorite;
import com.yusa.acgnbbs.domain.entity.Likes;
import com.yusa.acgnbbs.mapper.LikesMapper;
import com.yusa.acgnbbs.service.LikeService;
import com.yusa.acgnbbs.utils.SecurityUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikesMapper likesMapper;
    @Autowired
    SecurityUitl securityUitl;
    @Override
    public ResponseResult userLike(int userId) {
        return null;
    }

    @Override
    public ResponseResult addLike(int userId, int postId) {
        Boolean checkUserId = securityUitl.checkUserId(userId);
        if(checkUserId) {
            Likes likes = new Likes();
            likes.setUserId(userId);
            likes.setPostId(postId);
            likesMapper.insert(likes);
            return new ResponseResult<>(200,"OK",null);
        }
        return new ResponseResult<>(403,"error",null);
    }

    @Override
    public ResponseResult deleteLike(int userId, int postId) {
        Boolean checkUserId = securityUitl.checkUserId(userId);
        if(checkUserId) {
            Likes likes = new Likes();
            likes.setUserId(userId);
            likes.setPostId(postId);
            LambdaQueryWrapper<Likes> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Likes::getPostId,postId);
            lambdaQueryWrapper.eq(Likes::getUserId,userId);
            likesMapper.delete(lambdaQueryWrapper);
            return new ResponseResult<>(200,"OK",null);
        }
        return new ResponseResult<>(403,"error",null);
    }

    @Override
    public Boolean checkLiked(int userId, int postId) {
        LambdaQueryWrapper<Likes> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Likes::getUserId,userId);
        lambdaQueryWrapper.eq(Likes::getPostId,postId);
        List<Likes> likes = likesMapper.selectList(lambdaQueryWrapper);
        Boolean isLiked=true;
        if(likes.size()==0){
            isLiked=false;
        }
        return isLiked;
    }
}
