package com.yusa.acgnbbs.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Notice;

public interface NoticeService   {
    ResponseResult noticeList();
}
