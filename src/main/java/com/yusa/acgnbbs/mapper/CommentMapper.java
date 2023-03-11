package com.yusa.acgnbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yusa.acgnbbs.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
