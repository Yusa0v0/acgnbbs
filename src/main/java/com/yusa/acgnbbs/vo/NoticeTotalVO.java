package com.yusa.acgnbbs.vo;

import com.yusa.acgnbbs.domain.entity.Notice;

import java.util.List;

public class NoticeTotalVO {
    List<Notice> userInfoList;
    Long total;

    public NoticeTotalVO() {
    }

    public NoticeTotalVO(List<Notice> userInfoList, Long total) {
        this.userInfoList = userInfoList;
        this.total = total;
    }

    public List<Notice> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<Notice> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NoticeTotalVO{" +
                "userInfoList=" + userInfoList +
                ", total=" + total +
                '}';
    }
}
