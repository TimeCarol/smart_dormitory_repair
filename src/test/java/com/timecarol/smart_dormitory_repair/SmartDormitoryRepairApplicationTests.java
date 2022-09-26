package com.timecarol.smart_dormitory_repair;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SmartDormitoryRepairApplicationTests {

    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            System.out.println(DigestUtil.bcrypt("123456"));
        }
    }

}
