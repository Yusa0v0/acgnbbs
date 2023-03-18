package com.yusa.acgnbbs.vo;

import java.util.Date;

public class PostListVO {
    private Integer id;
    private String title;
    private String avatar;
    private Integer viewTimes;
    private Integer authorId;
    private String authorName;
    private Date createdAt;

    private Date updateAt;
    private String summary;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }



    @Override
    public String toString() {
        return "PostVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", viewTimes=" + viewTimes +
                ", authorId=" + authorId +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", summary='" + summary + '\'' +
                '}';
    }
}
