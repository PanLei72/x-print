package com.x.print.application.labeltemplate;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.application.LabelPrintable;
import com.x.print.infrastructure.utility.QRCodeUtility;
import com.x.print.infrastructure.utility.ZxingUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;


public class LabelTemplate5 extends LabelPrintable {

    private static final Logger logger = LogManager.getLogger(LabelTemplate5.class.getName());

    /**
     * @param graphics       指明打印的图形环境
     * @param pageFormat        指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 100*60
     * @param pageIndex 指明页号
     **/
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex != 0)
        {
            return NO_SUCH_PAGE;
        }

        String printerName = super.getPrinterName();

        try {
            logger.warn("[" + printerName + "],gra class is:" + graphics.getClass().getName());
            if (graphics.getClass().getName().contains("PeekGraphics")) {
                return Printable.PAGE_EXISTS;
            }

            // 转换成Graphics2D
            Graphics2D g2 = (Graphics2D) graphics;
            // 设置打印颜色为黑色
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(0.6f));

            //g2.translate(pf.getImageableX(), pf.getImageableY());//转换坐标，确定打印边界
            // 打印起点坐标
            double x = 5;
            double y = 5;
            g2.translate(x, y);
            int _lableWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 15;//284
            int _lableHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 10;
            int _lineHeight = (_lableHeight - 10) / 18;

            FontMetrics fontMetrics = g2.getFontMetrics();

            Font font = new Font("黑体", Font.BOLD, 11);
            g2.setFont(font);// 设置字体
            String title = this.getLabelDefinitionDataValue("title");

            int center = (_lableWidth - fontMetrics.stringWidth(title)) / 2;
            g2.drawString(title, center + (int) x, (_lineHeight - fontMetrics.getHeight()) + (int) y + 20);

            font = new Font("黑体", Font.BOLD, 9);
            g2.setFont(font);// 设置字体

            int _leftwhite = 10;
            int _offerY = 5;

            JSONObject labelData = getLabelData();
            logger.warn("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

            g2.drawString(getLabelDefinitionDataValue("name1"), (int) x + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name2"), (int) x + _lableWidth * 7 / 13 + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name3"), (int) x + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name4"), (int) x + _lableWidth * 7 / 13 + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name5"), (int) x + _leftwhite, (int) (y + _lineHeight * 5) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name6"), (int) x + _lableWidth * 7 / 13 + _leftwhite, (int) (y + _lineHeight * 5) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name7"), (int) x + _leftwhite, (int) (y + _lineHeight * 6) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name8"), (int) x + _lableWidth * 7 / 13 + _leftwhite, (int) (y + _lineHeight * 6) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name9"), (int) x + _leftwhite, (int) (y + _lineHeight * 8) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name10"), (int) x + _leftwhite, (int) (y + _lineHeight * 9) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name11"), (int) x + _leftwhite, (int) (y + _lineHeight * 11) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name12"), (int) x + _leftwhite, (int) (y + _lineHeight * 12) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name13"), (int) x + _leftwhite, (int) (y + _lineHeight * 14) + _offerY);
            g2.drawString(getLabelDefinitionDataValue("name14"), (int) x + _leftwhite, (int) (y + _lineHeight * 15) + _offerY);

            //二维码
            String barcode = this.getLabelDataValue("barcode");
            BufferedImage _lotNoQrCode = QRCodeUtility.createQRCode(barcode);
            g2.drawImage(_lotNoQrCode, (int) x + _lableWidth * 8 / 13 + _leftwhite, 5, null);

            g2.drawString(this.getLabelDataValue("value1"), (int) x + _lableWidth * 2 / 13 + _leftwhite * 3 / 2, (int) (y + _lineHeight * 3) + _offerY);

            String value2 = this.getLabelDataValue("value2");
            g2.drawString(value2, (int) x + _lableWidth * 10 / 13 + _leftwhite, (int) (y + _lineHeight * 3));


            BufferedImage _qtyBarcode = ZxingUtility.getBarCode2(value2);
            g2.drawImage(_qtyBarcode, (int) x + _lableWidth * 9 / 13, (int) (y + _lineHeight * 3) + 3, null);

            g2.drawString(this.getLabelDataValue("value3"), (int) x + _lableWidth * 3 / 13 + _leftwhite, (int) (y + _lineHeight * 6) + _offerY);
            g2.drawString(this.getLabelDataValue("value4"), (int) x + _lableWidth * 10 / 13 + _leftwhite, (int) (y + _lineHeight * 6) + _offerY);
            g2.drawString(this.getLabelDataValue("value5"), (int) x + _lableWidth * 3 / 13 + _leftwhite, (int) (y + _lineHeight * 9) + _offerY);
            g2.drawString(this.getLabelDataValue("value6"), (int) x + _lableWidth * 3 / 13 + _leftwhite, (int) (y + _lineHeight * 12) + _offerY);
            g2.drawString(barcode, (int) x + _lableWidth * 3 / 13 + _leftwhite, (int) (y + _lineHeight * 15) + _offerY);

            BufferedImage _lotNoBarcode = ZxingUtility.getBarCode(barcode);

            g2.drawImage(_lotNoBarcode, (int) x + 15, (int) (_lineHeight * 16) + _offerY, 250, 35, null);

            font = new Font("黑体", Font.BOLD, 10);
            g2.setFont(font);// 设置字体
            g2.drawOval((int) x + _lableWidth * 8 / 13 + _leftwhite + 5, (int) (y + _lineHeight * 9) + _offerY, _lineHeight * 4, _lineHeight * 2);
            g2.drawOval((int) x + _lableWidth * 8 / 13 + _leftwhite + 5, (int) (y + _lineHeight * 12) + _offerY, _lineHeight * 4, _lineHeight * 2);
            g2.drawString(this.getLabelDefinitionDataValue("value7"), (int) x + _lableWidth * 9 / 13, (int) (y + _lineHeight * 10) + 10);
            g2.drawString(this.getLabelDefinitionDataValue("value8"), (int) x + _lableWidth * 9 / 13, (int) (y + _lineHeight * 13) + 10);

            return PAGE_EXISTS;
        } catch (Exception e) {
            logger.error("[" + printerName + "]" + e.getMessage());
            return PAGE_EXISTS;
        }
    }


}
