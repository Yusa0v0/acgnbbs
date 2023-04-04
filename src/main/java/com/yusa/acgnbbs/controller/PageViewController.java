package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pageViews")
public class PageViewController {
    @Autowired
    PageViewService pageViewService;
    @GetMapping("/addDailyPageViews/{year}/{month}/{day}")
    public ResponseResult addDailyPageViews(@PathVariable("year") int year,@PathVariable("month") int month,@PathVariable("day") int day){
        pageViewService.addDailyPageViews(year,month,day);
        return new  ResponseResult(200,"OK",pageViewService.getDailyPageViews(year,month,day));
    }

    @GetMapping("/getDailyPageViews/{year}/{month}/{day}")
    public ResponseResult getDailyPageViews(@PathVariable("year") int year,@PathVariable("month") int month,@PathVariable("day") int day){
        return new  ResponseResult(200,"OK",year);
    }
    @GetMapping("/getMonthPageViews/{year}/{month}")
    public ResponseResult getMonthPageViews(@PathVariable("year") int year,@PathVariable("month") int month){
        return new  ResponseResult(200,"OK",pageViewService.getMonthPageViews(year,month));
    }
    @GetMapping("/getYearPageViews/{year}")
    public ResponseResult getYearPageViews(@PathVariable("year") int year){
        return new   ResponseResult(200,"OK",pageViewService.getYearPageViews(year));
    }


}
