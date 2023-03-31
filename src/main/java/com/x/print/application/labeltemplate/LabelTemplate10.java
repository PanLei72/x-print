package com.x.print.application.labeltemplate;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.application.LabelPrintable;
import com.x.print.infrastructure.utility.Barcode4jUtility;
import com.x.print.infrastructure.utility.QRCodeUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.OutputStream;


public class LabelTemplate10 extends LabelPrintable {

    private static final Logger logger = LogManager.getLogger(LabelTemplate10.class);

    /**
     * @param graphics   指明打印的图形环境
     * @param pageFormat 指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 100*60
     * @param pageIndex  指明页号
     **/
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        String printerName = super.getPrinterName();

        //sun.print.PeekGraphics a;
        //sun.awt.windows.WPathGraphics b;
        //可能由于加载的机制问题，会打印两次，目前只能想办法规避这个问题；
        logger.warn("[" + printerName + "],graphics class is:" + graphics.getClass().getName());
        if (graphics.getClass().getName().contains("PeekGraphics")) {
            return Printable.PAGE_EXISTS;
        }

        //转换成Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;
        //设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

//	      g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());//转换坐标，确定打印边界
        //打印起点坐标
        double x = 5;
        double y = 5;

//        int _lableWidth = 284; //标签宽度
//        int _lableHeight = 170; //标签高度
        int _lableWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 16;//284 标签宽度
        int _lableHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 14; //标签高度

        int _lineHeight = 17; //行高
        int _titileWidth = 134;

        int _titileWidth2 = 50;

        graphics2D.translate(x, y);//转换坐标，确定打印边界
        //画四边框
        graphics2D.drawLine((int) x, (int) (y), (int) x + _lableWidth, (int) (y));
        graphics2D.drawLine((int) x, (int) (y), (int) x, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x, (int) (y + _lableHeight), (int) x + _lableWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _lableWidth, (int) (y), (int) x + _lableWidth, (int) (y + _lableHeight));

        //画内部横线
        graphics2D.drawLine((int) x, (int) (y + _lineHeight), (int) x + _lableWidth, (int) (y + _lineHeight));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + _lableWidth, (int) (y + _lineHeight * 2));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 3), (int) x + _lableWidth, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 4), (int) x + _lableWidth, (int) (y + _lineHeight * 4));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 5), (int) x + _lableWidth, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 7) + 14, (int) x + _lableWidth, (int) (y + _lineHeight * 7) + 14);

        //画内部竖线
        graphics2D.drawLine((int) x + _titileWidth2, (int) (y + _lineHeight), (int) x + _titileWidth2, (int) (y + _lineHeight * 8) + 20);

        graphics2D.drawLine((int) x + _titileWidth + 30, (int) (y + _lineHeight * 1), (int) x + _titileWidth + 30, (int) (y + _lineHeight * 4));

        graphics2D.drawLine((int) x + _titileWidth2 + _titileWidth + 30, (int) (y + _lineHeight), (int) x + _titileWidth2 + _titileWidth + 30, (int) (y + _lineHeight * 4));

        Font font = new Font("黑体", Font.BOLD, 12);
        graphics2D.setFont(font);//设置字体

        int _offerY = 12;

        String title = this.getLabelDefinitionDataValue("title");
        FontMetrics fm = graphics2D.getFontMetrics();
        int center = (_lableWidth - fm.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x, (_lineHeight - fm.getHeight()) + (int) y + 9);

        font = new Font("黑体", Font.BOLD, 10);
        graphics2D.setFont(font);//设置字体

        int _leftWhite = 3;

        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftWhite, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftWhite, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftWhite, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftWhite, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftWhite, (int) (y + _lineHeight * 5) + _offerY * 2);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftWhite + _titileWidth + 30, (int) (y + _lineHeight * 1) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftWhite + _titileWidth + 30, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftWhite + _titileWidth + 30, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + _leftWhite, (int) (y + _lineHeight * 7) + _offerY + 17);


        //填写内容
        JSONObject labelData = getLabelData();
        logger.warn("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

        font = new Font("黑体", Font.PLAIN, 9);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(this.getLabelDataValue("value1"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value2"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value3"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value4"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value5"), (int) x + _leftWhite + _titileWidth2 + _titileWidth + 30, (int) (y + _lineHeight * 1) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value6"), (int) x + _leftWhite + _titileWidth2 + _titileWidth + 30, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value7"), (int) x + _leftWhite + _titileWidth2 + _titileWidth + 30, (int) (y + _lineHeight * 3) + _offerY);

        //条码
        String barcode = this.getLabelDataValue("barcode");
        String qrCode = this.getLabelDataValue("qrCode");

        BufferedImage _barcode = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());

        graphics2D.drawImage(_barcode, (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 5 + 8), null);
        //二维码
        BufferedImage projectItemNameQrCode = QRCodeUtility.createQRCode(qrCode, 45, 45);
        graphics2D.drawImage(projectItemNameQrCode, (int) x + _leftWhite * 20 + _titileWidth2 * 3, (int) (y + _lineHeight * 5) + 1, null);

        int img_Height = _barcode.getHeight();
        int img_width = _barcode.getWidth();
        center = (img_width - fm.stringWidth(barcode)) / 2;
        graphics2D.drawString(barcode, center + (int) x + _leftWhite + _titileWidth2,
                (int) y + _lineHeight * 5 + img_Height + fm.getHeight() + 3);

        graphics2D.drawString(this.getLabelDataValue("value8"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 7) + _offerY + 17);

        return PAGE_EXISTS;
    }


}
