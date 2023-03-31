package com.x.print.application.labeltemplate;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.print.application.LabelPrintable;
import com.x.print.infrastructure.utility.Barcode4jUtility;
import com.x.print.infrastructure.utility.QRCodeUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.OutputStream;
import java.net.URLDecoder;

public class LabelTemplate8 extends LabelPrintable {

    private static final Logger logger = LogManager.getLogger(LabelTemplate8.class);

    /**
     * @param graphics 指明打印的图形环境
     * @param pageFormat 指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 100*60
     * @param pageIndex 指明页号
     **/
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex != 0)
        {
            return NO_SUCH_PAGE;
        }

        String printerName = super.getPrinterName();

        logger.warn("[" + printerName + "],gra class is:" + graphics.getClass().getName());
        //sun.print.PeekGraphics a;
        //sun.awt.windows.WPathGraphics b;
        //可能由于加载的机制问题，会打印两次，目前只能想办法规避这个问题；
        if (graphics.getClass().getName().contains("PeekGraphics")) {
            return Printable.PAGE_EXISTS;
        }

//		Long PAGES = getPAGES();
//		if (pageIndex >= PAGES) // 当打印页号大于需要打印的总页数时，打印工作结束
//			return Printable.NO_SUCH_PAGE;

        // 转换成Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;
        // 设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

//	      g2.translate(pf.getImageableX(), pf.getImageableY());//转换坐标，确定打印边界
        // 打印起点坐标
        double x = 5;
        double y = 5;
        int _lableWidth = 270;
        int _lableHeight = 150;
        int _lineHeight = 21;
        int _titileWidth = 30;
        int _contextWidht = 75;
        graphics2D.translate(x, y);// 转换坐标，确定打印边界
        // 画四边框
        graphics2D.drawLine((int) x, (int) (y), (int) x + _lableWidth, (int) (y));
        graphics2D.drawLine((int) x, (int) (y), (int) x, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x, (int) (y + _lableHeight), (int) x + _lableWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _lableWidth, (int) (y), (int) x + _lableWidth, (int) (y + _lableHeight));

        // 画内部横线

        graphics2D.drawLine((int) x, (int) (y + _lineHeight), (int) x + 2 * (_titileWidth + _contextWidht), (int) (y + _lineHeight));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + _lableWidth, (int) (y + _lineHeight * 2));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 3), (int) x + _lableWidth, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 4), (int) x + _lableWidth, (int) (y + _lineHeight * 4));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 5), (int) x + _lableWidth, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x + 2 * (_titileWidth + _contextWidht), (int) (y + _lineHeight * 6), (int) x + _lableWidth, (int) (y + _lineHeight * 6));


        // 画内部竖线
        graphics2D.drawLine((int) x + _titileWidth, (int) (y + _lineHeight), (int) x + _titileWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht * 2, (int) (y), (int) x + _titileWidth * 2 + _contextWidht * 2, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _titileWidth * 3 + _contextWidht * 2, (int) (y + 2 * _lineHeight), (int) x + _titileWidth * 3 + _contextWidht * 2, (int) (y + _lableHeight));

        Font font = new Font("新宋体", Font.PLAIN, 11);
        graphics2D.setFont(font);// 设置字体

        String title = this.getLabelDefinitionDataValue("title");
        FontMetrics fm = graphics2D.getFontMetrics();
        int center = (_lableWidth - (_titileWidth + _contextWidht) * 2 - fm.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x + 15, (_lineHeight - fm.getHeight()) + (int) y + 6);

        font = new Font("新宋体", Font.PLAIN, 9);
        graphics2D.setFont(font);// 设置字体

        int _leftwhite = 3;
        int _offerY = 13;
        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftwhite, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftwhite, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftwhite, (int) (y + _lineHeight * 5 + _offerY + 5));
        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftwhite, (int) (y + _lineHeight * 6 + 8));
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + _leftwhite + (_titileWidth + _contextWidht),     (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name10"), (int) x + _leftwhite + (_titileWidth + _contextWidht),  (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name11"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name12"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name13"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name14"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name15"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 5) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name16"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 6) + _offerY);

        // 填写内容
        JSONObject labelData = getLabelData();
        logger.debug("[" + printerName + "]" + "JSON:" + labelData.toJSONString());
        try {

            graphics2D.drawString(this.getLabelDataValue("value1"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value2"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 2) + _offerY);

            logger.info("[" + printerName + "]" + "font,Line:180");

            font = new Font("新宋体", Font.PLAIN, 7);
            graphics2D.setFont(font);// 设置字体
            graphics2D.drawString(this.getLabelDataValue("value3"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 3) + _offerY);


            font = new Font("黑体", Font.BOLD, 14);
            graphics2D.setFont(font);//设置字体
            graphics2D.drawString(this.getLabelDataValue("value4"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht),
                    (int) (y) + _offerY);

            logger.info("[" + printerName + "]" + "Line:187");

            font = new Font("黑体", Font.BOLD, 14);
            graphics2D.setFont(font);//设置字体
            graphics2D.drawString(this.getLabelDataValue("value5"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value6"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value7"), (int) x + 3 * _titileWidth + 2 * _contextWidht + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value8"), (int) x + 3 * _titileWidth + 2 * _contextWidht + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value9"), (int) x + 3 * _titileWidth + 2 * _contextWidht + _leftwhite, (int) (y + _lineHeight * 4) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value10"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 4) + _offerY);

            font = new Font("新宋体", Font.PLAIN, 9);
            graphics2D.setFont(font);// 设置字体

            graphics2D.drawString(this.getLabelDataValue("value11"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight * 3) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value12"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight * 4) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value13"), (int) x + 3 * _titileWidth + 2 * _contextWidht + _leftwhite, (int) (y + _lineHeight * 5) + _offerY);
            graphics2D.drawString(this.getLabelDataValue("value14"), (int) x + 3 * _titileWidth + 2 * _contextWidht + _leftwhite, (int) (y + _lineHeight * 6) + _offerY);
            logger.info("[" + printerName + "]" + "Line:214");

            // 打印条码
            String barcode = this.getLabelDataValue("barcode");
            String qrCode = this.getLabelDataValue("barcode");

            font = new Font("新宋体", Font.PLAIN, 12);
            graphics2D.setFont(font);// 设置字体
            BufferedImage _barcode = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());
            graphics2D.drawImage(_barcode, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 5 + 5), null);

            // 打印二维码
            BufferedImage _qrcode_Bi = QRCodeUtility.createQRCode(qrCode, 2);
            BufferedImage _qrcode_Bi_Zoom = QRCodeUtility.zoomImage(_qrcode_Bi, 40, 40);
            graphics2D.drawImage(_qrcode_Bi_Zoom, _lableWidth - 42, (int) (y + 1), null);
            int img_Height = _barcode.getHeight();
            int img_width = _barcode.getWidth();
            center = (img_width - fm.stringWidth(barcode)) / 2;
            graphics2D.drawString(barcode, center + (int) x + _leftwhite + _titileWidth,
                    (int) y + _lineHeight * 5 + img_Height + fm.getHeight() + 2);

        } catch (Exception ex) {
            logger.error("[" + printerName + "]" + ex.getMessage());
            return PAGE_EXISTS;
        }
        logger.debug("[" + printerName + "]" + "draw _qrcode_Bi_Zoom end");

        return PAGE_EXISTS;
    }

}
