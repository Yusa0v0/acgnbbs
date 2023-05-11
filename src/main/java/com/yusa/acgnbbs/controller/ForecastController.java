package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.FavoriteService;
import com.yusa.acgnbbs.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yusa.acgnbbs.constants.SystemConstants.*;

@RestController
@RequestMapping("/forecast")
public class ForecastController {
    @Autowired
    ForecastService favoriteService;
    @GetMapping("/lastWeekCommentForecast")
    public ResponseResult getLastWeekCommentStatistics(){
        return favoriteService.getLastWeekForecast(DAILY_COMMENT_STATISTICS_KEY);
    }
    @GetMapping("/lastWeekUserForecast")
    public ResponseResult getLastWeekUserStatistics(){
        return favoriteService.getLastWeekForecast(DAILY_USER_STATISTICS_KEY);
    }

    @GetMapping("/lastWeekNewUserForecast")
    public ResponseResult getLastWeekNewUserStatistics(){
        return favoriteService.getLastWeekForecast(NEW_USER_STATISTICS_KEY);
    }
    @GetMapping("/lastWeekUserSignForecast")
    public ResponseResult getLastWeekUserSignStatistics(){
        return favoriteService.getLastWeekForecast(USER_SIGN_STATISTICS_KEY);
    }
}
