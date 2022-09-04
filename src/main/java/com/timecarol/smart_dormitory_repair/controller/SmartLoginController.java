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

    @ApiOperation("生成验证码")
    @GetMapping("/verifyCode")
    public void generateVerifyCode(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        //ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        //告诉浏览器，这个请求用图片的方式打开
        res.setContentType("image/jpeg");
        //网站存在缓存，不让浏览器缓存
        res.setDateHeader("empires", -1);
        res.setHeader("Cache-Control", "no-cache");
        res.setHeader("Pragma", "no-cache");
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write(res.getOutputStream());
        //将验证码保存至Session
        req.getSession().setAttribute("verifyCode", captcha.getCode());
        //关闭输出流
        res.getOutputStream().close();
    }

    @ApiOperation("获得验证码")
    @GetMapping("/qrcode")
    public void generateQRCode(HttpServletResponse res) throws IOException {
        BufferedImage image = QRCodeUtil.createImage("http://timecarol.xyz");
        res.setContentType("image/png");
        res.setHeader("base64", QRCodeUtil.bufferedImageToImgBase64(image));
        ImageIO.write(image, "png", res.getOutputStream());
        boolean saveResult = QRCodeUtil.saveBufferedImage(UUID.randomUUID() + ".jpeg", image);
        log.info("保存结果: " + saveResult);
        InputStream inputStream = QRCodeUtil.buildImage(UUID.randomUUID().toString());
        log.info("解析结果: " + QRCodeUtil.decode(inputStream));
        if (inputStream != null) {
            inputStream.close();
        }
        res.getOutputStream().close();
    }

    @ApiOperation("生成PDF")
    @GetMapping("/pdf")
    public void generatePDF(HttpServletResponse res) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("company", "此处是公司名称");
        param.put("planNo", "SK202209040002");
        param.put("name", "法外狂徒张三");
        param.put("phone", "15231425312");
        param.put("idCard", "360781199912303635");
        param.put("carNumber", "赣B63254");
        param.put("remark", "此处是备注信息");
        PdfUtil.PdfDynamicParam pdfParam = new PdfUtil.PdfDynamicParam();
        pdfParam.setTemplatePath("templates/pdf/提货函.ftl");
        pdfParam.setPdfPath(DateUtil.format(new Date(), "yyyy-MM-dd_HH-mm-ss") + "_提货函.pdf");
        pdfParam.setPdfParam(param);
        boolean result = PdfUtil.generatePdf(pdfParam);
        log.info("pdf生成结果: " + result);

        pdfParam.setTemplatePath("templates/pdf/送货单.ftl");
        pdfParam.setPdfPath(DateUtil.format(new Date(), "yyyy-MM-dd_HH-mm-ss") + "_送货单.pdf");
        result = PdfUtil.generatePdf(pdfParam);
        log.info("pdf生成结果: " + result);

        res.setContentType("application/pdf");

        File file = new File(pdfParam.getPdfPath());
        ServletOutputStream os = res.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.close();
    }
}
