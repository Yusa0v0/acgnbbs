package com.yusa.acgnbbs.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.*;
import com.yusa.acgnbbs.mapper.*;
import com.yusa.acgnbbs.service.FavoriteService;
import com.yusa.acgnbbs.service.LikeService;
import com.yusa.acgnbbs.service.PostService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.RegexValidateUtil;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.PostDetailsVO;
import com.yusa.acgnbbs.vo.PostSummaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.yusa.acgnbbs.constants.SystemConstants.*;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    LikesMapper likesMapper;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    LikeService likeService;
    @Autowired
    SecurityUitl securityUitl;
    @Override
    public ResponseResult hotPostList() {
        return null;
    }

    @Override
    public ResponseResult postList() {
        return null;
    }
    public List<User> selectUserList(int pageNum, int pageSize, String username) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), "username", username);
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public ResponseResult animationPostList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_ANIMATION);
        List<Post> posts = list(lambdaQueryWrapper);
        // 转换成VO
        List<PostSummaryVO> postSummaryVOList =BeanCopyUtils.copyBeanList(posts, PostSummaryVO.class);
        System.out.println(postSummaryVOList);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(posts, PostSummaryVO.class));
    }

    @Override
    public ResponseResult comicPostList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_COMIC);
        List<Post> posts = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(posts, PostSummaryVO.class));
    }

    @Override
    public ResponseResult gamePostList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_GAME);
        List<Post> posts = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(posts, PostSummaryVO.class));
    }

    @Override
    public ResponseResult novelPostList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_NOVEL);
        List<Post> posts = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(posts, PostSummaryVO.class));
    }

    @Override
    public ResponseResult userPostList(int pageNum, int pageSize, int userId) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getAuthorId, userId);
        List<Post> posts = list(lambdaQueryWrapper);
        // 转换成VO
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(posts, PostSummaryVO.class));
    }
    @Override
    public ResponseResult postDetails(int id) {
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getId,id);
        Post post = postMapper.selectOne(lambdaQueryWrapper);
        // 增加阅读次数
        post.setViewTimes(post.getViewTimes()+1);
        postMapper.updateById(post);
        PostDetailsVO postDetailsVO=BeanCopyUtils.copyBean(post, PostDetailsVO.class);
        // 查询作者名字
        LambdaQueryWrapper<User> lambdaQueryWrapper4 = new LambdaQueryWrapper();
        lambdaQueryWrapper4.eq(User::getId,post.getAuthorId());
        User user = userMapper.selectOne(lambdaQueryWrapper4);
        postDetailsVO.setAuthorName(user.getUsername());
        // 查询评论数
        LambdaQueryWrapper<Comment> lambdaQueryWrapper1 = new LambdaQueryWrapper();
        lambdaQueryWrapper1.eq(Comment::getPostId,id);
        int commentNum = commentMapper.selectCount(lambdaQueryWrapper1);
        postDetailsVO.setCommentNum(commentNum);
        // 查收藏数
        LambdaQueryWrapper<Favorite> lambdaQueryWrapper2 = new LambdaQueryWrapper();
        lambdaQueryWrapper2.eq(Favorite::getPostId,id);
        Integer favoriteNum = favoriteMapper.selectCount(lambdaQueryWrapper2);
        postDetailsVO.setFavoriteNum(favoriteNum);
        // 查喜欢数
        LambdaQueryWrapper<Likes> lambdaQueryWrapper3 = new LambdaQueryWrapper();
        lambdaQueryWrapper3.eq(Likes::getPostId,id);
        Integer likeNum = likesMapper.selectCount(lambdaQueryWrapper3);
        postDetailsVO.setLikeNum(likeNum);
        // 查询是否收藏
        int userId = securityUitl.getUserId();
        boolean favorited=false;
        // 查询是否喜欢
        boolean liked=false;
        System.out.println(userId);
        if(userId==0){
            favorited=false;
            liked=false;
        }
        else {
            favorited = favoriteService.checkFavorite(userId, id);
            liked =likeService.checkLiked(userId,id);
        }
        postDetailsVO.setFavorited(favorited);
        postDetailsVO.setLiked(liked);

        return ResponseResult.okResult(postDetailsVO);
    }

    @Override
    public ResponseResult addPost(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = loginUser.getUsername();
        System.out.println(username);
        boolean checkPhone = RegexValidateUtil.checkPhone(username);
        boolean checkEmail = RegexValidateUtil.checkEmail(username);
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        if(checkPhone){
            lambdaQueryWrapper.eq(User::getPhone, username);
        }
        else if (checkEmail){
            lambdaQueryWrapper.eq(User::getEmail, username);
        }
        else {
            return new ResponseResult(200,"发布失败，请重试",null);
//            throw new RuntimeException("not phone or email");
        }
        //查询User信息
        User user = userMapper.selectOne(lambdaQueryWrapper);
        System.out.println(post);
        post.setViewTimes(0);
        post.setAuthorId(user.getId());
        //
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateFormat.format(calendar.getTime()));
        Date date = new Date();
        post.setCreatedAt(date);
        post.setUpdateAt(date);
        post.setDelFlag(0);


//        post.setId(9);
        int insert = postMapper.insert(post);

        return  new ResponseResult(200,"发布成功！",null);
    }

    @Override
    public ResponseResult deletePost(int postId) {
        //TODO
        return new  ResponseResult(200,"delete success",null);
    }
}