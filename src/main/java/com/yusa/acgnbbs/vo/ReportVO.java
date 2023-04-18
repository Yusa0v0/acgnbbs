package com.yusa.acgnbbs.vo;

import com.yusa.acgnbbs.domain.entity.Report;

import java.util.Date;

public class ReportVO  extends Report {
    Boolean isUserBanned;
    Boolean isPostDeleted;

    public Boolean getUserBanned() {
        return isUserBanned;
    }

    public void setUserBanned(Boolean userBanned) {
        isUserBanned = userBanned;
    }

    public Boolean getPostDeleted() {
        return isPostDeleted;
    }

    public void setPostDeleted(Boolean postDeleted) {
        isPostDeleted = postDeleted;
    }

    public ReportVO() {
    }

    public ReportVO(Report report, Boolean isUserBanned, Boolean isPostDeleted) {
        super(report);
        this.isUserBanned = isUserBanned;
        this.isPostDeleted = isPostDeleted;
    }
}
