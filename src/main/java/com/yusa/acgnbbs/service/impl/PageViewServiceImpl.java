package com.yusa.acgnbbs.service.impl;

import com.yusa.acgnbbs.service.PageViewService;
import com.yusa.acgnbbs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PageViewServiceImpl implements PageViewService {
    @Autowired
    RedisCache redisCache;
    @Override
    public void addDailyPageViews(int year,int month,int day) {
        String key= "dailyPageViews-"+year+"-"+month+"-"+day;
        Integer dailyPageViews = redisCache.getCacheObject(key);
        if(dailyPageViews==null){
            redisCache.setCacheObject(key,1);
        }else{
            redisCache.incr(key,1);
        }
    }

    @Override
    public Integer getDailyPageViews(int year, int month, int day) {
        String key= "dailyPageViews-"+year+"-"+month+"-"+day;
        return redisCache.getCacheObject(key);
    }

    @Override
    public Integer getMonthPageViews(int year, int month) {
        String key= "monthPageViews-"+year+"-"+month;
        Integer monthPageViews=0;
        for(int i=1;i<=31;i++){
            Integer dailyPageViews = getDailyPageViews(year,month,i);
            if(!Objects.isNull(dailyPageViews)) {
                monthPageViews += dailyPageViews;
            }
        }
        return monthPageViews;
    }

    @Override
    public Integer getYearPageViews(int year) {
        String key= "yearPageViews-"+year;
        Integer yearPageViews=0;
        for(int i=1;i<=12;i++){
            Integer dailyPageViews = getMonthPageViews(year,i);
            if(!Objects.isNull(dailyPageViews)) {
                yearPageViews += dailyPageViews;
            }
        }
        return yearPageViews;
    }


}
