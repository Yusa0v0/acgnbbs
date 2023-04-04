package com.yusa.acgnbbs.service;

public interface PageViewService {
     void addDailyPageViews(int year,int month,int day);
     Integer getDailyPageViews(int year,int month,int day);
     Integer getMonthPageViews(int year,int month);
     Integer getYearPageViews(int year);
}
