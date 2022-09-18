package com.timecarol.smart_dormitory_repair.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.util.PdfUtil;
import com.timecarol.smart_dormitory_repair.util.QRCodeUtil;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

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

    @PostMapping("/upload")
    @ResponseBody
    public SimpleResponse<String> upload(MultipartFile file, @RequestParam("fileName") String fileName) throws IOException {
        String filePath = this.getClass().getClassLoader().getResource("").getPath() + "static/uploads/" + fileName;
        File newFile = FileUtil.newFile(filePath);
        FileUtil.writeFromStream(file.getInputStream(), newFile);
        return new SimpleResponse<>("/static/uploads/" + newFile.getName());
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
        res.setHeader("img-base64", QRCodeUtil.bufferedImageToImgBase64(image));
        ImageIO.write(image, "png", res.getOutputStream());
        res.getOutputStream().close();
    }

    @ApiOperation("生成PDF")
    @GetMapping("/pdf")
    public void generatePDF(HttpServletResponse res) throws IOException {
        PdfUtil.PdfDynamicParam pdfParam = new PdfUtil.PdfDynamicParam();
        pdfParam.setTemplatePath("templates/pdf/送货单.ftl");
        pdfParam.setPdfPath(DateUtil.format(new Date(), "yyyy-MM-dd_HH-mm-ss") + "_送货单.pdf");
        boolean result = PdfUtil.generatePdf(pdfParam);
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
        FileUtil.del(file);
        os.close();
    }
}
