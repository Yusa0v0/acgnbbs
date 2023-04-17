package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.domain.entity.Report;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.service.PostService;
import com.yusa.acgnbbs.vo.PostDetailsVO;
import com.yusa.acgnbbs.vo.PostSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yusa.acgnbbs.domain.ResponseResult;

import static com.yusa.acgnbbs.constants.SystemConstants.POST_PAGE_SIZE;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping("/postList")
    public ResponseResult PostList(){
         return postService.postList();
    }

    @GetMapping("/animationPostList/{pageNum}")
    public ResponseResult animationPostList(@PathVariable("pageNum") int pageNum){
        return postService.animationPostList(pageNum,POST_PAGE_SIZE);
    }
    @GetMapping("/comicPostList/{pageNum}")
    public ResponseResult comicPostList(@PathVariable("pageNum") int pageNum){
        return postService.comicPostList(pageNum,POST_PAGE_SIZE);
    }

    @GetMapping("/gamePostList/{pageNum}")
    public ResponseResult gamePostList(@PathVariable("pageNum") int pageNum){
        return postService.gamePostList(pageNum,POST_PAGE_SIZE);
    }

    @GetMapping("/novelPostList/{pageNum}")
    public ResponseResult novelPostList(@PathVariable("pageNum") int pageNum){
        return postService.novelPostList(pageNum,POST_PAGE_SIZE);
    }

    @GetMapping("/userPostList/{userId}/{currentPage}/{pageSize}")
    public ResponseResult userPostList(@PathVariable("userId") int userId,@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize){
        return postService.userPostList(currentPage,pageSize,userId);
    }

    @GetMapping("/postDetails/{id}")
    public ResponseResult postDetails(@PathVariable("id") int id){
        return postService.postDetails(id);
    }

    @PostMapping("/addPost")
    public ResponseResult addPost(@RequestBody Post post){
        return postService.addPost(post);
    }
    @GetMapping("/getWritePost/{postId}")
    public ResponseResult getWritePost(@PathVariable("postId") int postId){
        return postService.getWritePost(postId);
    }
    @GetMapping("/deletePost/{userId}/{postId}")
//    @PreAuthorize("hasAuthority('aAdmin')")
    public ResponseResult deletePost(@PathVariable("userId") int userId,@PathVariable("postId") int postId){
        return postService.deletePost(userId,postId);
    }


}
