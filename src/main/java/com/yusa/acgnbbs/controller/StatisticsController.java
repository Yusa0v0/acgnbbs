package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.FavoriteService;
import com.yusa.acgnbbs.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yusa.acgnbbs.constants.SystemConstants.*;

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

    @GetMapping("/lastWeekNewUserStatistics")
    public ResponseResult getLastWeekNewUserStatistics(){
        return statisticsService.getLastWeekStatistics(NEW_USER_STATISTICS_KEY);
    }
    @GetMapping("/lastWeekUserSignStatistics")
    public ResponseResult getLastUserSignStatistics(){
        return statisticsService.getLastWeekStatistics(USER_SIGN_STATISTICS_KEY);
    }


    // 帖子种类浏览量分析
    @GetMapping("/getLastWeekAnimationPageViewStatistics")
    public ResponseResult getLastWeekAnimationPageViewStatistics(){
        return statisticsService.getLastWeekStatistics(ANIMATION_PAGE_VIEW_KEY);
    }
    @GetMapping("/getLastWeekComicPageViewStatistics")
    public ResponseResult getLastWeekComicPageViewStatistics(){
        return statisticsService.getLastWeekStatistics(COMIC_PAGE_VIEW_KEY);
    }
    @GetMapping("/getLastWeekGamePageViewStatistics")
    public ResponseResult getLastWeekGamePageViewStatistics(){
        return statisticsService.getLastWeekStatistics(GAME_PAGE_VIEW_KEY);
    }

    @GetMapping("/getLastWeekNovelPageViewStatistics")
    public ResponseResult getLastWeekNovelPageViewStatistics(){
        return statisticsService.getLastWeekStatistics(NOVEL_PAGE_VIEW_KEY);
    }

}
