package com.wdd.myplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.wdd.myplatform.mapper")
@EnableOpenApi
public class MyPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPlatformApplication.class, args);
    }

}
