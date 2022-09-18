package com.timecarol.smart_dormitory_repair.util;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * @author timecarol
 * @Description: 生成PDF的工具类
 * @date 2022年9月4日
 */
@Slf4j
public class PdfUtil {
    /* 中文字体文件位置 */
    private static final String SIMSUM_FILE = PdfUtil.class.getClassLoader().getResource("").getPath() + "font/simsun.ttc";

    /**
     * 根据pdf参数生成pdf<br>
     * Notice: pdf文件用完后别忘记删除
     *
     * @param param pdf参数
     * @return 输入流
     */
    public static InputStream buildPdf(PdfDynamicParam param) {
        if (!generatePdf(param)) {
            return null;
        }
        File file = new File(param.getPdfPath());
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            log.error("文件找不到异常: " + e);
        }
        return null;
    }

    /**
     * 根据pdf参数生成pdf<br>
     * Notice: pdf文件用完后别忘记删除
     *
     * @param param pdf参数
     * @return 是否生成成功
     */
    public static boolean generatePdf(PdfDynamicParam param) {
        //临时html文件位置
        String temporaryPdfHtml = UUID.randomUUID() + "_temporaryPdf.html";
        try {
            // 生成html合同
            generateHTML(param.getTemplatePath(), temporaryPdfHtml, param.getPdfParam());
            // 根据html合同生成pdf合同
            generatePDF(param.getPdfPath(), temporaryPdfHtml);
            // 删除临时html格式合同
            removeFile(temporaryPdfHtml);
            return true;
        } catch (Exception e) {
            log.error("生成pdf失败: " + e);
        }
        return false;
    }

    /**
     * @Description 生成html格式合同
     */
    private static void generateHTML(String templatePath, String temporaryPdfHtml, Map<String, Object> paramMap) throws Exception {

        Configuration cfg = new Configuration();
        //设置默认编码为UTF-8
        cfg.setDefaultEncoding("UTF-8");
        /**
         * 1.setClassForTemplateLoading(this.getClass(), "/HttpWeb");
         * 基于类路径，HttpWeb包下的framemaker.ftl文件
         * 2.setDirectoryForTemplateLoading(new File("/template"));
         * 基于文件系统,template目录下的文件
         * 3.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
         * 基于Servlet Context，指的是基于WebRoot下的template下的framemaker.ftl文件
         */
        //模板文件名
        String templateName = new File(templatePath.trim()).getName();
        //模板路径
        templatePath = templatePath.replace(templateName, "");
        cfg.setDirectoryForTemplateLoading(new File(templatePath));

        // templateName.ftl为要装载的模板
        Template template = cfg.getTemplate(templateName);
        //临时HTML文件
        File outHtmFile = new File(temporaryPdfHtml);

        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outHtmFile)));
        // 将参数输出到模版，并操作到HTML上
        template.process(paramMap, out);
        out.close();
    }

    /**
     * @Description 根据html生成pdf格式文件
     */
    private static void generatePDF(String pdfUrl, String temporaryPdfHtml) throws Exception {
        File htmFile = new File(temporaryPdfHtml);
        File pdfFile = new File(pdfUrl);
        String url = null;
        url = htmFile.toURI().toURL().toString();
        OutputStream os = new FileOutputStream(pdfFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        ITextFontResolver fontResolver = renderer.getFontResolver();
        // 解决中文支持问题
        fontResolver.addFont(SIMSUM_FILE, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.layout();
        renderer.createPDF(os);
        os.close();
    }

    /**
     * @Description 移除文件
     */
    private static boolean removeFile(String fileUrl) {
        File file = new File(fileUrl);
        return file.delete();
    }

    /**
     * @author timecarol
     * @Description: 生成PDF参数
     * @date 2022年9月4日
     */
    @Data
    @NoArgsConstructor
    public static class PdfDynamicParam {
        /**
         * 只需要传入resources下的相对路径<br>
         * 例如: templates/pdf/pickUp.ftl
         */
        @ApiModelProperty("模板路径(.ftl结尾)")
        private String templatePath;

        /**
         * 相对路径为项目所在位置, 可以传入绝对路径<br>
         * Notice: pdf文件用完后别忘记删除
         */
        @ApiModelProperty("pdf生成路径(.pdf结尾)")
        private String pdfPath;

        @ApiModelProperty("生成pdf所需参数")
        private Map<String, Object> pdfParam;

        public PdfDynamicParam(String templatePath, String pdfPath, Map<String, Object> pdfParam) {
            this.templatePath = templatePath;
            String path = this.getClass().getClassLoader().getResource("").getPath();
            this.templatePath = path + templatePath;
            this.pdfParam = pdfParam;
        }

        /**
         * 只需要传入resources下的相对路径<br>
         * 例如: templates/pdf/pickUp.ftl
         */
        public void setTemplatePath(String templatePath) {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            this.templatePath = path + templatePath;
        }
    }
}
