package com.timecarol.smart_dormitory_repair.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.util.PdfUtil;
import com.timecarol.smart_dormitory_repair.util.QRCodeUtil;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Api(description = "登录控制器")
@RestController
public class SmartLoginController {

    @Autowired
    private ISmartUserService smartUserService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public SimpleResponse<SaTokenInfo> login(@RequestBody SmartUserVo vo) {
        //检查是否登录
        if (StpUtil.isLogin()) {
            //如果已经登录直接返回token信息
            return new SimpleResponse<>(StpUtil.getTokenInfo());
        }
        //到数据库查询用户信息
        SmartUserDto smartUser = smartUserService.query(vo);
        if (smartUser == null) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "用户不存在");
        }
        //比较密码
        if (!passwordEncoder.matches(vo.getPassword(), smartUser.getPassword())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "密码错误");
        }
        //登录
        StpUtil.login(smartUser.getId(), "PC");
        //得到token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        //去掉密码
        smartUser.setPassword("");
        //将用户信息缓存到session中
        StpUtil.getTokenSessionByToken(tokenInfo.tokenValue).set("SmartUser", JSON.toJSONString(smartUser));
        return new SimpleResponse<>(tokenInfo);
    }

}
