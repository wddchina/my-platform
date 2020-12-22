package com.wdd.myplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date 2018-11-14 10:48
 */
@Configuration
public class Swagger3Config {
    @Configuration
    public class Swagger3 {

        @Bean
        public Docket docket() {
            return new Docket(DocumentationType.OAS_30).apiInfo(
                    new ApiInfoBuilder()
                            .contact(new Contact("wdd", "", "aaa@qq.com"))
                            .title("Swagger3测试集成")
                            .build()
            );
        }
    }
}
