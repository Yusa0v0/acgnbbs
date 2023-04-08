package com.yusa.acgnbbs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2023-03-11 16:37:35
 */
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    
    private Integer gender;
    
    private String email;
    
    private String bio;
    
    private String phone;
    
    private String password;
    
    private String avatar;
    private Integer isBanned;
    @TableLogic
    private Integer delFlag;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Integer isBanned) {
        this.isBanned = isBanned;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User() {
    }

    public User(Integer id, String username, Integer gender, String email, String bio, String phone, String password, Integer isBanned, Integer delFlag, String avatar) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.bio = bio;
        this.phone = phone;
        this.password = password;
        this.isBanned = isBanned;
        this.delFlag = delFlag;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", isBanned=" + isBanned +
                ", delFlag=" + delFlag +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

