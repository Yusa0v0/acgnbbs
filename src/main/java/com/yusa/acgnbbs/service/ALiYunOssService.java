package com.yusa.acgnbbs.service;

import org.springframework.web.multipart.MultipartFile;

public interface ALiYunOssService {
    String uploadFileAvatar(MultipartFile file,String filePath);
}
