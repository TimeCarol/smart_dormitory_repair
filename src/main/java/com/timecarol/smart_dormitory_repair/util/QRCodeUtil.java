package com.timecarol.smart_dormitory_repair.util;

import cn.hutool.core.util.StrUtil;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Hashtable;

/**
 * @Author timecarol
 * @Date 2022/9/4
 * @Version 1.0
 */
@Slf4j
public class QRCodeUtil {

    /**
     * 二维码宽度
     */
    private static final int QRCODE_SIZE = 430;
    /**
     * 编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 二维码绘制高度偏移量，留出空间写文字描述二维码信息
     */
    private static final int OFFSET_HEIGHT = 25;
    /**
     * 二维码标题字体
     */
    private static final String TITLE_FONT = "微软雅黑";
    /**
     * 标题前缀
     */
    private static final String TITLE_PREFIX = "Please use your mobile phone to scan the contents!";

    /**
     * 生成二维码
     *
     * @param content 内容
     * @return 图片
     */
    public static BufferedImage createImageWithDefaultTitle(String content) {
        return createImage(content, TITLE_PREFIX);
    }

    /**
     * 生成带标题的二维码
     *
     * @param content 扫描成功的内容
     * @param title   二维码标题
     * @return 二维码图片
     */
    private static BufferedImage createImage(String content, String title) {
        //等同于hashmap,hashtable是线程安全的
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        } catch (WriterException e) {
            log.error("生成二维码异常了..content【{}】", content, e);
            //throw new RuntimeException("生成二维码信息异常，请稍后重试");
            throw new RuntimeException("The generated QR code information is abnormal, please try again later");
        }

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        log.info("生成二维码成功！");
        //有标题，合成带标题的二维码
        if (StrUtil.isNotBlank(title)) {
            return drawDetailForQR(image, title);
        }
        //直接返回生成的二维码
        return image;
    }

    /**
     * 绘制二维码描述信息.
     *
     * @param source 源二维码图片
     * @param title  二维码标题
     * @return 合成后的图片
     */
    private static BufferedImage drawDetailForQR(BufferedImage source, String title) {
        //新建模板图片
        BufferedImage bufferedImage = new BufferedImage(QRCODE_SIZE, QRCODE_SIZE + OFFSET_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        //绘制矩形背景
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, QRCODE_SIZE, OFFSET_HEIGHT);
        //绘制描述信息
        Font font = new Font(TITLE_FONT, Font.PLAIN, 22);
        graphics.setColor(Color.black);
        graphics.setFont(font);
        graphics.drawString(TITLE_PREFIX + title, 20, OFFSET_HEIGHT - 2);
        //绘制二维码
        graphics.drawImage(source, 0, OFFSET_HEIGHT, QRCODE_SIZE, QRCODE_SIZE, null);
        graphics.dispose();
        return bufferedImage;
    }

    /**
     * 创建不带标题的二维码
     */
    public static InputStream buildImage(String content) {
        return bufferedImageToInputStream(createImage(content));
    }

    /**
     * 创建不带标题的二维码
     *
     * @param content 二维码内容
     * @return
     */
    public static BufferedImage createImage(String content) {
        return createImage(CHARSET, content, QRCODE_SIZE, QRCODE_SIZE);
    }

    /**
     * 创建不带标题的二维码
     * @param content 二维码的内容
     * @param qrWidth 二维码的宽度
     * @param qrHeight 二维码的高度
     * @return
     */
    public static BufferedImage createImage(String content, int qrWidth, int qrHeight) {
        return createImage(CHARSET, content, qrWidth, qrHeight);
    }

    /**
     * 创建二维码
     *
     * @param charSet  编码方式
     * @param content  二维码内容
     * @param qrWidth  二维码长度
     * @param qrHeight 二维码高度
     * @return
     */
    public static BufferedImage createImage(String charSet, String content, int qrWidth, int qrHeight) {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, charSet);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrWidth, qrHeight, // 修改二维码底部高度
                    hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 对已经生成好的二维码设置logo
     *
     * @param source     二维码
     * @param logo       logo图片
     * @param logoWidth  logo宽度
     * @param logoHeight logo高度
     */
    public static void insertLogoImage(BufferedImage source, Image logo, int logoWidth, int logoHeight) {
        Graphics2D graph = source.createGraphics();
        int qrWidth = source.getWidth();
        int qrHeight = source.getHeight();
        int x = (qrWidth - logoWidth) / 2;
        int y = (qrHeight - logoHeight) / 2;
        graph.drawImage(logo, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 缩小logo图片
     *
     * @param logoPath
     * @param logoWidth
     * @param logoHeight
     * @return
     */
    public static Image compressLogo(String logoPath, int logoWidth, int logoHeight) {
        File file = new File(logoPath);
        if (!file.exists()) {
            log.error("" + logoPath + "   该文件不存在！");
            return null;
        }
        Image original = null;
        try {
            original = ImageIO.read(new File(logoPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = original.getWidth(null);
        int height = original.getHeight(null);
        if (width > logoWidth) {
            width = logoWidth;
        }
        if (height > logoHeight) {
            height = logoHeight;
        }
        Image image = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        return image;
    }

    /**
     * 增加底部的说明文字
     *
     * @param source 二维码
     * @param text   说明内容
     * @param step
     */
    public static BufferedImage addBottomFont(BufferedImage source, String text, int step) {

        int qrWidth = source.getWidth();
        log.debug("二维码的宽度{}", qrWidth);
        int qrHeight = source.getHeight();
        log.debug("二维码的高度{}", qrHeight);
        BufferedImage textImage = textToImage(text, qrWidth, 20, 16);
        Graphics2D graph = source.createGraphics();
        //开启文字抗锯齿
        graph.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src = textImage;
        graph.drawImage(src, 0, qrHeight - (20 * step) - 10, width, height, null);
        graph.dispose();
        return source;
    }

    /**
     * 将文明说明增加到二维码上
     *
     * @param str
     * @param width
     * @param height
     * @param fontSize 字体大小
     * @return
     */
    private static BufferedImage textToImage(String str, int width, int height, int fontSize) {
        BufferedImage textImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font(TITLE_FONT, Font.BOLD, fontSize);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(str, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (width - fontMetrics.stringWidth(str)) / 2;
        float y = (height + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;
        g2.drawString(str, (int) offset, (int) y);
        return textImage;
    }

    /**
     * 顶部增加说明文字
     *
     * @param source
     * @param text
     */
    private static void addUpFont(BufferedImage source, String text) {
        int qrWidth = source.getWidth();
        int qrHeight = source.getHeight();

        BufferedImage textImage = textToImage(text, qrWidth, 30, 24);
        Graphics2D graph = source.createGraphics();
        //开启文字抗锯齿
        graph.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src = textImage;
        graph.drawImage(src, 0, 30, width, height, null);
        graph.dispose();
    }

    /**
     * 生成二维码图片
     *
     * @param charSet    二维码编码方式
     * @param content    内容
     * @param qrWidth    宽度
     * @param qrHeight   长度
     * @param formatName jpg等图片格式
     * @param imgPath    二维码存放路径
     */
    public static void encode(String charSet, String content, int qrWidth, int qrHeight, String formatName, String imgPath) {
        BufferedImage image = QRCodeUtil.createImage(charSet, content, qrWidth, qrHeight);
        try {
            ImageIO.write(image, formatName, new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片流
     *
     * @param charSet  二维码编码方式
     * @param content  内容
     * @param qrWidth  宽度
     * @param qrHeight 长度
     * @return
     */
    public static BufferedImage encode(String charSet, String content, int qrWidth, int qrHeight) {
        BufferedImage image = QRCodeUtil.createImage(charSet, content, qrWidth, qrHeight);
        return image;
    }

    public static void encode(BufferedImage image, String formatName, String imgPath) {
        try {

            ImageIO.write(image, formatName, new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            return file.mkdirs();
        }
        return false;
    }

    /**
     * 解析二维码, 解析完成后会自动关闭流
     * @param inputStream
     * @return 解析后的结果
     */
    public static String decode(InputStream inputStream) {
        return decode(inputStream, CHARSET);
    }

    /**
     * 解析二维码, 解析完成后会自动关闭流
     * @param inputStream 二维码输入流
     * @param charSet 编码
     * @return 解析后的结果
     */
    public static String decode(InputStream inputStream, String charSet) {
        if (inputStream == null) {
            return "";
        }
        BufferedImage image;
        try {
            image = ImageIO.read(inputStream);
            return decode(image, charSet);
        } catch (IOException e) {
            log.error("解析二维码发生错误: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("IO异常: " + e);
            }
        }
        return "";
    }

    /**
     * 解析二维码
     * @param image 图片
     * @return 解析后的结果
     */
    public static String decode(BufferedImage image) {
        return decode(image, CHARSET);
    }

    /**
     * 解析二维码
     * @param image 图片
     * @param charSet 编码
     * @return 解析后的结果
     */
    public static String decode(BufferedImage image, String charSet) {
        if (image == null) {
            return "";
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, charSet);
        try {
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (NotFoundException e) {
            log.error("解析二维码发生错误: " + e);
        }
        return "";
    }

    /**
     * 解析二维码
     * @param file 文件
     * @return 解析后的结果
     */
    public static String decode(File file) {
        return decode(file, CHARSET);
    }

    /**
     * 解析二维码
     */
    public static String decode(File file, String charSet) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            return decode(image, charSet);
        } catch (IOException e) {
            log.error("IO异常: " + e);
        }
        return "";
    }

    /**
     * 将BufferedImage转换为InputStream
     *
     * @param image
     * @return
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            log.error("提示:", e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                log.error("提示:", e);
            }
        }
        return null;
    }

    /**
     * BufferedImage转换成前端可用的图片Base64编码
     * @param image 图片
     * @return 返回Base64编码后的结果, 可以在<img src="">的src中
     */
    public static String bufferedImageToImgBase64(BufferedImage image) {
        return "data:image/png;base64," + bufferedImageToBase64(image);
    }

    /**
     * BufferedImage转Base64
     * @param image 图片
     * @return 返回Base64编码后的结果
     */
    public static String bufferedImageToBase64(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (Exception e) {
            log.error("提示:", e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                log.error("提示:", e);
            }
        }
        return "";
    }

    /**
     * 将BufferedImage保存到本地
     * @param filePath 文件路径
     * @param image 图片
     * @return 返回是否保存成功
     */
    public static boolean saveBufferedImage(String filePath, BufferedImage image) {
        if (image == null) {
            return false;
        }
        File file = new File(filePath);
        String format = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        try {
            return ImageIO.write(image, format, file);
        } catch (IOException e) {
            log.error("保存图片到本地失败: " + e);
        }
        return false;
    }

    /**
     * Description: 基础的图片生成类
     * @Author timecarol
     * @Date 2022/9/4
     * @Version 1.0
     */
    public static class BufferedImageLuminanceSource extends LuminanceSource {

        private final BufferedImage image;
        private final int left;
        private final int top;

        public BufferedImageLuminanceSource(BufferedImage image) {
            this(image, 0, 0, image.getWidth(), image.getHeight());
        }

        public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
            super(width, height);

            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();
            if (left + width > sourceWidth || top + height > sourceHeight) {
                throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
            }

            for (int y = top; y < top + height; y++) {
                for (int x = left; x < left + width; x++) {
                    if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                        image.setRGB(x, y, 0xFFFFFFFF); // = white
                    }
                }
            }

            this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
            this.image.getGraphics().drawImage(image, 0, 0, null);
            this.left = left;
            this.top = top;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            if (y < 0 || y >= getHeight()) {
                throw new IllegalArgumentException("Requested row is outside the image: " + y);
            }
            int width = getWidth();
            if (row == null || row.length < width) {
                row = new byte[width];
            }
            image.getRaster().getDataElements(left, top + y, width, 1, row);
            return row;
        }

        @Override
        public byte[] getMatrix() {
            int width = getWidth();
            int height = getHeight();
            int area = width * height;
            byte[] matrix = new byte[area];
            image.getRaster().getDataElements(left, top, width, height, matrix);
            return matrix;
        }

        @Override
        public boolean isCropSupported() {
            return true;
        }

        @Override
        public LuminanceSource crop(int left, int top, int width, int height) {
            return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
        }

        @Override
        public boolean isRotateSupported() {
            return true;
        }

        @Override
        public LuminanceSource rotateCounterClockwise() {
            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();
            AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
            BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g = rotatedImage.createGraphics();
            g.drawImage(image, transform, null);
            g.dispose();
            int width = getWidth();
            return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
        }

    }
}


