package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Favorite;
import com.yusa.acgnbbs.mapper.FavoriteMapper;
import com.yusa.acgnbbs.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteMapper favoriteMapper;
    @Override
    public ResponseResult userFavorite(int userId) {
        LambdaQueryWrapper<Favorite>  lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getUserId,userId);
        List<Favorite> favorites = favoriteMapper.selectList(lambdaQueryWrapper);
        return new ResponseResult(200,"OK",favorites);
    }

    @Override
    public ResponseResult addFavorite(int userId, int postId) {
        Favorite favorite=new Favorite();
        favorite.setUserId(userId);
        favorite.setPostId(postId);
        favoriteMapper.insert(favorite);
        return new ResponseResult(200,"OK",null);
    }

    @Override
    public ResponseResult deleteFavorite(int userId) {
        return null;
    }
}
