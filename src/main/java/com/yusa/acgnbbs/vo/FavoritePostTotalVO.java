package com.yusa.acgnbbs.vo;

import java.util.List;

public class FavoritePostTotalVO {
    private List<FavoritePostVO> favoritePostList;
    private Long total;

    public FavoritePostTotalVO() {
    }

    public FavoritePostTotalVO(List<FavoritePostVO> favoritePostList, Long total) {
        this.favoritePostList = favoritePostList;
        this.total = total;
    }

    public List<FavoritePostVO> getFavoritePostList() {
        return favoritePostList;
    }

    public void setFavoritePostList(List<FavoritePostVO> favoritePostList) {
        this.favoritePostList = favoritePostList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FavoritePostTotalVO{" +
                "favoritePostList=" + favoritePostList +
                ", total=" + total +
                '}';
    }
}
