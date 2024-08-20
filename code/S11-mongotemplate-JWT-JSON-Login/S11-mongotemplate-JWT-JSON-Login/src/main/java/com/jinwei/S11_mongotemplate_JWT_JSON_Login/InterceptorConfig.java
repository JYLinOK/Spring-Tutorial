package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}

