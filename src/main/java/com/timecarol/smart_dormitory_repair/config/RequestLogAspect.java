package com.timecarol.smart_dormitory_repair.config;


import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Pointcut("execution(public * com.timecarol.smart_dormitory_repair.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        Instant start = Instant.now();
        req.setAttribute("requestStartTime", start);
        StringBuilder logInfo = new StringBuilder();
        logInfo.append(StrUtil.format("********request start, url = {},", req.getRequestURL().toString()));
        logInfo.append(StrUtil.format("Method = {},", req.getMethod()));
        logInfo.append(StrUtil.format("remoteIp = {}, localIp = {},", req.getRemoteAddr(), req.getLocalAddr() + ":" + req.getLocalPort()));
        logInfo.append(StrUtil.format("class_method = {},", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()));
        Object[] args = joinPoint.getArgs();
        List<Object> argList = Collections.emptyList();
        if (Objects.nonNull(args) && args.length > 0) {
            argList = Stream.of(args).filter(o -> !(o instanceof ServletResponse)).collect(Collectors.toList());
        }
        logInfo.append(StrUtil.format("args = {},", JSON.toJSONString(argList)));
        String userAgent = req.getHeader(HttpHeaders.USER_AGENT);
        logInfo.append(StrUtil.format("user-agent = {},", userAgent));
        UserAgent ua = UserAgentUtil.parse(userAgent);
        if (Objects.nonNull(ua)) {
            String browser = ua.getBrowser().toString();
            String version = ua.getVersion();
            String engine = ua.getEngine().toString();
            String engineVersion = ua.getEngineVersion();
            String os = ua.getOs().toString();
            String platform = ua.getPlatform().toString();
            logInfo.append(StrUtil.format("{} info, browser:{}, version:{}, engine:{}, engineVersion:{}, os:{}, platform:{}", HttpHeaders.USER_AGENT, browser, version, engine, engineVersion, os, platform));
        }
        log.info(logInfo.toString());
    }

    @AfterReturning(returning = "object", value = "log()")
    public void doAfter(Object object) {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append(StrUtil.format("returnArgs = {}", JSON.toJSONString(object)));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        String url = req.getRequestURL().toString();
        Instant start = (Instant) req.getAttribute("requestStartTime");
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info("********request completed. {}, url:{}, cost:{}", logInfo.toString(), url, DateUtil.formatBetween(duration.toMillis(), BetweenFormatter.Level.MILLISECOND));
    }
}
