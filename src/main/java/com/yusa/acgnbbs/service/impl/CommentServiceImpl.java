package com.yusa.acgnbbs.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.CommentMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.CommentService;
import com.yusa.acgnbbs.service.UserService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.RegexValidateUtil;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    SecurityUitl securityUitl;
    @Override
    public ResponseResult postComment(int currentPage, int pageSize,int postId) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        long total = page.getTotal();
        System.out.println(total);
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getPostId, postId);
        List<Comment> comments = list(lambdaQueryWrapper);
        // 转换成VO
        List<CommentVO> commentVOList = BeanCopyUtils.copyBeanList(comments, CommentVO.class);
        for (CommentVO commentVO: commentVOList) {
            Integer userId = commentVO.getUserId();
            String userAvatar = userService.getUserAvatar(userId);
            String username =userService.getUsername(userId);
            commentVO.setAvatar(userAvatar);
            commentVO.setUsername(username);
        }

        return ResponseResult.okResult(commentVOList);
    }

    @Override
    public ResponseResult userComment(int currentPage, int pageSize,int userId) {
        PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getUserId, userId);
        List<Comment> comments = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(comments, CommentVO.class));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        int frontId = comment.getUserId();
        int loginId = securityUitl.getUserId();
        if(frontId!=loginId){
            return new ResponseResult(403,"评论失败",null);
        }
        comment.setUserId(loginId);
        comment.setLikeTimes(0);
        comment.setDelFlag(0);
        commentMapper.insert(comment);
        return new ResponseResult(200,"OK",null);
    }
}
