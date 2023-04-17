package com.yusa.acgnbbs.vo;

import com.yusa.acgnbbs.domain.entity.Notice;
import com.yusa.acgnbbs.domain.entity.Report;

import java.util.List;

public class ReportTotalVO {
    List<Report> reportList;
    Long total;

    public ReportTotalVO() {
    }

    public ReportTotalVO(List<Report> reportList, Long total) {
        this.reportList = reportList;
        this.total = total;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
