package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface StatisticsService {

    void increaseDailyStatistics(String key);
    void increaseDailyStatisticsWithValue(String key,String day,long value);

    ResponseResult getDailyStatistics(String key,String day);
    ResponseResult getLastWeekStatistics(String key);


}
