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


public class LabelTemplate15 extends LabelPrintable {


    private static final Logger logger = LogManager.getLogger(LabelTemplate15.class.getName());

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
            // sun.print.PeekGraphics a;
            // sun.awt.windows.WPathGraphics b;
            // 可能由于加载的机制问题，会打印两次，目前只能想办法规避这个问题；
            logger.warn("[" + printerName + "],gra class is:" + graphics.getClass().getName());
            if (graphics.getClass().getName().contains("PeekGraphics")) {
                return Printable.PAGE_EXISTS;
            }

            // 转换成Graphics2D
            Graphics2D graphics2D = (Graphics2D) graphics;
            // 设置打印颜色为黑色
            graphics2D.setColor(Color.black);
            graphics2D.setStroke(new BasicStroke(0.6f));
            // 打印起点坐标
            double x = 5;
            double y = 5;
            graphics2D.translate(x, y);
            int _lableWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 15;//284
            int _lableHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 10;
            int _lineHeight = (_lableWidth - 10) / 18;

            int _titileWidth = 60;
            int _contextWidht = 75;


            // 画四边框
            graphics2D.drawLine((int) x, (int) (y), (int) x + _lableWidth, (int) (y));
            graphics2D.drawLine((int) x, (int) (y), (int) x, (int) (y + _lineHeight * 22));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 22), (int) x + _lableWidth, (int) (y + _lineHeight * 22));
            graphics2D.drawLine((int) x + _lableWidth, (int) (y), (int) x + _lableWidth, (int) (y + _lineHeight * 22));

            // 画内部横线
            graphics2D.drawLine((int) x, (int) (y + _lineHeight), (int) x + _lableWidth, (int) (y + _lineHeight));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + _lableWidth, (int) (y + _lineHeight * 2));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 3), (int) x + _lableWidth, (int) (y + _lineHeight * 3));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 4), (int) x + _lableWidth, (int) (y + _lineHeight * 4));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 5), (int) x + _lableWidth, (int) (y + _lineHeight * 5));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 6), (int) x + _lableWidth, (int) (y + _lineHeight * 6));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 7), (int) x + _lableWidth, (int) (y + _lineHeight * 7));

            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 9), (int) x + _lableWidth, (int) (y + _lineHeight * 9));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 10), (int) x + _lableWidth, (int) (y + _lineHeight * 10));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 11), (int) x + _lableWidth, (int) (y + _lineHeight * 11));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 12), (int) x + _lableWidth, (int) (y + _lineHeight * 12));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 13), (int) x + _lableWidth, (int) (y + _lineHeight * 13));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 14), (int) x + _lableWidth, (int) (y + _lineHeight * 14));

            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 18), (int) x + _lableWidth, (int) (y + _lineHeight * 18));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 19), (int) x + _lableWidth, (int) (y + _lineHeight * 19));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 20), (int) x + _lableWidth, (int) (y + _lineHeight * 20));
            graphics2D.drawLine((int) x, (int) (y + _lineHeight * 21), (int) x + _lableWidth, (int) (y + _lineHeight * 21));

            // 画内部竖线
            graphics2D.drawLine((int) x + _titileWidth, (int) (y + _lineHeight), (int) x + _titileWidth, (int) (y + _lineHeight * 22));

            graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 3));
            graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 4), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 7));
            graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 9), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 14));
            graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 18), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 22));

            graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 3));
            graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 4), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 7));
            graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 9), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 14));
            graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 18), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 21));

            Font font = new Font("黑体", Font.BOLD, 11);
            graphics2D.setFont(font);// 设置字体

            String title = this.getLabelDefinitionDataValue("title");
            FontMetrics fm = graphics2D.getFontMetrics();
            int center = (_lableWidth - fm.stringWidth(title)) / 2;
            graphics2D.drawString(title, center + (int) x, (_lineHeight - fm.getHeight()) + (int) y + 10);

            font = new Font("黑体", Font.BOLD, 9);
            graphics2D.setFont(font);// 设置字体

            int _leftwhite = 8;
            int _offerY = 11;
            graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftwhite, (int) (y + _lineHeight) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftwhite, (int) (y + _lineHeight * 4) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftwhite, (int) (y + _lineHeight * 5) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftwhite, (int) (y + _lineHeight * 6) + _offerY);

            graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftwhite, (int) (y + _lineHeight * 7) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftwhite, (int) (y + _lineHeight * 9) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + _leftwhite, (int) (y + _lineHeight * 10) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name10"), (int) x + _leftwhite, (int) (y + _lineHeight * 11) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name11"), (int) x + _leftwhite, (int) (y + _lineHeight * 12) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name12"), (int) x + _leftwhite, (int) (y + _lineHeight * 13) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name13"), (int) x + _leftwhite, (int) (y + _lineHeight * 14) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name14"), (int) x + _leftwhite, (int) (y + _lineHeight * 15) + _offerY);

            graphics2D.drawString(this.getLabelDefinitionDataValue("name15"), (int) x + _leftwhite, (int) (y + _lineHeight * 19) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name16"), (int) x + _leftwhite, (int) (y + _lineHeight * 20) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name17"), (int) x + _leftwhite, (int) (y + _lineHeight * 21) + _offerY);

            graphics2D.drawString(this.getLabelDefinitionDataValue("name18"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name19"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 2) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name20"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 4) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name21"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 5) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name22"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 6) + _offerY);

            graphics2D.drawString(this.getLabelDefinitionDataValue("name23"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 9) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name24"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 10) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name25"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 11) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name26"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 12) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name27"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 13) + _offerY);

            graphics2D.drawString(this.getLabelDefinitionDataValue("name28"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 18) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name29"), (int) x + _leftwhite + _titileWidth + _contextWidht, (int) (y + _lineHeight * 18) + _offerY);
            graphics2D.drawString(this.getLabelDefinitionDataValue("name30"), (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 18) + _offerY);

            JSONObject labelData = getLabelData();
            logger.warn("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

            String value1 = this.getLabelDataValue("value1");
            String value2 = this.getLabelDataValue("value2");
            String value3 = this.getLabelDataValue("value3");
            String value4 = this.getLabelDataValue("value4");
            String value5 = this.getLabelDataValue("value5");
            String value6 = this.getLabelDataValue("value6");
            String value7 = this.getLabelDataValue("value7");
            String value8 = this.getLabelDataValue("value8");
            String value9 = this.getLabelDataValue("value9");
            String value10 = this.getLabelDataValue("value10");
            String value11 = this.getLabelDataValue("value11");
            String value12 = this.getLabelDataValue("value12");
            String value13 = this.getLabelDataValue("value13");
            String value14 = this.getLabelDataValue("value14");
            String value15 = this.getLabelDataValue("value15");
            String value16 = this.getLabelDataValue("value16");
            String value17 = this.getLabelDataValue("value17");
            String value18 = this.getLabelDataValue("value18");
            String value19 = this.getLabelDataValue("value19");
            String value20 = this.getLabelDataValue("value20");
            String value21 = this.getLabelDataValue("value21");
            String value22 = this.getLabelDataValue("value22");

            String barcode = this.getLabelDataValue("barcode");

            font = new Font("黑体", Font.BOLD, 7);
            graphics2D.setFont(font);
            graphics2D.drawString(value1, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight) + _offerY);
            graphics2D.drawString(value2, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 2) + _offerY);
            graphics2D.drawString(value3, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 3) + _offerY);
            graphics2D.drawString(value4, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 4) + _offerY);

            font = new Font("黑体", Font.BOLD, 13);
            graphics2D.setFont(font);
            graphics2D.drawString(value6, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * (7.5)) + _offerY);
            font = new Font("黑体", Font.BOLD, 7);
            graphics2D.setFont(font);

            graphics2D.drawString(value8, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 10) + _offerY);
            graphics2D.drawString(value9, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 11) + _offerY);
            graphics2D.drawString(value10, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 12) + _offerY);
            graphics2D.drawString(value11, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 13) + _offerY);
            graphics2D.drawString(value21, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 6) + _offerY);
            graphics2D.drawString(value22, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 6) + _offerY);

            BufferedImage _barcode = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());
            graphics2D.drawImage(_barcode, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 14 + 10), null);
            graphics2D.drawString(barcode, (int) x + _leftwhite + _titileWidth + 20, (int) (y + _lineHeight * 16 + 15) + _offerY);

            font = new Font("黑体", Font.BOLD, 13);
            graphics2D.setFont(font);
            graphics2D.drawString(value12, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight) + _offerY);
            graphics2D.drawString(value14, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 4) + _offerY);
            graphics2D.drawString(value16, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 9) + _offerY);
            graphics2D.drawString(value7, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 9) + _offerY);

            graphics2D.drawString(value5, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 5) + _offerY);
            graphics2D.drawString(value15, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 5) + _offerY);

            font = new Font("黑体", Font.BOLD, 7);
            graphics2D.setFont(font);

            graphics2D.drawString(value13, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 2) + _offerY);
            graphics2D.drawString(value17, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 10) + _offerY);
            graphics2D.drawString(value18, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 11) + _offerY);
            graphics2D.drawString(value19, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 12) + _offerY);
            graphics2D.drawString(value20, (int) x + _leftwhite + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 13) + _offerY);

            return PAGE_EXISTS;
        } catch (Exception e) {
            logger.error("[" + printerName + "]" + e.getMessage());
            return PAGE_EXISTS;
        }
    }
}
