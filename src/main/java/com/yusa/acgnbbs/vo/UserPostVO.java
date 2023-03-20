package com.yusa.acgnbbs.vo;

public class UserPostVO {
    private Integer id;
    private String title;
    private Integer categoryId;
    private Integer authorId;
    private String authorName;

    public UserPostVO() {
    }

    public UserPostVO(Integer id, String title, Integer categoryId, Integer authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.authorName = authorName;
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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
