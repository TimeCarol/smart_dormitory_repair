package com.timecarol.smart_dormitory_repair.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.timecarol.smart_dormitory_repair.constant.Constant;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Slf4j
@Controller
@Api(description = "基本控制器")
public class BaseController {

    public SmartUserDto getSmartUser() {
        Object smartUser = StpUtil.getTokenSession().get(Constant.SMART_USER);
        if (Objects.isNull(smartUser)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "您还没有登录");
        }
        SmartUserDto dto = JSON.parseObject(smartUser.toString(), SmartUserDto.class);
        if (StrUtil.isEmpty(dto.getUsername())) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "您还没有登录");
        }
        return dto;
    }

}
