package com.yusa.acgnbbs.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Comment;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.CommentMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.CommentService;
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
    SecurityUitl securityUitl;
    @Override
    public ResponseResult postComment(int pageNum, int pageSize,int postId) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getPostId, postId);
        List<Comment> comments = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(comments, CommentVO.class));
    }

    @Override
    public ResponseResult userComment(int pageNum, int pageSize,int userId) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getUserId, userId);
        List<Comment> comments = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(comments, CommentVO.class));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        int id = securityUitl.getUserId();
        comment.setUserId(id);
        comment.setLikeTimes(0);
        comment.setDelFlag(0);
        commentMapper.insert(comment);
        return new ResponseResult(200,"OK",null);
    }
}
