package com.yusa.acgnbbs.vo;

public class UserCommentVO {
    private Integer id;
    private Integer userId;
    private Integer postId;
    private String postTitle;
    private String content;

    public UserCommentVO() {
    }

    public UserCommentVO(Integer id, Integer userId, Integer postId, String postTitle, String content) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.postTitle = postTitle;
        this.content = content;
    }

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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserCommentVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
