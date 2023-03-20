package com.yusa.acgnbbs.vo;

public class FavoritePostVO {
    private Integer id;
    private String title;
    private Integer categoryId;
    private Integer authorId;
    private String authorName;

    public FavoritePostVO() {
    }

    public FavoritePostVO(Integer id, String title, Integer categoryId, Integer authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public FavoritePostVO(Integer id, String title, Integer authorId, String authorName) {
        this.id = id;
        this.title = title;
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

    @Override
    public String toString() {
        return "FavoritePostVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
