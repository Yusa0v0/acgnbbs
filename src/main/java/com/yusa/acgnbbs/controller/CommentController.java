package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.yusa.acgnbbs.constants.SystemConstants.COMMENT_PAGE_SIZE;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/postCommentList/{postId}/{pageNum}")
    public ResponseResult postCommentList(@PathVariable("pageNum") int pageNum,@PathVariable("postId") int postId){
        return commentService.postComment( pageNum,  COMMENT_PAGE_SIZE,postId);
    }
    @GetMapping("/userCommentList/{userId}/{pageNum}")
    public ResponseResult userCommentList(@PathVariable("pageNum") int pageNum,@PathVariable("userId") int userId){
        return commentService.userComment(pageNum,COMMENT_PAGE_SIZE,userId);
    }
    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
