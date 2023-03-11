package com.yusa.acgnbbs.service;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;


public interface CommentService {
    ResponseResult postComment(int pageNum, int pageSize,int postId);
    ResponseResult userComment(int pageNum, int pageSize,int userId);
    ResponseResult addComment(Comment comment);

}
