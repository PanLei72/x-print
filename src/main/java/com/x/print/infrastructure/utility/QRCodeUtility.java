package com.x.print.infrastructure.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.swetake.util.Qrcode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成二维码
 * */
public class QRCodeUtility {
    private static final Logger logger = LogManager.getLogger(QRCodeUtility.class);

    public static BufferedImage createQRCode(String content, int size) {
        BufferedImage bufImg = null;
        try {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            qrcodeHandler.setQrcodeVersion(size);
            byte[] contentBytes = content.getBytes("utf-8");
            int imgSize = 67 + 12 * (size - 1);
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, imgSize, imgSize);

            gs.setColor(Color.BLACK);
            int pixoff = 2;
            if (contentBytes.length > 0 && contentBytes.length < 800) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
            }
            gs.dispose();
            bufImg.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImg;
    }

    public static BufferedImage zoomImage(BufferedImage bufImg, int width, int height) {
        if (bufImg == null)
            return null;
        logger.info("Zoom_1");

        double wr = 0, hr = 0;
        Image Itemp = bufImg.getScaledInstance(width, height, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板
        logger.info("Zoom_2");
        wr = width * 1.0 / bufImg.getWidth();     //获取缩放比例
        hr = height * 1.0 / bufImg.getHeight();
        logger.info("Zoom_3");
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        logger.info("Zoom_4");
        return (BufferedImage) Itemp;
    }


    /**
     * 创建二维码
     *
     * @param qrData 生成二维码中要存储的信息
     * @throws Exception
     */
    public static BufferedImage createQRCode(String qrData) {
        try {
            int width = 15;
            int height = 15;
            Map<EncodeHintType, Object> encodeHints = new HashMap<EncodeHintType, Object>();
            encodeHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            encodeHints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.DATA_MATRIX, width, height, encodeHints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建二维码
     *
     * @param qrData 生成二维码中要存储的信息
     * @throws Exception
     */
    public static BufferedImage createQRCode(String qrData, int width, int height) {
        try {
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('L');//纠错等级（分为L、M、H三个等级）
            qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符
            qrcode.setQrcodeVersion(0);//版本

            //设置一下二维码的像素
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //绘图
            Graphics2D gs = bufferedImage.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.setColor(Color.BLACK);
            gs.clearRect(0, 0, width, height);//清除下画板内容

            //设置下偏移量,如果不加偏移量，有时会导致出错。
            int pixoff = 2;

            byte[] d = qrData.getBytes("utf-8");
            if (d.length > 0 && d.length < 120) {
                boolean[][] s = qrcode.calQrcode(d);
                for (int i = 0; i < s.length; i++) {
                    for (int j = 0; j < s.length; j++) {
                        if (s[j][i]) {
                            gs.fillRect(j * 2 + pixoff, i * 2 + pixoff, 2, 2);
                        }
                    }
                }
            }
            gs.dispose();
            bufferedImage.flush();
            return bufferedImage;
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

}


