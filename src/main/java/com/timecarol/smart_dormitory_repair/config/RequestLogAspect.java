package com.timecarol.smart_dormitory_repair.config;


import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

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
        logInfo.append(StrUtil.format("********request start, url = {}, ", req.getRequestURL().toString()));
        logInfo.append(StrUtil.format("Method = {},", req.getMethod()));
        logInfo.append(StrUtil.format("remoteIp = {}, localIp = {},", req.getRemoteAddr(), req.getLocalAddr() + ":" + req.getLocalPort()));
        logInfo.append(StrUtil.format("class_method = {},", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()));
        Object[] args = joinPoint.getArgs();
        List<Object> argList = Collections.emptyList();
        if (Objects.nonNull(args) && args.length > 0) {
            argList = Lists.newLinkedList();
            for (Object o : args) {
                if (o instanceof ServletRequest) {
                    ServletRequest request = (ServletRequest) o;
                    argList.add(request.getParameterMap());
                } else if (o instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) o;
                    Map<String, Object> fileParam = Maps.newLinkedHashMap();
                    fileParam.put("name", file.getName());
                    fileParam.put("originalFileName", file.getOriginalFilename());
                    fileParam.put("fileSize", file.getSize() + " bytes");
                    fileParam.put("Content-Type", file.getContentType());
                    argList.add(fileParam);
                } else if (o instanceof HttpSession) {
                    HttpSession session = (HttpSession) o;
                    Enumeration<String> attributeNames = session.getAttributeNames();
                    Map<String, Object> sessionParam = Maps.newLinkedHashMap();
                    while (attributeNames.hasMoreElements()) {
                        String name = attributeNames.nextElement();
                        Object attribute = session.getAttribute(name);
                        sessionParam.put(name, attribute);
                    }
                    argList.add(sessionParam);
                } else if (!(o instanceof ServletResponse)) {
                    argList.add(o);
                }
            }
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
            logInfo.append(StrUtil.format("{} info{browser:{}, version:{}, engine:{}, engineVersion:{}, os:{}, platform:{}}", HttpHeaders.USER_AGENT, browser, version, engine, engineVersion, os, platform));
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
