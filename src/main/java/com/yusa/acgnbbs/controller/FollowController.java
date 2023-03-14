package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowService followService;
    @GetMapping("/addFollow/{userId}/{followedId}")
    public ResponseResult follow(@PathVariable("userId") int userId, @PathVariable("followedId") int followedId){
        return followService.follow(userId,followedId);
    }

    @GetMapping("/cancelFollow/{userId}/{followedId}")
    public ResponseResult cancelFollow(@PathVariable("userId") int userId, @PathVariable("followedId") int followedId){
        return followService.cancelFollow(userId,followedId);
    }
    @GetMapping("/followList/{userId}/{currentPage}/{pageSize}")
    public ResponseResult followList(@PathVariable("userId") int userId,@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize ){
        return followService.getFollowList(currentPage,pageSize,userId);
    }
    @GetMapping("/fansList/{userId}/{currentPage}/{pageSize}")
    public ResponseResult fansList(@PathVariable("userId") int userId,@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize ){
        return followService.getFansList(currentPage,pageSize,userId);
    }
}
