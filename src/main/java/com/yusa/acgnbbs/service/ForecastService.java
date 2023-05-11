package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;

public interface ForecastService {
    ResponseResult getLastWeekForecast(String key);
}
