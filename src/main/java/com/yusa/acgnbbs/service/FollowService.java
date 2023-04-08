package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface FollowService {
     ResponseResult follow(int userId,int followedId);
     ResponseResult cancelFollow(int userId,int followedId);
    ResponseResult getIsFollowed(int fromId,int ToId);
     ResponseResult getFollowList(int currentPage,int pageSize,int userId);
     ResponseResult getFansList(int currentPage,int pageSize,int userId);

}
