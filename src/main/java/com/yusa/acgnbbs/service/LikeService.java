package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface LikeService {
    ResponseResult userLike(int userId);
    ResponseResult addLike(int userId,int postId);
    ResponseResult deleteLike(int userId,int postId);
    Boolean checkLiked(int userId, int postId);
}
