package com.timecarol.smart_dormitory_repair;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.Duration;
import java.time.Instant;

@Slf4j
//关闭Spring Security认证
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.timecarol.smart_dormitory_repair.mapper")
public class SmartDormitoryRepairApplication {

    public static void main(String[] args) {
        Instant start = Instant.now();
        SpringApplication.run(SmartDormitoryRepairApplication.class, args);
        Instant end = Instant.now();
        Duration between = Duration.between(start, end);
        log.info("smart-dormitory启动完成, 耗时: {}毫秒, swagger地址: http://localhost:8086/swagger-ui.html, API文档地址: http://localhost:8086/doc.html", between.toMillis());
    }

}
