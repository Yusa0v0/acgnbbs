package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.PostService;
import com.yusa.acgnbbs.service.RankService;
import com.yusa.acgnbbs.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rank")

public class RankController {
    @Autowired
    RankService rankService;
    @GetMapping("/scoreRankList")
    public ResponseResult getRankListWithScore(){
        return rankService.getScoreRankList(0,10);
    }
    @GetMapping("/signNumRankList")
    public ResponseResult getSignNumRankList(){
        return rankService.getSignNumRankList(0,10);
    }
    @GetMapping("/fanNumRankList")
    public ResponseResult getFanNumRankList(){
        return rankService.getFanNumRankList(0,10);
    }
    @GetMapping("/postNumRankList")
    public ResponseResult getPostNumRankList(){
        return rankService.getPostNumRankList(0,10);
    }
}
