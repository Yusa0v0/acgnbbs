package com.yusa.acgnbbs.vo;

public class UserInfoFollowVO  extends UserInfoVO{
    private Boolean isFollowed;


    public UserInfoFollowVO(Boolean isFollowed) {
        this.isFollowed = isFollowed;
    }
    public UserInfoFollowVO(Boolean isFollowed,UserInfoVO userInfoVO) {
        super(userInfoVO);
        this.isFollowed = isFollowed;

    }
    public UserInfoFollowVO(Integer id, String username, Integer gender, String bio, String avatar, Boolean isFollowed) {
        super(id, username, gender, bio, avatar);
        this.isFollowed = isFollowed;
    }

    public Boolean getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(Boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    @Override
    public String toString() {
        return "UserInfoFollowVO{" +
                "isFollowed=" + isFollowed +
                '}';
    }
}
