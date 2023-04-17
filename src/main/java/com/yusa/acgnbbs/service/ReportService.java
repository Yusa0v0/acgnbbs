package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Report;

public interface ReportService {
    ResponseResult report(Report report);
    ResponseResult reportList(int currentPage, int pageSize);
    ResponseResult handleReport(int reportId);
}
