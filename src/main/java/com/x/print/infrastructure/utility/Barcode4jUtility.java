package com.x.print.infrastructure.utility;

import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-30
 */
public class Barcode4jUtility {


    /**
     * 生成条形码到输出流
     *
     * @param msg
     * @param ous
     */
    public static BufferedImage generateBarcode(String msg, boolean hideText, int dpi, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return null;
        }
        // 如果想要其他类型的条码(CODE 39, EAN-8...)直接获取相关对象Code39Bean...等等
        Code128Bean bean = new Code128Bean();
        // 分辨率：值越大条码越长，分辨率越高。
        // 设置两侧是否加空白
        bean.doQuietZone(true);
        // 设置条码每一条的宽度
        // UnitConv 是barcode4j 提供的单位转换的实体类，用于毫米mm,像素px,英寸in,点pt之间的转换
        bean.setModuleWidth(UnitConv.in2mm(1.25f / dpi));

        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        String format = "image/png";
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
                    false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, msg);
            // 结束绘制
            canvas.finish();

            BufferedImage bufferedImage = canvas.getBufferedImage();
            return bufferedImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成条形码到输出流
     *
     * @param msg
     * @param ous
     */
    public static BufferedImage generateBarcode(String msg, boolean hideText, OutputStream ous) {
        return Barcode4jUtility.generateBarcode(msg, hideText, 40, ous);
    }

}
