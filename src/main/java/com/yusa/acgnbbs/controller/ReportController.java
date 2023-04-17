package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Report;
import com.yusa.acgnbbs.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportService reportService;
    @PostMapping("/report")
    public ResponseResult report(@RequestBody Report report){
        return reportService.report(report);
    }
    @GetMapping("/reportList/{currentPage}/{pageSize}")
    public ResponseResult reportList(@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize){
        return reportService.reportList(currentPage,pageSize);
    }
    @GetMapping("/handleReport/{reportId}")
    public ResponseResult handleReport(@PathVariable("reportId") int reportId){
        return reportService.handleReport(reportId);
    }
}
