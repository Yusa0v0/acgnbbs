package com.yusa.acgnbbs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * (Comments)实体类
 *
 * @author makejava
 * @since 2023-02-27 21:21:42
 */
@TableName("comment")

public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)

    private Integer id;
    
    private Integer userId;
    
    private Integer postId;
    
    private String content;
    
    private Integer likeTimes;
    
    private Date createdAt;
    @TableLogic
    private Integer delFlag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikeTimes() {
        return likeTimes;
    }

    public void setLikeTimes(Integer likeTimes) {
        this.likeTimes = likeTimes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}

