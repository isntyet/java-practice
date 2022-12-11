package com.isntyet.java.practice.human.infra.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class UserFeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() throws InterruptedException {
        return requestTemplate -> requestTemplate.header("header", "header2");
    }
}
