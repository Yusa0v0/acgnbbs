package com.yusa.acgnbbs.vo;

import java.util.List;

public class UserInfoTotalVO {
    List<UserInfoVO> userInfoList;
    Long total;

    public UserInfoTotalVO() {
    }

    public UserInfoTotalVO(List<UserInfoVO> userInfoVOList, Long total) {
        this.userInfoList = userInfoVOList;
        this.total = total;
    }

    public List<UserInfoVO> getUserInfoVOList() {
        return userInfoList;
    }

    public void setUserInfoVOList(List<UserInfoVO> userInfoList) {
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
        return "UserInfoTotalVO{" +
                "userInfoList=" + userInfoList +
                ", total=" + total +
                '}';
    }
}
