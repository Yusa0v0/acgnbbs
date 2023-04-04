package com.yusa.acgnbbs.vo;

import java.util.Date;

public class WritePostVO {
    private Integer id;
    private String title;
    private Integer categoryId;
    private String content;
    private String cover;

    public WritePostVO() {
    }

    public WritePostVO(Integer id, String title, Integer categoryId, String content, String cover) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.content = content;
        this.cover = cover;
    }

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "WritePostVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
