package com.x.print.application.labeltemplate;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.application.LabelPrintable;
import com.x.print.infrastructure.utility.QRCodeUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;


public class LabelTemplate16 extends LabelPrintable {


    private static final Logger logger = LogManager.getLogger(LabelTemplate16.class);

    /**
     * @param graphics   指明打印的图形环境
     * @param pageFormat 指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 100*60
     * @param pageIndex  指明页号
     **/
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        String printerName = super.getPrinterName();

        try {
            logger.warn("[" + printerName + "],gra class is:" + graphics.getClass().getName());
            if (graphics.getClass().getName().contains("PeekGraphics")) {
                return Printable.PAGE_EXISTS;
            }

            Graphics2D g2 = (Graphics2D) graphics;
            // 设置打印颜色为黑色
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(0.6f));

            // 打印起点坐标
            double x = 5;
            double y = 5;
            g2.translate(x, y);
            int _lableWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 15;//284
            int _lableHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 10;
            int _lineHeight = (_lableWidth - 10) / 18;

            int _columnWidth1 = _lineHeight * 4;
            int _columnWidth2 = _lineHeight * 8;
            int _columnWidth3 = _lineHeight * 4;
            int _columnWidth4 = _lableWidth - _columnWidth1 - _columnWidth2 - _columnWidth3;

            // 画四边框
            g2.drawLine((int) x, (int) (y), (int) x + _lableWidth, (int) (y));
            g2.drawLine((int) x, (int) (y), (int) x, (int) (y + _lineHeight * 11));
            g2.drawLine((int) x, (int) (y + _lineHeight * 11), (int) x + _lableWidth, (int) (y + _lineHeight * 11));
            g2.drawLine((int) x + _lableWidth, (int) (y), (int) x + _lableWidth, (int) (y + _lineHeight * 11));

            // 画内部横线
            g2.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + _lableWidth, (int) (y + _lineHeight * 2));
            g2.drawLine((int) x, (int) (y + _lineHeight * 7), (int) x + _lableWidth, (int) (y + _lineHeight * 7));
            g2.drawLine((int) x, (int) (y + _lineHeight * 8), (int) x + _lableWidth, (int) (y + _lineHeight * 8));
            g2.drawLine((int) x, (int) (y + _lineHeight * 9), (int) x + _lableWidth, (int) (y + _lineHeight * 9));
            g2.drawLine((int) x, (int) (y + _lineHeight * 10), (int) x + _lableWidth, (int) (y + _lineHeight * 10));

            // 画内部竖线
            g2.drawLine((int) x + _columnWidth1, (int) (y + _lineHeight * 2), (int) x + _columnWidth1, (int) (y + _lineHeight * 11));

            g2.drawLine((int) x + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 7), (int) x + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 8));
            g2.drawLine((int) x + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 9), (int) x + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 11));
            g2.drawLine((int) x + _columnWidth1 + _columnWidth2 + _columnWidth3, (int) (y + _lineHeight * 7), (int) x + _columnWidth1 + _columnWidth2 + _columnWidth3, (int) (y + _lineHeight * 8));
            g2.drawLine((int) x + _columnWidth1 + _columnWidth2 + _columnWidth3, (int) (y + _lineHeight * 9), (int) x + _columnWidth1 + _columnWidth2 + _columnWidth3, (int) (y + _lineHeight * 11));


            FontMetrics fm = g2.getFontMetrics();

            Font font = new Font("黑体", Font.BOLD, 9);
            g2.setFont(font);// 设置字体

            int _leftwhite = 8;
            int _offerY = 11;

            g2.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
            g2.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftwhite, (int) (y + _lineHeight * 7) + _offerY);
            g2.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftwhite, (int) (y + _lineHeight * 8) + _offerY);
            g2.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftwhite, (int) (y + _lineHeight * 9) + _offerY);
            g2.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftwhite, (int) (y + _lineHeight * 10) + _offerY);

            g2.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftwhite + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 7) + _offerY);
            g2.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftwhite + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 9) + _offerY);
            g2.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftwhite + _columnWidth1 + _columnWidth2, (int) (y + _lineHeight * 10) + _offerY);


            JSONObject labelData = getLabelData();
            logger.warn("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

            String barcode = this.getLabelDataValue("barcode");
            String value1 = this.getLabelDataValue("value1");
            String value2 = this.getLabelDataValue("value2");
            String value3 = this.getLabelDataValue("value3");
            String value4 = this.getLabelDataValue("value4");
            String value5 = this.getLabelDataValue("value5");
            String value6 = this.getLabelDataValue("value6");
            String value7 = this.getLabelDataValue("value7");

            int centerSfcNo = (_lableWidth - _columnWidth1 - fm.stringWidth(barcode)) / 2;
            int centerItemNo = (_columnWidth2 - fm.stringWidth(value1)) / 2;
            int centerItemDesc = (_lableWidth - _columnWidth1 - fm.stringWidth(value2)) / 2;
            int centerProject = (_columnWidth2 - fm.stringWidth(value3)) / 2;
            int centerWord = (_columnWidth2 - fm.stringWidth(value4)) / 2;
            int centerVersion = (_columnWidth4 - fm.stringWidth(value5)) / 2;
            int centerItemName = (_columnWidth4 - fm.stringWidth(value6)) / 2;
            int centerQty = (_columnWidth4 - fm.stringWidth(value7)) / 2;

            font = new Font("黑体", Font.BOLD, 11);
            g2.setFont(font);// 设置字体

            String title = this.getLabelDefinitionDataValue("title");

            int center = (_lableWidth - fm.stringWidth(title)) / 2;
            g2.drawString(title, center + (int) x, (_lineHeight - fm.getHeight()) + (int) y + 20);

            font = new Font("黑体", Font.BOLD, 7);
            g2.setFont(font);

            BufferedImage barcodeImage = QRCodeUtility.createQRCode(barcode, _lineHeight * 5 - 6, _lineHeight * 5 - 6);
            g2.drawImage(barcodeImage, (int) x + _columnWidth1 + centerSfcNo, (int) (y + _lineHeight * 2), null);
            g2.drawString(barcode, (int) x + _columnWidth1 + centerSfcNo, (int) (y + _lineHeight * 6) + _offerY);
            g2.drawString(value1, (int) x + _leftwhite + _columnWidth1 + centerItemNo, (int) (y + _lineHeight * 7) + _offerY);
            g2.drawString(value2, (int) x + _leftwhite + _columnWidth1 + centerItemDesc, (int) (y + _lineHeight * 8) + _offerY);
            g2.drawString(value3, (int) x + _leftwhite + _columnWidth1 + centerProject, (int) (y + _lineHeight * 9) + _offerY);
            g2.drawString(value4, (int) x + _leftwhite + _columnWidth1 + centerWord, (int) (y + _lineHeight * 10) + _offerY);
            g2.drawString(value5, (int) x + _leftwhite + _columnWidth1 + _columnWidth2 + _columnWidth3 + centerVersion, (int) (y + _lineHeight * 7) + _offerY);
            g2.drawString(value6, (int) x + _leftwhite + _columnWidth1 + _columnWidth2 + _columnWidth3 + centerItemName, (int) (y + _lineHeight * 9) + _offerY);
            g2.drawString(value7, (int) x + _leftwhite + _columnWidth1 + _columnWidth2 + _columnWidth3 + centerQty, (int) (y + _lineHeight * 10) + _offerY);

            return PAGE_EXISTS;
        } catch (Exception e) {
            logger.error("[" + printerName + "]" + e.getMessage());
            return PAGE_EXISTS;
        }
    }


}
