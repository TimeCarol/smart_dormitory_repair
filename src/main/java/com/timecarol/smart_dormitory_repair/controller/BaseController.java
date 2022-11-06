package com.timecarol.smart_dormitory_repair.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.timecarol.smart_dormitory_repair.constant.Constant;
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
        Object smartUser = StpUtil.getTokenSession().get(Constant.SMART_USER);
        if (smartUser == null) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "您还没有登录");
        }
        return JSON.parseObject(smartUser.toString(), SmartUserDto.class);
    }

//    @PostMapping("/upload")
//    @ResponseBody
//    public SimpleResponse<String> upload(MultipartFile file, @RequestParam("fileName") String fileName) throws IOException {
//        String filePath = this.getClass().getClassLoader().getResource("").getPath() + "static/uploads/" + fileName;
//        File newFile = FileUtil.newFile(filePath);
//        FileUtil.writeFromStream(file.getInputStream(), newFile);
//        return new SimpleResponse<>("/static/uploads/" + newFile.getName());
//    }

//    @ApiOperation("生成验证码")
//    @GetMapping("/verifyCode")
//    public void generateVerifyCode(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
//        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
//        //ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
//        //告诉浏览器，这个请求用图片的方式打开
//        res.setContentType("image/jpeg");
//        //网站存在缓存，不让浏览器缓存
//        res.setDateHeader("empires", -1);
//        res.setHeader("Cache-Control", "no-cache");
//        res.setHeader("Pragma", "no-cache");
//        //图形验证码写出，可以写出到文件，也可以写出到流
//        captcha.write(res.getOutputStream());
//        //将验证码保存至Session
//        req.getSession().setAttribute("verifyCode", captcha.getCode());
//        //关闭输出流
//        res.getOutputStream().close();
//    }

//    @ApiOperation("获得验证码")
//    @GetMapping("/qrcode")
//    public void generateQRCode(HttpServletResponse res) throws IOException {
//        BufferedImage image = QRCodeUtil.createImage("http://timecarol.xyz");
//        res.setContentType("image/png");
//        res.setHeader("img-base64", QRCodeUtil.bufferedImageToImgBase64(image));
//        ImageIO.write(image, "png", res.getOutputStream());
//        res.getOutputStream().close();
//    }

//    @ApiOperation("生成PDF")
//    @GetMapping("/pdf")
//    public void pdf(HttpServletResponse res) throws IOException {
//        PdfUtil.PdfDynamicParam pdfParam = new PdfUtil.PdfDynamicParam();
//        pdfParam.setTemplatePath("templates/pdf/物流退还单.ftl");
//        pdfParam.setPdfPath(DateUtil.format(new Date(), "yyyy-MM-dd_HH-mm-ss") + "_物流退换单.pdf");
//        Map<String, Object> param = Maps.newHashMapWithExpectedSize(20);
//        param.put("printNo", "SHsunying000009");
//        param.put("piece", "第一联（共四联）");
//        param.put("dispatchNo", "ZY202210240127");
//        param.put("printDate", new Date());
//        param.put("projectName", "颀中先进封装测试生产基地项目");
//        param.put("projectAddress", "安徽省合肥市瑶海区合肥综合保税区");
//        param.put("authName", "苏州鹏润脚手架工程有限公司");
//        param.put("mobiles", "13456743563");
//        param.put("projectArea", "东北一区");
//        param.put("driver", "张三");
//        param.put("carNumber", "赣B23456");
//        param.put("detailList", Collections.emptyList());
//        param.put("remarks", "备注");
//        param.put("receiveDate", "2022-11-03");
//        param.put("nickName", "张三");
//        boolean result = PdfUtil.generatePdf(pdfParam);
//        log.info("pdf生成结果: " + result);
//
//        res.setContentType("application/pdf");
//
//        File file = new File(pdfParam.getPdfPath());
//        ServletOutputStream os = res.getOutputStream();
//        FileInputStream fis = new FileInputStream(file);
//        byte[] buffer = new byte[1024];
//        int len;
//        while ((len = fis.read(buffer)) != -1) {
//            os.write(buffer, 0, len);
//        }
//        FileUtil.del(file);
//        os.close();
//    }
}
