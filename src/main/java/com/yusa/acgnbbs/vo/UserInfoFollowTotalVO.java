package com.yusa.acgnbbs.vo;

import java.util.List;

public class UserInfoFollowTotalVO {
    List<UserInfoFollowVO> userInfoFollowList;
    Long total;

    public UserInfoFollowTotalVO(List<UserInfoFollowVO> userInfoFollowList, Long total) {
        this.userInfoFollowList = userInfoFollowList;
        this.total = total;
    }

    public UserInfoFollowTotalVO() {
    }

    public List<UserInfoFollowVO> getUserInfoFollowList() {
        return userInfoFollowList;
    }

    public void setUserInfoFollowList(List<UserInfoFollowVO> userInfoFollowList) {
        this.userInfoFollowList = userInfoFollowList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserInfoFollowTotalVO{" +
                "userInfoFollowList=" + userInfoFollowList +
                ", total=" + total +
                '}';
    }
}
