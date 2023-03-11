package com.yusa.acgnbbs.vo;

public class PostSubmitVO {
    private String title;
    private Integer categoryId;
    private String content;
    private String summary;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public PostSubmitVO() {
    }

    public PostSubmitVO(String title, Integer categoryId, String content, String summary) {
        this.title = title;
        this.categoryId = categoryId;
        this.content = content;
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "PostSubmitVO{" +
                "title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
