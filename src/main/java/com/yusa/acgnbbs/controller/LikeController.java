package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    LikeService likeService;
    @GetMapping("/addLike/{userId}/{postId}")
    public ResponseResult addLike(@PathVariable("userId")int userId,@PathVariable("postId")int postId){
        return likeService.addLike(userId,postId);
    }
    @GetMapping("/deleteLike/{userId}/{postId}")
    public ResponseResult deleteLike(@PathVariable("userId")int userId,@PathVariable("postId")int postId){
        return likeService.deleteLike(userId,postId);
    }

}
