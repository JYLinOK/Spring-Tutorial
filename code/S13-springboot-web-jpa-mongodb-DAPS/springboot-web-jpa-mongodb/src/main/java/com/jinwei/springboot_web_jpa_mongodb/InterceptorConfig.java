package com.jinwei.springboot_web_jpa_mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//        registration.addPathPatterns("/**"); //所有路径都被拦截
//        registration.excludePathPatterns("" +
//                        "/assets/**",             // assets文件夹里文件不拦截
//                "/**/*.js",              //js静态资源不拦截
//                "/**/*.css"             //css静态资源不拦截

@Configuration
@Slf4j
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    // 注册-自定义拦截器-registry
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
//                .addPathPatterns("/api/backUser/loginUser", "/api/backConnector/registerConnector")
//                .excludePathPatterns("/api/backUser/loginUser");
                .excludePathPatterns("/api/backConnector/findAllConnector");
    }
}

