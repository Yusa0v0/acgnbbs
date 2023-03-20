package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface FavoriteService {
    ResponseResult userFavoriteList(int currentPage,int pageSize,int userId);
    Boolean checkFavorite(int userId,int postId);

    ResponseResult addFavorite(int userId,int postId);
    ResponseResult deleteFavorite(int userId,int postId );

}
