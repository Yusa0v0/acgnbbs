package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.SignService;
import com.yusa.acgnbbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    SignService signService;
    @GetMapping("/sign")
    public ResponseResult userSign(){
        return signService.sign();
    }
    @GetMapping("/getSigned/{id}")
    public ResponseResult getSigned(@PathVariable int id){
        return signService.getSigned(id);
    }

    @GetMapping("/signCount/{userId}")
    public ResponseResult signCount(@PathVariable int userId) {
        return signService.signCount(userId);
    }
    @GetMapping("/signMonthCount/{userId}/{date}")
    public ResponseResult signMonthCount(@PathVariable("userId") int userId,@PathVariable("date") String date) {
        return signService.signMonthCount(userId,date);
    }
    @GetMapping("/signYearCount/{userId}/{year}")
    public ResponseResult signYearCount(@PathVariable("userId") int userId,@PathVariable("year") int year) {
        return signService.signYearCount(userId,year);
    }
    @GetMapping("/signTotalCount/{userId}/{year}")
    public ResponseResult signTotalCount(@PathVariable("userId") int userId,@PathVariable("year") int year) {
        return signService.signYearCount(userId,year);
    }
}
