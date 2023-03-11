package com.yusa.acgnbbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yusa.acgnbbs.mapper")
@SpringBootApplication
public class AcgnbbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcgnbbsApplication.class, args);
    }

}
