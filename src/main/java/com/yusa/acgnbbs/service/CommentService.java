package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;


public interface CommentService {
    ResponseResult postComment(int currentPages, int pageSize,int postId);
    ResponseResult userComment(int currentPage, int pageSize,int userId);
    ResponseResult addComment(Comment comment);

}
