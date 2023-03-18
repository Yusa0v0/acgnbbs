package com.yusa.acgnbbs.vo;

import java.util.List;

public class PostListTotalVO {
    private List<PostListVO> postList;
    private Long total;

    public PostListTotalVO(List<PostListVO> postList, Long total) {
        this.postList = postList;
        this.total = total;
    }

    public List<PostListVO> getPostList() {
        return postList;
    }

    public void setPostList(List<PostListVO> postList) {
        this.postList = postList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
