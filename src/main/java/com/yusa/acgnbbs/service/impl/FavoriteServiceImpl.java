package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Favorite;
import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.mapper.FavoriteMapper;
import com.yusa.acgnbbs.mapper.PostMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.FavoriteService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.utils.SecurityUitl;
import com.yusa.acgnbbs.vo.FavoritePostTotalVO;
import com.yusa.acgnbbs.vo.FavoritePostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SecurityUitl securityUitl;

    @Override
    public ResponseResult userFavoriteList(int currentPage,int pageSize,int userId) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Favorite>  lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Favorite::getCreatedAt).eq(Favorite::getUserId,userId);
        List<Favorite> favorites = favoriteMapper.selectList(lambdaQueryWrapper);
        List<Post> postList= new ArrayList<>();
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数
        System.out.println("total:"+total);
        for (Favorite favorite: favorites) {
            Post post = postMapper.selectById(favorite.getPostId());
            if(!Objects.isNull(post)) {
                postList.add(post);
            }
        }
        List<FavoritePostVO> favoritePostVOS = BeanCopyUtils.copyBeanList(postList, FavoritePostVO.class);
        for (FavoritePostVO favorite: favoritePostVOS) {
            favorite.setAuthorName(userMapper.selectById(favorite.getAuthorId()).getUsername());
        }
        FavoritePostTotalVO favoritePostTotalVO = new FavoritePostTotalVO(favoritePostVOS,total);
        return new ResponseResult(200,"OK",favoritePostTotalVO);
    }

    @Override
    public Boolean checkFavorite(int userId, int postId) {
        LambdaQueryWrapper<Favorite> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getUserId,userId);
        lambdaQueryWrapper.eq(Favorite::getPostId,postId);
        Favorite favorite = favoriteMapper.selectOne(lambdaQueryWrapper);
        Boolean isFavorited=true;
        if(Objects.isNull(favorite)){
            isFavorited=false;
        }
        return isFavorited;
    }

    @Override
    public ResponseResult addFavorite(int userId, int postId) {
        int loginId = securityUitl.getUserId();
        if(userId!=loginId){
            return new ResponseResult(403,"登录状态异常",null);
        }
        Favorite favorite=new Favorite();
        favorite.setUserId(userId);
        favorite.setPostId(postId);
        favoriteMapper.insert(favorite);
        return new ResponseResult(200,"OK",null);
    }

    @Override
    public ResponseResult deleteFavorite(int userId,int postId) {
        int loginId = securityUitl.getUserId();
        if(userId!=loginId){
            return new ResponseResult(403,"登录状态异常",null);
        }
        LambdaQueryWrapper<Favorite> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getUserId,userId);
        lambdaQueryWrapper.eq(Favorite::getPostId,postId);

        favoriteMapper.delete(lambdaQueryWrapper);
        return new ResponseResult(200,"OK",null);
    }
}
