package com.yusa.acgnbbs.service.impl;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;

public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeService likeService;
    @Override
    public ResponseResult userLike(int userId) {
        return null;
    }

    @Override
    public ResponseResult addLike(int userId, int postId) {
        return null;
    }
}
