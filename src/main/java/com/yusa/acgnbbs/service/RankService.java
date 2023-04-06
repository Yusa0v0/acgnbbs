package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface RankService {

    ResponseResult getScoreRankList(int start,int end);
    ResponseResult getSignNumRankList(int start,int end);

    ResponseResult getFanNumRankList(int start,int end);
    ResponseResult getPageViewRankList(int start,int end);


}
