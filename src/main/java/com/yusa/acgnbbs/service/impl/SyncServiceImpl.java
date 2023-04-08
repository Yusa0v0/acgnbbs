package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.CommentMapper;
import com.yusa.acgnbbs.mapper.PostMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.PostService;
import com.yusa.acgnbbs.service.SyncService;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yusa.acgnbbs.constants.SystemConstants.USER_COMMENT_NUM_SET;
import static com.yusa.acgnbbs.constants.SystemConstants.USER_POST_NUM_SET;

@Service
public class SyncServiceImpl implements SyncService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Override
    public void SyncPostNum() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper =new LambdaQueryWrapper<>();
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        System.out.println();
        for (User user: users) {
            LambdaQueryWrapper<Post> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Post::getAuthorId,user.getId());
            Integer postNum = postMapper.selectCount(lambdaQueryWrapper);
            System.out.println("userId: "+user.getId()+"  postNum:" +postNum);
            redisZSetRankUtil.init(USER_POST_NUM_SET,user.getId());
            System.out.println(postNum.doubleValue());
            redisZSetRankUtil.fetchScore(postNum.doubleValue());
        }
    }

    @Override
    public void SyncCommentNum() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper =new LambdaQueryWrapper<>();
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        System.out.println();
        for (User user: users) {
            LambdaQueryWrapper<Comment> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Comment::getUserId,user.getId());
            Integer postNum = commentMapper.selectCount(lambdaQueryWrapper);
            System.out.println("userId: "+user.getId()+"  postNum:" +postNum);
            redisZSetRankUtil.init(USER_COMMENT_NUM_SET,user.getId());
            System.out.println(postNum.doubleValue());
            redisZSetRankUtil.fetchScore(postNum.doubleValue());
        }
    }
}
