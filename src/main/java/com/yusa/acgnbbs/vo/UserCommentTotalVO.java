package com.yusa.acgnbbs.vo;

import java.util.List;

public class UserCommentTotalVO {
    List<UserCommentVO> userCommentList;
    Long total;

    public UserCommentTotalVO(List<UserCommentVO> userCommentList, Long total) {
        this.userCommentList = userCommentList;
        this.total = total;
    }

    public List<UserCommentVO> getUserCommentList() {
        return userCommentList;
    }

    public void setUserCommentList(List<UserCommentVO> userCommentList) {
        this.userCommentList = userCommentList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UserCommentTotalVO{" +
                "userCommentList=" + userCommentList +
                ", total=" + total +
                '}';
    }
}
