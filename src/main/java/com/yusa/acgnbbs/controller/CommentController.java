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
    @GetMapping("/postCommentList/{postId}/{currentPage}/{pageSize}")
    public ResponseResult postCommentList(@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize,@PathVariable("postId") int postId){
        return commentService.postComment( currentPage,  pageSize,postId);
    }
    @GetMapping("/userCommentList/{userId}/{currentPage}/{pageSize}")
    public ResponseResult userCommentList(@PathVariable("currentPage") int currentPage,@PathVariable("pageSize")int pageSize,@PathVariable("userId") int userId){
        return commentService.userComment(currentPage,pageSize,userId);
    }
    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
