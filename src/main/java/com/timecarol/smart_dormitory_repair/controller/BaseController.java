package com.timecarol.smart_dormitory_repair.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@Api(description = "基本控制器")
public class BaseController {

    public SmartUserDto getSmartUser() {
        Object smartUser = StpUtil.getTokenSession().get("SmartUser");
        if (smartUser == null) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "您还没有登录");
        }
        return JSON.parseObject(smartUser.toString(), SmartUserDto.class);
    }
}
