package com.yusa.acgnbbs.service.impl;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.SingleSmoothingEntity;
import com.yusa.acgnbbs.service.ForecastService;
import com.yusa.acgnbbs.service.StatisticsService;
import com.yusa.acgnbbs.utils.SingleExponentialSmoothingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yusa.acgnbbs.constants.SystemConstants.DAILY_USER_STATISTICS_KEY;

@Service
public class ForecastServiceImpl implements ForecastService {
    @Autowired
    StatisticsService statisticsService;
    @Override
    public ResponseResult getLastWeekForecast(String key) {
        List<Integer> lastWeek = (List<Integer>) (statisticsService.getLastWeekStatistics(key).getData());
        List<Double> lastWeekDouble =new ArrayList<>();
        Double lastPredictParam = new Double(0);
        List<Integer> lastForecast=new ArrayList<>();
        SingleSmoothingEntity singleSmoothingEntity = new SingleSmoothingEntity();
        for (Integer j:lastWeek){
            lastWeekDouble.add(j.doubleValue());
        }
        for (int i = 0; i < 7; i++) {
            for (Double j:lastWeekDouble){
                lastPredictParam+=j;
            }
            lastPredictParam/=lastWeekDouble.size();
            singleSmoothingEntity.setRealDataList(lastWeekDouble);
            singleSmoothingEntity.setLastPredictParam(lastPredictParam);
            Double result = SingleExponentialSmoothingUtil.singleExponentialSmoothingMethod(singleSmoothingEntity);
            System.out.println("result:"+result);
            lastPredictParam=0.0;
            lastWeekDouble.remove(0);
            lastWeekDouble.add(result);
            lastForecast.add(result.intValue());
        }
        System.out.println(lastForecast);
        return ResponseResult.okResult(lastForecast);
    }
}
