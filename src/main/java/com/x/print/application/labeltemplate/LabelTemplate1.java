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
import java.io.OutputStream;

/**
 * 外购件流程卡
 */
public class LabelTemplate1 extends LabelPrintable {


    private static final Logger logger = LogManager.getLogger(LabelTemplate1.class);

    /**
     * @param graphics   指明打印的图形环境
     * @param pageFormat 指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 100*60
     * @param pageIndex  指明页号
     **/
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if(pageIndex != 0)
        {
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

//        if (pageIndex >= getPAGES()) //当打印页号大于需要打印的总页数时，打印工作结束
//            return Printable.NO_SUCH_PAGE;

        //转换成Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;
        //设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

//        graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());//转换坐标，确定打印边界
        //打印起点坐标
        double x = 5;
        double y = 5;

//        int _lableWidth = 284; //标签宽度
//        int _lableHeight = 170; //标签高度
        int _lableWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 14;//200 标签宽度
        int _lableHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 11; //77 标签高度

        int _lineHeight = 15; //行高
        int _titileWidth = 90;

        int _titileWidth2 = 38;
        int _titileWidth3 = 28;


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

        //画内部竖线
        graphics2D.drawLine((int) x + _titileWidth3, (int) (y + _lineHeight), (int) x + _titileWidth3, (int) (y + _lineHeight * 5) + 1);

        graphics2D.drawLine((int) x + _titileWidth + 10, (int) (y + _lineHeight * 0), (int) x + _titileWidth + 10, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x + _titileWidth2 + _titileWidth + 10, (int) (y + _lineHeight * 0), (int) x + _titileWidth2 + _titileWidth + 10, (int) (y + _lineHeight * 3));

        Font font = new Font("黑体", Font.BOLD, 10);
        graphics2D.setFont(font);//设置字体

        int _offerY = 10;

        String title = this.getLabelDefinitionDataValue("title");
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int center = (_lableWidth - 90 - fontMetrics.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x, (int) y + 12);


        font = new Font("黑体", Font.PLAIN, 8);
        graphics2D.setFont(font);//设置字体

        int _leftWhite = 3;

        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftWhite, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftWhite + _titileWidth + 10, (int) (y + _lineHeight * 0) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftWhite, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftWhite + _titileWidth + 10, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftWhite + _titileWidth + 10, (int) (y + _lineHeight * 1) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftWhite, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftWhite, (int) (y + _lineHeight * 4) + _offerY);

        //填写内容
        JSONObject labelData = getLabelData();

        logger.warn("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

        String value1 = "test";
        String value3 = "test";
        String barcode = "test";
        String value2 = "test";
        String value4 = "test";
        String value5 = "test";

        try {

            value1 = this.getLabelDataValue("value1");
            value2 = this.getLabelDataValue("value2");
            value3 = this.getLabelDataValue("value3");
            value4 = this.getLabelDataValue("value4");
            value5 = this.getLabelDataValue("value5");

            barcode = this.getLabelDataValue("barcode");
        } catch (Exception e) {
            logger.error("[" + printerName + "]" + e.getMessage());
            return PAGE_EXISTS;
        }

        font = new Font("黑体", Font.PLAIN, 8);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value3, (int) x + _leftWhite + _titileWidth3, (int) (y + _lineHeight) + _offerY);

        font = new Font("黑体", Font.BOLD, 8);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value4, (int) x + _leftWhite + _titileWidth2 + _titileWidth + 10, (int) (y + _lineHeight * 0) + _offerY);

        font = new Font("黑体", Font.PLAIN, 8);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value1, (int) x + _leftWhite + _titileWidth3, (int) (y + _lineHeight * 2) + _offerY);

        graphics2D.drawString(value2, (int) x + _leftWhite + _titileWidth2 + _titileWidth + 10, (int) (y + _lineHeight * 1) + _offerY);
        graphics2D.drawString(value5, (int) x + _leftWhite + _titileWidth2 + _titileWidth + 10, (int) (y + _lineHeight * 2) + _offerY);

        BufferedImage _barcode = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());
        graphics2D.drawImage(_barcode, (int) x + _leftWhite + _titileWidth3, (int) (y + _lineHeight * 3 + 4), null);

        int img_Height = _barcode.getHeight();
        int img_width = _barcode.getWidth();
        center = (img_width - fontMetrics.stringWidth(barcode)) / 2;
        graphics2D.drawString(barcode, center + (int) x + _leftWhite + _titileWidth3,
                (int) 2 + _lineHeight * 3 + img_Height + fontMetrics.getHeight() + 1);

        return PAGE_EXISTS;
    }

}
