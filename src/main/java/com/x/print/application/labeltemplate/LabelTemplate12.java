package com.x.print.application.labeltemplate;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.application.LabelPrintable;
import com.x.print.infrastructure.utility.Barcode4jUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.OutputStream;

public class LabelTemplate12 extends LabelPrintable {

    private static final Logger logger = LogManager.getLogger(LabelTemplate12.class);

    /**
     * @param graphics   指明打印的图形环境
     * @param pageFormat 指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
     * @param pageIndex  指明页号
     **/
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        String printerName = super.getPrinterName();

        logger.warn("[" + printerName + "]" + "Start Print");

        Graphics2D graphics2D = (Graphics2D) graphics;
        // 设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

        // 打印起点坐标
        double x = 5;
        double y = 5;
        int _lableWidth = 270;
        int _lableHeight = 150;
        int _lineHeight = 26;
        int _titileWidth = 40;
        int _contextWidht = 85;
        graphics2D.translate(x, y);// 转换坐标，确定打印边界
        // 画四边框
        graphics2D.drawLine((int) x, (int) (y), (int) x + _lableWidth, (int) (y));
        graphics2D.drawLine((int) x, (int) (y), (int) x, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x, (int) (y + _lableHeight), (int) x + _lableWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _lableWidth, (int) (y), (int) x + _lableWidth, (int) (y + _lableHeight));

        // 画内部横线
        graphics2D.drawLine((int) x, (int) (y + _lineHeight), (int) x + _lableWidth, (int) (y + _lineHeight));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + _lableWidth, (int) (y + _lineHeight * 2));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 3), (int) x + _lableWidth, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 4), (int) x + _lableWidth, (int) (y + _lineHeight * 4));

        // 画内部竖线
        graphics2D.drawLine((int) x + _titileWidth, (int) (y + _lineHeight), (int) x + _titileWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 3));

        Font font = new Font("黑体", Font.BOLD, 12);
        graphics2D.setFont(font);//设置字体
        String title = this.getLabelDefinitionDataValue("title");
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int center = (_lableWidth - fontMetrics.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x, (_lineHeight - fontMetrics.getHeight()) + (int) y + 6);

        font = new Font("新宋体", Font.PLAIN, 9);
        graphics2D.setFont(font);// 设置字体

        int _leftwhite = 3;
        int _offerY = 13;
        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftwhite, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftwhite, (int) (y + _lineHeight * 4 + _offerY + 5));
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftwhite, (int) (y + _lineHeight * 5 + 8));

        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);

        // 填写内容
        JSONObject labelData = getLabelData();
        logger.debug("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

        // 打印条码
        font = new Font("新宋体", Font.PLAIN, 12);
        graphics2D.setFont(font);// 设置字体
        graphics2D.drawString(this.getLabelDataValue("value1"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value2"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value3"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value4"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value5"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);

        String barcode = this.getLabelDataValue("barcode");
        try {
            BufferedImage barcodeImage = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());
            graphics2D.drawImage(barcodeImage, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 4 + 5), null);
            int img_Height = barcodeImage.getHeight();
            int img_width = barcodeImage.getWidth();
            center = (img_width - fontMetrics.stringWidth(barcode)) / 2;
            graphics2D.drawString(barcode, center + (int) x + _leftwhite + _titileWidth, (int) y + _lineHeight * 4 + img_Height + fontMetrics.getHeight() + 2);
        } catch (Exception ex) {
            logger.error("[" + printerName + "]" + ex.getMessage());
            return Printable.NO_SUCH_PAGE;
        }
        logger.debug("[" + printerName + "]" + "输出至打印机，开始打印");
        return PAGE_EXISTS;
    }


}
