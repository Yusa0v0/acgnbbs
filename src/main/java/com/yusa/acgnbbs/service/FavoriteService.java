package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface FavoriteService {
    ResponseResult userFavorite(int userId);
    ResponseResult addFavorite(int userId,int postId);
    ResponseResult deleteFavorite(int userId,int postId );

}
