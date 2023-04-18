package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Post;
import com.yusa.acgnbbs.domain.entity.Report;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.PostMapper;
import com.yusa.acgnbbs.mapper.ReportMapper;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.service.ReportService;
import com.yusa.acgnbbs.utils.BeanCopyUtils;
import com.yusa.acgnbbs.vo.ReportTotalVO;
import com.yusa.acgnbbs.vo.ReportVO;
import com.yusa.acgnbbs.vo.UserPostTotalVO;
import com.yusa.acgnbbs.vo.UserPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PostMapper postMapper;
    @Override
    public ResponseResult report(Report report) {
        System.out.println(report);
        Date date = new Date();
        report.setCreateAt(date);
        reportMapper.insert(report);
        return new ResponseResult(200,"举报成功",null);
    }

    @Override
    public ResponseResult reportList(int currentPage, int pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        LambdaQueryWrapper<Report> lambdaQueryWrapper = new LambdaQueryWrapper();
        List<Report> reports = reportMapper.selectList(lambdaQueryWrapper);
        List<ReportVO> reportVOS=new ArrayList<>();
        for (Report report: reports){
            Integer userId = report.getUserId();
            Integer isBanned = userMapper.selectById(userId).getIsBanned();
            Integer postId = report.getPostId();
            Post post = postMapper.selectById(postId);
            Boolean isDeleted= Objects.isNull(post);
            ReportVO reportVO = new ReportVO(report, isBanned!=0, isDeleted);
            reportVOS.add(reportVO);
        }
        PageInfo info = new PageInfo<>(page.getResult());
        Long total = info.getTotal();//获取总条数

        ReportTotalVO reportTotalVO =new ReportTotalVO(reportVOS,total);
        // 转换成VO
        return ResponseResult.okResult(reportTotalVO);
    }

    @Override
    public ResponseResult handleReport(int reportId) {
        Report report = reportMapper.selectById(reportId);
        report.setIsHandled(1);
        reportMapper.updateById(report);
        return ResponseResult.okResult();
    }
}
