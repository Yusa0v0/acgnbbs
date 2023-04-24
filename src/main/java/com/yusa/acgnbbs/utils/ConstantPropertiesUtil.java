package com.yusa.acgnbbs.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取阿里云信息的工具类
 *      该类的执行流程：
 *          类加载后，先加载四个变量，再分别赋值
 *          接着执行重写的方法
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {   //当项目一启动，就会执行该接口的重写方法
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;//地域节点

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;//id

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;//秘钥

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;//项目名称
    @Value("${spring.mail.username}")
    private String fromEmail;//项目名称

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String FROM_EMAIL;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
        FROM_EMAIL=fromEmail;
    }
}


