package com.timecarol.smart_dormitory_repair.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录拦截器
//        registry.addInterceptor(new SaTokenLoginInterceptor())
//                // 需要拦截的路径
//                .addPathPatterns("/**")
//                // 不需要拦截的路径
//                .excludePathPatterns(
//                        // 获取验证码
//                        "/kaptcha-image",
//                        // 登录
//                        "/login",
//                        // 登出
//                        "/logout",
//                        // 开发文档
//                        "/doc.html", "/webjars/**", "/v2/api-docs/**", "/swagger-resources/**", "/favicon.ico"
//                );
        // 注册注解拦截器
        registry.addInterceptor(new SaAnnotationInterceptor())
                // 需要拦截的路径
                .addPathPatterns("/**");
    }

    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOrigins("*")
                //允许跨域的方法
                .allowedMethods("GET", "OPTIONS", "POST", "PUT", "DELETE")
                //允许跨域携带的请求头
                .allowedHeaders("*")
                //允许跨域响应头
                .exposedHeaders("Authorization", "satoken");
    }
}
