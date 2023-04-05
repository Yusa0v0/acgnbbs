package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface RankService {
    public ResponseResult getSignNumRank();
    public ResponseResult getScoreRank();
    public ResponseResult getFanNumRank();
    public ResponseResult getPageViewRank();


}
