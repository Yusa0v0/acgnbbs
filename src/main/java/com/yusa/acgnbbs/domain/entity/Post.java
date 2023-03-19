package com.yusa.acgnbbs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * (Post)实体类
 *
 * @author makejava
 * @since 2023-02-26 20:32:15
 */
@TableName("post")
public class Post implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String title;
    
    private Integer viewTimes;
    
    private Integer authorId;
    
    private Integer categoryId;
    
    private String content;
    private String cover;
    private Date createdAt;
    
    private Date updateAt;
    
    private String summary;
    
    private Integer delFlag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Integer viewTimes) {
        this.viewTimes = viewTimes;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", viewTimes=" + viewTimes +
                ", authorId=" + authorId +
                ", categoryId=" + categoryId +
                ", content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", summary='" + summary + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}

