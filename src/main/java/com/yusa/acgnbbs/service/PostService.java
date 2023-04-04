package com.yusa.acgnbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.vo.PostDetailsVO;
import com.yusa.acgnbbs.vo.PostSubmitVO;
import org.springframework.stereotype.Service;

/**
 * @author Yusa
 * @version 1.0
 */
@Service
public interface PostService extends IService<Post> {
    ResponseResult postList() ;
    ResponseResult hotPostList();

    /**
     * 四个列表
    **/
    ResponseResult animationPostList(int pageNum, int pageSize);
    ResponseResult comicPostList(int pageNum, int pageSize);
    ResponseResult gamePostList(int pageNum, int pageSize);
    ResponseResult novelPostList(int pageNum, int pageSize);
    ResponseResult userPostList(int pageNum, int pageSize,int userId);


    /**
     * 帖子详情
     */
    ResponseResult postDetails(int id);
//    ResponseResult animationPostList(Integer pageNum, Integer pageSize, Long categoryId);
    ResponseResult addPost(Post post);

    ResponseResult getWritePost(int postId);
    ResponseResult deletePost(int postId);
}
