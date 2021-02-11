package com.example.hope.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
        WebContentInterceptor contentInterceptor = new WebContentInterceptor();
//        contentInterceptor.addCacheMapping(CacheControl.maxAge(1, TimeUnit.HOURS).noTransform().mustRevalidate(),"/service/*");

        registry.addInterceptor(contentInterceptor);
    }
    @Bean
    public Authentication authenticationInterceptor() {
        return new Authentication();
    }
}