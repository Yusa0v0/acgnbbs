package com.yusa.acgnbbs.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.*;
import com.yusa.acgnbbs.mapper.*;
import com.yusa.acgnbbs.service.FavoriteService;
import com.yusa.acgnbbs.service.LikeService;
import com.yusa.acgnbbs.service.PostService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Page page = PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_ANIMATION);
        List<Post> posts = list(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("总条数"+total);
        // 转换成VO
        List<PostListVO> postListVOList =BeanCopyUtils.copyBeanList(posts, PostListVO.class);
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper();

        for (PostListVO postListVO :
                postListVOList) {
            User user = userMapper.selectById(postListVO.getAuthorId());
            String username = user.getUsername();
            String avatar = user.getAvatar();
            postListVO.setAvatar(avatar);
            postListVO.setAuthorName(username);
        }
        PostListTotalVO postListTotalVO = new PostListTotalVO(postListVOList, total);
        return ResponseResult.okResult(postListTotalVO);
    }

    @Override
    public ResponseResult comicPostList(int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_COMIC);
        List<Post> posts = list(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("总页数"+total);
        // 转换成VO
        List<PostListVO> postListVOList =BeanCopyUtils.copyBeanList(posts, PostListVO.class);
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper();

        for (PostListVO postListVO :
                postListVOList) {
            User user = userMapper.selectById(postListVO.getAuthorId());
            String username = user.getUsername();
            String avatar = user.getAvatar();
            postListVO.setAvatar(avatar);
            postListVO.setAuthorName(username);
        }
        PostListTotalVO postListTotalVO = new PostListTotalVO(postListVOList, total);
        return ResponseResult.okResult(postListTotalVO);
    }

    @Override
    public ResponseResult gamePostList(int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_GAME);
        List<Post> posts = list(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("总页数"+total);
        // 转换成VO
        List<PostListVO> postListVOList =BeanCopyUtils.copyBeanList(posts, PostListVO.class);
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper();

        for (PostListVO postListVO :
                postListVOList) {
            User user = userMapper.selectById(postListVO.getAuthorId());
            String username = user.getUsername();
            String avatar = user.getAvatar();
            postListVO.setAvatar(avatar);
            postListVO.setAuthorName(username);
        }
        PostListTotalVO postListTotalVO = new PostListTotalVO(postListVOList,total);
        return ResponseResult.okResult(postListTotalVO);
    }

    @Override
    public ResponseResult novelPostList(int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getCategoryId, POST_CATEGORY_NOVEL);
        List<Post> posts = list(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("总页数"+total);
        // 转换成VO
        List<PostListVO> postListVOList =BeanCopyUtils.copyBeanList(posts, PostListVO.class);
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper();

        for (PostListVO postListVO :
                postListVOList) {
            User user = userMapper.selectById(postListVO.getAuthorId());
            String username = user.getUsername();
            String avatar = user.getAvatar();
            postListVO.setAvatar(avatar);
            postListVO.setAuthorName(username);
        }
        PostListTotalVO postListTotalVO = new PostListTotalVO(postListVOList, total);
        return ResponseResult.okResult(postListTotalVO);
    }

    @Override
    public ResponseResult userPostList(int pageNum, int pageSize, int userId) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Post::getAuthorId, userId);
        List<Post> posts = list(lambdaQueryWrapper);
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        List<UserPostVO> userPostVOList = BeanCopyUtils.copyBeanList(posts, UserPostVO.class);
        for (UserPostVO userPostVO: userPostVOList) {
            userPostVO.setAuthorName(userMapper.selectById(userPostVO.getAuthorId()).getUsername());
        }
        UserPostTotalVO userPostTotalVO = new UserPostTotalVO(userPostVOList, total);

        // 转换成VO
        return ResponseResult.okResult(userPostTotalVO);
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
        System.out.println(post);
        User user = securityUitl.getUser();
        // 如果postId为0，则为新增
        if(post.getId()==0) {
            post.setViewTimes(0);
            post.setAuthorId(user.getId());
            post.setTitle(post.getTitle());
            Date date = new Date();
            post.setCreatedAt(date);
            post.setUpdateAt(date);
            post.setDelFlag(0);
//        post.setId(9);
            int insert = postMapper.insert(post);
        }
        // 否则为更新
        else {
            // 原来的
            Post post1 = postMapper.selectById(post.getId());
            post.setCreatedAt(post1.getCreatedAt());
            post.setUpdateAt(new Date());
            postMapper.updateById(post);
        }
        return  new ResponseResult(200,"发布成功！",null);
    }

    @Override
    public ResponseResult getWritePost(int postId) {
        Post post = postMapper.selectById(postId);
        System.out.println(post);
        WritePostVO writePostVO = BeanCopyUtils.copyBean(post, WritePostVO.class);
        System.out.println(writePostVO);
        return new ResponseResult(200,"OK",writePostVO);
    }

    @Override
    public ResponseResult deletePost(int postId) {
        //TODO
        return new  ResponseResult(200,"delete success",null);
    }
}