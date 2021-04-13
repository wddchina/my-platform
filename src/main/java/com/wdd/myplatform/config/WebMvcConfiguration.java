package com.wdd.myplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Bean
    AuthorizeInterceptor authorizeInterceptor() {
        return new AuthorizeInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(authorizeInterceptor()).excludePathPatterns();
    }
}
