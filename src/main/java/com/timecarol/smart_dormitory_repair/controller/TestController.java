package com.timecarol.smart_dormitory_repair.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.timecarol.smart_dormitory_repair.util.PdfUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

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
