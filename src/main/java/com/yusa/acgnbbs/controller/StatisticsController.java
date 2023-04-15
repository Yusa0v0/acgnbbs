package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yusa.acgnbbs.constants.SystemConstants.DAILY_COMMENT_STATISTICS_KEY;
import static com.yusa.acgnbbs.constants.SystemConstants.DAILY_USER_STATISTICS_KEY;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;
    @GetMapping("/lastWeekCommentStatistics")
    public ResponseResult getLastWeekCommentStatistics(){
        return statisticsService.getLastWeekStatistics(DAILY_COMMENT_STATISTICS_KEY);
    }
    @GetMapping("/lastWeekUserStatistics")
    public ResponseResult getLastWeekUserStatistics(){
        return statisticsService.getLastWeekStatistics(DAILY_USER_STATISTICS_KEY);
    }
}
