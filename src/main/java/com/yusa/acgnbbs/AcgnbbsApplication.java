package com.yusa.acgnbbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.yusa.acgnbbs.mapper")
@EnableSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AcgnbbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcgnbbsApplication.class, args);
    }

}
