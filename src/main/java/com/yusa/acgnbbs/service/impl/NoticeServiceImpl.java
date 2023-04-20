package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Notice;
import com.yusa.acgnbbs.mapper.NoticeMapper;
import com.yusa.acgnbbs.service.NoticeService;
import com.yusa.acgnbbs.vo.NoticeTotalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;
    @Override
    public ResponseResult noticeList(int currentPage, int pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(Notice::getId);
        List<Notice> notices = list(lambdaQueryWrapper);
        long total = page.getTotal();
        NoticeTotalVO noticeTotalVO = new NoticeTotalVO(notices, total);
        return ResponseResult.okResult(noticeTotalVO);
    }

    @Override
    public ResponseResult addNotice(Notice notice) {
        Date date = new Date();
        System.out.println(notice);
        if(notice.getId()==0){
//            notice.setId(null);
            notice.setCreatedAt(date);
            int insert = noticeMapper.insert(notice);
            System.out.println("insert:" + insert);
        }
        else {
            int update = noticeMapper.updateById(notice);
            System.out.println("update:" + update);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteNotice(int noticeId) {
        noticeMapper.deleteById(noticeId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getNewNotice() {
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Notice> notices = noticeMapper.selectList(lambdaQueryWrapper.orderByDesc(Notice::getCreatedAt).last("LIMIT 1"));
        Notice notice =notices.get(0);
        return new ResponseResult(200,"OK",notice);
    }
}
