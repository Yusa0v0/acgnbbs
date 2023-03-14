package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface FollowService {
    public ResponseResult follow(int userId,int followedId);
    public ResponseResult cancelFollow(int userId,int followedId);
    public ResponseResult getFollowList(int currentPage,int pageSize,int userId);
    public ResponseResult getFansList(int currentPage,int pageSize,int userId);

}
