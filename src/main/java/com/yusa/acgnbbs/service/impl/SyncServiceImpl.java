package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.domain.entity.Follow;
import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.CommentMapper;
import com.yusa.acgnbbs.mapper.FollowMapper;
import com.yusa.acgnbbs.mapper.PostMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.PostService;
import com.yusa.acgnbbs.service.SyncService;
import com.yusa.acgnbbs.utils.RedisZSetRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yusa.acgnbbs.constants.SystemConstants.*;

@Service
public class SyncServiceImpl implements SyncService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FollowMapper followMapper;
    @Autowired
    RedisZSetRankUtil redisZSetRankUtil;
    @Override
    public void SyncPostNum() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper =new LambdaQueryWrapper<>();
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        redisZSetRankUtil.init(USER_POST_NUM_SET,1);
        redisZSetRankUtil.batchDel();
        for (User user: users) {
            LambdaQueryWrapper<Post> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Post::getAuthorId,user.getId());
            Integer postNum = postMapper.selectCount(lambdaQueryWrapper);
            System.out.println("userId: "+user.getId()+"  postNum:" +postNum);
            redisZSetRankUtil.init(USER_POST_NUM_SET,user.getId());
            redisZSetRankUtil.fetchScore(postNum.doubleValue());
        }
    }

    @Override
    public void SyncCommentNum() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper =new LambdaQueryWrapper<>();
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        redisZSetRankUtil.init(USER_COMMENT_NUM_SET,1);
        redisZSetRankUtil.batchDel();
        for (User user: users) {
            LambdaQueryWrapper<Comment> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Comment::getUserId,user.getId());
            Integer postNum = commentMapper.selectCount(lambdaQueryWrapper);
            System.out.println("userId: "+user.getId()+"  postNum:" +postNum);
            redisZSetRankUtil.init(USER_COMMENT_NUM_SET,user.getId());
            redisZSetRankUtil.fetchScore(postNum.doubleValue());
        }
    }
    @Override
    public void SyncFanNum(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper =new LambdaQueryWrapper<>();
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        redisZSetRankUtil.init(USER_FAN_NUM_SET,1);
        redisZSetRankUtil.batchDel();
        for (User user: users) {
            LambdaQueryWrapper<Follow> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Follow::getFollowedId,user.getId()).eq(Follow::getStatus,1);
            Integer fanNum = followMapper.selectCount(lambdaQueryWrapper);
            redisZSetRankUtil.init(USER_FAN_NUM_SET,user.getId());
            redisZSetRankUtil.fetchScore(fanNum.doubleValue());
        }
    }
}
