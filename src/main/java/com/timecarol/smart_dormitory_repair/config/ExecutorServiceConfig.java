package com.timecarol.smart_dormitory_repair.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 配置线程池
 */

@Configuration
@Slf4j
public class ExecutorServiceConfig {

    @Bean
    public ExecutorService executorService() {
        int corePoolSize = Runtime.getRuntime().availableProcessors(); //核心线程数量
        int maximumPoolSize = corePoolSize << 1; //最大线程数量
        int queueCapacity = 200; //缓冲队列大小
        int keepAliveTime = 60; //允许线程空闲时间
        log.info("初始化线程池, 核心线程数量: {}, 最大线程数量: {}, 允许线程空闲时间: {}, 缓冲队列大小: {}", corePoolSize, maximumPoolSize, keepAliveTime, queueCapacity);
        return new ThreadPoolExecutor(corePoolSize,
                                maximumPoolSize,
                                keepAliveTime,
                                TimeUnit.SECONDS,
                                new LinkedBlockingQueue<>(queueCapacity),
                                Executors.defaultThreadFactory(),
                                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
