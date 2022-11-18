package com.timecarol.smart_dormitory_repair.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.BooleanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
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

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(15000)) //设置连接超时时间为15秒
                .setReadTimeout(Duration.ofMillis(15000)) //设置读取超时时间为15秒
                .rootUri("http://httpbin.org/") //设置根路径
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8)) //设置Http消息转换器
                .errorHandler(new ResponseErrorHandler() {
                    private Logger log = LoggerFactory.getLogger(this.getClass());

                    @Override
                    public boolean hasError(ClientHttpResponse response) throws IOException {
                        return BooleanUtil.isFalse(HttpStatus.OK.equals(response.getStatusCode()));
                    }

                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        try {
                            InputStream in = response.getBody();
                            log.error("请求失败, 状态码: {}, headers:{}, body:{}", response.getRawStatusCode(), response.getHeaders(), IoUtil.readUtf8(in));
                        } catch (IOException e) {
                            log.error("请求失败, 状态码: {}, headers:{}, error:{}", response.getRawStatusCode(), response.getHeaders(), e);
                            throw e;
                        }
                    }
                })
                .interceptors(new ClientHttpRequestInterceptor() {
                    private Logger log = LoggerFactory.getLogger(this.getClass());

                    @Override
                    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                        Instant start = Instant.now();
                        String bodyContent = "empty";
                        if (body.length > 0) {
                            bodyContent = new String(body, StandardCharsets.UTF_8);
                        }
                        log.info("请求开始, Method:{}, URI:{}, Headers:{}, body:{}, start:{}", request.getMethodValue(), request.getURI(), request.getHeaders(), bodyContent, start.toEpochMilli());
                        ClientHttpResponse execute = execution.execute(request, body);
                        Duration duration = Duration.between(start, Instant.now());
                        byte[] bytes = IoUtil.readBytes(execute.getBody(), Boolean.FALSE);
                        String responseBody = new String(bytes, StandardCharsets.UTF_8);
                        log.info("请求结束, ResponseStatus:{}, ResponseHeaders:{}, ResponseBody:{}, 耗时:{}毫秒, start:{}", execute.getStatusCode(), execute.getHeaders(), responseBody, duration.toMillis(), start.toEpochMilli());
                        return execute;
                    }
                })
                .build();
    }
}
