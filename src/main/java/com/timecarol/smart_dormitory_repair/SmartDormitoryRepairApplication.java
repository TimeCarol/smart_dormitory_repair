package com.timecarol.smart_dormitory_repair;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@Slf4j
//关闭Spring Security认证
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.timecarol.smart_dormitory_repair.mapper")
public class SmartDormitoryRepairApplication {

    public static void main(String[] args) {
        long startTime = DateUtil.current();
        SpringApplication.run(SmartDormitoryRepairApplication.class, args);
        log.info("smart-dormitory启动完成, 耗时: {}毫秒, swagger地址: http://localhost:8086/swagger-ui.html", DateUtil.current() - startTime);
    }

}
