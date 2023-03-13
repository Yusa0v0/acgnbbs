package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;
    @GetMapping("/favorite/addFavorite/{userId}/{postId}")
    public ResponseResult addFavorite(@PathVariable("userId") int userId,@PathVariable("postId") int postId){
        return favoriteService.addFavorite(userId,postId);
    }
    @GetMapping("/favorite/deleteFavorite/{userId}/{postId}")
    public ResponseResult deleteFavorite(@PathVariable("userId") int userId,@PathVariable("postId") int postId){
        return favoriteService.deleteFavorite(userId,postId);
    }

}
