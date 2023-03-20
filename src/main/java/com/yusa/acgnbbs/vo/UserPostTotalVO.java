package com.yusa.acgnbbs.vo;

import java.util.List;

public class UserPostTotalVO {
    private List<UserPostVO> userPostList;
    private Long total;

    public UserPostTotalVO() {
    }

    public UserPostTotalVO(List<UserPostVO> userPostList, Long total) {
        this.userPostList = userPostList;
        this.total = total;
    }

    public List<UserPostVO> getUserPostList() {
        return userPostList;
    }

    public void setUserPostList(List<UserPostVO> userPostList) {
        this.userPostList = userPostList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserPostTotalVo{" +
                "userPostList=" + userPostList +
                ", total=" + total +
                '}';
    }
}
