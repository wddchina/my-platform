package com.wdd.myplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @description
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
