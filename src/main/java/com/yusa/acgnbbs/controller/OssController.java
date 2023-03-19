package com.yusa.acgnbbs.controller;

import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.service.ALiYunOssService;
import com.yusa.acgnbbs.service.impl.ALiYunOssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Eric
 * @create 2022-04-24 23:51
 */
@Api(tags = "阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin    //解决跨域
public class OssController {
    @Autowired
    private ALiYunOssService ossService;
    //上传头像到OSS
    @PostMapping
    @ApiOperation(value = "上传头像到OSS")
    public ResponseResult uploadOssFile(@RequestBody MultipartFile file){
        //获取上传的文件  通过 MultipartFile
        String url = ossService.uploadFileAvatar(file,"images");//返回上传图片的路径
        return ResponseResult.okResult(url);
    }
}
