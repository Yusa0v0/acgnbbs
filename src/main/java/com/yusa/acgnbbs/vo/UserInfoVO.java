package com.yusa.acgnbbs.vo;

public class UserInfoVO
{
    public UserInfoVO() {
    }
    public UserInfoVO(UserInfoVO userInfoVO) {
        this.id = userInfoVO.id;
        this.username =  userInfoVO.username;
        this.gender =  userInfoVO.gender;
        this.bio =  userInfoVO.bio;
        this.avatar =  userInfoVO.avatar;
    }

    public UserInfoVO(Integer id, String username, Integer gender, String bio, String avatar) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.bio = bio;
        this.avatar = avatar;
    }

    private Integer id;
    private String username;

    private Integer gender;

    private String bio;

    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", bio='" + bio + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
