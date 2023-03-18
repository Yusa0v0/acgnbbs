package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Notice;
import com.yusa.acgnbbs.mapper.NoticeMapper;
import com.yusa.acgnbbs.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;
    @Override
    public ResponseResult noticeList() {
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Notice::getId,1 );
        List<Notice> notices = list(lambdaQueryWrapper);
        return ResponseResult.okResult(notices);
    }
}
