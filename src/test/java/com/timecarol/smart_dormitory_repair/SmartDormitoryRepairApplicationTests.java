package com.timecarol.smart_dormitory_repair;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.timecarol.smart_dormitory_repair.entity.SmartMaintainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SmartDormitoryRepairApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            System.out.println(DigestUtil.bcrypt("123456"));
        }
    }

    @Test
    void testRestTemplate() {
        //测试get请求
        Map<String, Object> param = Maps.newLinkedHashMap();
        param.put("name", "法外狂徒张三");
        ResponseEntity<String> response = restTemplate.getForEntity("/get?name={name}", String.class, param);
        MediaType contentType = response.getHeaders().getContentType();
        if (!MediaType.APPLICATION_JSON.equals(contentType)) {
            log.error("不受支持的类型:{}", String.valueOf(contentType));
        } else {
            log.info("类型:{}", String.valueOf(contentType));
            Map<String, Object> map = JSON.parseObject(response.getBody(), new TypeReference<Map<String, Object>>() {
            });
            log.info("result:{}", map);
        }
        //测试post请求
        response = restTemplate.postForEntity("/post", new SmartMaintainer(), String.class);
        log.info("result:{}", JSON.parseObject(response.getBody(), new TypeReference<Map<String, Object>>() {
        }));
        //测试返回错误响应码
        response = restTemplate.postForEntity("/status/404", new SmartMaintainer(), String.class);
        log.info("result:{}", JSON.parseObject(response.getBody(), new TypeReference<Map<String, Object>>() {
        }));
    }

}
