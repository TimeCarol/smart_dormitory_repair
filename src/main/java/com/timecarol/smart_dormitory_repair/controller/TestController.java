package com.timecarol.smart_dormitory_repair.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.timecarol.smart_dormitory_repair.constant.Constant;
import com.timecarol.smart_dormitory_repair.util.PdfUtil;
import com.timecarol.smart_dormitory_repair.util.QRCodeUtil;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@Api(description = "测试控制器")
public class TestController {

    @ApiOperation("生成PDF")
    @GetMapping("/pdf")
    public void pdf(HttpServletResponse res) throws IOException {
        PdfUtil.PdfDynamicParam pdfParam = new PdfUtil.PdfDynamicParam();
        pdfParam.setTemplatePath("templates/pdf/物流退还单.ftl");
        pdfParam.setPdfPath(DateUtil.format(new Date(), "yyyy-MM-dd_HH-mm-ss") + "_物流退换单.pdf");
        Map<String, Object> param = Maps.newHashMapWithExpectedSize(20);
        param.put("printNo", "SHsunying000009");
        param.put("piece", "第一联（共四联）");
        param.put("dispatchNo", "ZY202210240127");
        param.put("printDate", new Date());
        param.put("projectName", "颀中先进封装测试生产基地项目");
        param.put("projectAddress", "安徽省合肥市瑶海区合肥综合保税区");
        param.put("authName", "苏州鹏润脚手架工程有限公司");
        param.put("mobiles", "13456743563");
        param.put("projectArea", "东北一区");
        param.put("driver", "张三");
        param.put("carNumber", "赣B23456");
        param.put("detailList", Lists.newArrayList(new TestObj()));
        param.put("remarks", "备注");
        param.put("receiveDate", "2022-11-03");
        param.put("nickName", "张三");
        pdfParam.setPdfParam(param);
        boolean result = PdfUtil.generatePdf(pdfParam);
        log.info("pdf生成结果: " + result);

        res.setContentType("application/pdf");

        File file = new File(pdfParam.getPdfPath());
        ServletOutputStream os = res.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        IoUtil.copy(fis, os);
        IoUtil.close(fis);
        IoUtil.close(os);
        FileUtil.del(file);
    }

    @PostMapping("/upload")
    @ResponseBody
    public SimpleResponse<String> upload(@RequestParam("file") MultipartFile file, @RequestParam(required = false, value = "fileName") String fileName) throws IOException {
        if (Objects.isNull(file)) {
            return SimpleResponse.error("文件不存在");
        }
        if (StrUtil.isEmpty(fileName)) {
            if (StrUtil.isNotEmpty(file.getOriginalFilename())) {
                fileName = file.getOriginalFilename();
            } else {
                log.warn("未知文件, fileSize:{} bytes, localTime:{}, time:{}", file.getSize(), DateUtil.now(), DateUtil.current());
                fileName = IdUtil.getSnowflakeNextIdStr();
            }
        }
        String dir = "uploads";
        FileUtil.mkdir(dir);
        String filePath = dir + "/" + fileName;
        File newFile = FileUtil.newFile(filePath);
        ByteArrayInputStream byteArrayInputStream = IoUtil.toStream(file.getBytes());
        InputStream inputStream = file.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        String type = FileTypeUtil.getType(byteArrayInputStream);
        IoUtil.close(byteArrayInputStream);
        String mainName = FileNameUtil.mainName(newFile.getName());
        if (StrUtil.isNotEmpty(type)) {
            newFile = FileUtil.newFile(dir + "/" + mainName + Constant.DOT + type);
        }
        FileUtil.writeFromStream(bis, newFile);
        IoUtil.close(inputStream);
        return new SimpleResponse<>(dir + "/" + newFile.getName());
    }

    @ApiOperation("获得二维码")
    @GetMapping("/qrcode")
    public void generateQRCode(HttpServletResponse res) throws IOException {
        BufferedImage image = QRCodeUtil.createImage("http://timecarol.xyz");
        res.setContentType("image/png");
        res.setHeader("img-base64", QRCodeUtil.bufferedImageToImgBase64(image));
        ImageIO.write(image, "png", res.getOutputStream());
        IoUtil.close(res.getOutputStream());
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
        IoUtil.close(res.getOutputStream());
    }

    @Data
    public static class TestObj {
        private Integer rowIndex = 1;
        private String productModel = "48-LG-2500";
        private String productSpe = "Φ48*3.25*2500";
        private String productName = "立杆";
        private String productCount = "1664";
        private String productSpeCount = "";
        private String compensationModel = "";
        private String compensationType = "";
        private String compensationCount = "";
    }

}
