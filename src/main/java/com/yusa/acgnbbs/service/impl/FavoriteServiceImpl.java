package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Favorite;
import com.yusa.acgnbbs.mapper.FavoriteMapper;
import com.yusa.acgnbbs.service.FavoriteService;
import com.yusa.acgnbbs.utils.SecurityUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    SecurityUitl securityUitl;

    @Override
    public ResponseResult userFavoriteList(int userId) {
        LambdaQueryWrapper<Favorite>  lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getUserId,userId);
        List<Favorite> favorites = favoriteMapper.selectList(lambdaQueryWrapper);
        return new ResponseResult(200,"OK",favorites);
    }

    @Override
    public Boolean checkFavorite(int userId, int postId) {
        LambdaQueryWrapper<Favorite> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getUserId,userId);
        lambdaQueryWrapper.eq(Favorite::getPostId,postId);
        Favorite favorite = favoriteMapper.selectOne(lambdaQueryWrapper);
        Boolean isFavorited=true;
        if(Objects.isNull(favorite)){
            isFavorited=false;
        }
        return isFavorited;
    }

    @Override
    public ResponseResult addFavorite(int userId, int postId) {
        int loginId = securityUitl.getUserId();
        if(userId!=loginId){
            return new ResponseResult(403,"登录状态异常",null);
        }
        Favorite favorite=new Favorite();
        favorite.setUserId(userId);
        favorite.setPostId(postId);
        favoriteMapper.insert(favorite);
        return new ResponseResult(200,"OK",null);
    }

    @Override
    public ResponseResult deleteFavorite(int userId,int postIds) {
        int loginId = securityUitl.getUserId();
        if(userId!=loginId){
            return new ResponseResult(403,"登录状态异常",null);
        }
        LambdaQueryWrapper<Favorite> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getUserId,userId);
        favoriteMapper.delete(lambdaQueryWrapper);
        return new ResponseResult(200,"OK",null);
    }
}
