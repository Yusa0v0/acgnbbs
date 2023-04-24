package com.yusa.acgnbbs.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yusa.acgnbbs.service.ALiYunOssService;
import com.yusa.acgnbbs.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@Service
public class ALiYunOssServiceImpl implements ALiYunOssService {
    @Override
    public String uploadFileAvatar(MultipartFile file,String filePath) {
        //工具类获取值：分别是：地域节点、id、秘钥、项目名称
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            // 1、在文件名称里面添加随机唯一值
            // 去除生成后的值中的横杠
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid + fileName;
            // 2、把文件安装日期进行分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接路径和文件名
            fileName =filePath +"/"+ datePath + "/" + fileName;
            //调用oss方法实现上传
            //参数一：Bucket名称  参数二：上传到oss文件路径和文件名称 参数三：上传文件的流
            ossClient.putObject(bucketName,fileName,inputStream);
            //关闭OSSClient
            ossClient.shutdown();
            //返回路径
            String url = " https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
