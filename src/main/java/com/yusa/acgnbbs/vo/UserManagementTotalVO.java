package com.yusa.acgnbbs.vo;

import java.util.List;

public class UserManagementTotalVO {
    List<UserManagementVO> userManagementList;
    Long total;

    public UserManagementTotalVO(List<UserManagementVO> userManagementList, Long total) {
        this.userManagementList = userManagementList;
        this.total = total;
    }

    public UserManagementTotalVO() {
    }

    public List<UserManagementVO> getUserManagementList() {
        return userManagementList;
    }

    public void setUserManagementList(List<UserManagementVO> userManagementList) {
        this.userManagementList = userManagementList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
