package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.Notice;
import com.yusa.acgnbbs.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @GetMapping("/noticeList/{currentPage}/{pageSize}")
    public ResponseResult noticeList(@PathVariable("currentPage") int currentPage,@PathVariable("pageSize") int pageSize){
        return noticeService.noticeList(currentPage,pageSize);
    }

    @PostMapping("/addNotice")
    public ResponseResult addNotice(@RequestBody Notice notice){
        return noticeService.addNotice(notice);
    }
    @GetMapping("/deleteNotice/{noticeId}")
    public ResponseResult deleteNotice(@PathVariable("noticeId") int noticeId){
        return noticeService.deleteNotice(noticeId);
    }
}
