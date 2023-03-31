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


public class LabelTemplate14 extends LabelPrintable {


    private static final Logger logger = LogManager.getLogger(LabelTemplate14.class.getName());

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

        //sun.print.PeekGraphics a;
        //sun.awt.windows.WPathGraphics b;
        //可能由于加载的机制问题，会打印两次，目前只能想办法规避这个问题；
        logger.warn("[" + printerName + "],gra class is:" + graphics.getClass().getName());
        if (graphics.getClass().getName().contains("PeekGraphics")) {
            return Printable.PAGE_EXISTS;
        }

        Graphics2D graphics2D = (Graphics2D) graphics;
        //设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

        //打印起点坐标
        double x = 5;
        double y = 5;
        int _lableWidth = 270;
        int _lableHeight = 150;
        int _lineHeight = 18;
        int _titileWidth = 30;
        int _titileWidth2 = 15;
        int _contextWidht = 75;
        int _contextWidht2 = 55;

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
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 6), (int) x + _lableWidth, (int) (y + _lineHeight * 6));

        //画内部竖线
        graphics2D.drawLine((int) x + _titileWidth, (int) (y + _lineHeight), (int) x + _titileWidth, (int) (y + _lineHeight * 4));//(int)x+_titileWidth,(int)(y+_lableHeight));
        graphics2D.drawLine((int) x + _titileWidth, (int) (y + _lineHeight * 6), (int) x + _titileWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _titileWidth2, (int) (y + _lineHeight * 4), (int) x + _titileWidth2, (int) (y + _lineHeight * 6));

        graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight), (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 4));
        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight), (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 4));

        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht * 2, (int) (y + _lineHeight), (int) x + _titileWidth * 2 + _contextWidht * 2, (int) (y + _lineHeight * 4));
        graphics2D.drawLine((int) x + _titileWidth * 3 + _contextWidht * 2, (int) (y + _lineHeight), (int) x + _titileWidth * 3 + _contextWidht * 2, (int) (y + _lineHeight * 4));

        graphics2D.drawLine((int) x + _titileWidth2 + _contextWidht2, (int) (y + _lineHeight * 4), (int) x + _titileWidth2 + _contextWidht2, (int) (y + _lineHeight * 6));
        graphics2D.drawLine((int) x + _titileWidth2 * 2 + _contextWidht2, (int) (y + _lineHeight * 4), (int) x + _titileWidth2 * 2 + _contextWidht2, (int) (y + _lineHeight * 6));

        graphics2D.drawLine((int) x + _titileWidth2 * 2 + _contextWidht2 * 2, (int) (y + _lineHeight * 4), (int) x + _titileWidth2 * 2 + _contextWidht2 * 2, (int) (y + _lineHeight * 6));
        graphics2D.drawLine((int) x + _titileWidth2 * 3 + _contextWidht2 * 2, (int) (y + _lineHeight * 4), (int) x + _titileWidth2 * 3 + _contextWidht2 * 2, (int) (y + _lineHeight * 6));

        graphics2D.drawLine((int) x + _titileWidth2 * 3 + _contextWidht2 * 3, (int) (y + _lineHeight * 4), (int) x + _titileWidth2 * 3 + _contextWidht2 * 3, (int) (y + _lineHeight * 6));
        graphics2D.drawLine((int) x + _titileWidth2 * 4 + _contextWidht2 * 3, (int) (y + _lineHeight * 4), (int) x + _titileWidth2 * 4 + _contextWidht2 * 3, (int) (y + _lineHeight * 6));



        Font font = new Font("黑体", Font.BOLD, 11);
        graphics2D.setFont(font);//设置字体

        String title = this.getLabelDefinitionDataValue("title");
        FontMetrics fm = graphics2D.getFontMetrics();
        int center = (_lableWidth - fm.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x, (_lineHeight - fm.getHeight()) + (int) y + 10);


        font = new Font("黑体", Font.BOLD, 9);
        graphics2D.setFont(font);//设置字体

        int _leftwhite = 3;
        int _offerY = 13;
        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftwhite, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftwhite, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftwhite, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftwhite, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftwhite, (int) (y + _lineHeight * 5) + _offerY);

        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftwhite, (int) (y + _lineHeight * 6 + _offerY + 5));
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftwhite, (int) (y + _lineHeight * 7 + 8));


        graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name10"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name11"), (int) x + _leftwhite + (_titileWidth2 + _contextWidht2), (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name12"), (int) x + _leftwhite + (_titileWidth2 + _contextWidht2), (int) (y + _lineHeight * 5) + _offerY);

        graphics2D.drawString(this.getLabelDefinitionDataValue("name13"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight) + _offerY);

        graphics2D.drawString(this.getLabelDefinitionDataValue("name14"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name15"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name16"), (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 2, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name17"), (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 2, (int) (y + _lineHeight * 5) + _offerY);

        graphics2D.drawString(this.getLabelDefinitionDataValue("name18"), (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 3, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name19"), (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 3, (int) (y + _lineHeight * 5) + _offerY);

        //填写内容
        JSONObject _flowcard = getLabelData();

        logger.warn("[" + printerName + "]" + "JSON:" + _flowcard.toJSONString());
        String value1 = "test";//工单
        String value2 = "test";//物料号
        String value3 = "test";//时间


        String value4 = "test";//项目名
        String value5 = "test";//模具
        String value6 = "test";//配对


        String value7 = "test";//数量

        String barcode = "test";//一维条码

        String value8 = "test";
        String value9 = "test";
        String value13 = "test";
        String value10 = "test";
        String value14 = "test";
        String value11 = "test";
        String value15 = "test";

        String value12 = "batch7null";
        String value16 = "batch8null";
        String value17 = "zpresource";
        String value18 = "jixiaolong";
        try {

            value1 = this.getLabelDataValue("value1");
            value2 = this.getLabelDataValue("value2");
            value3 = this.getLabelDataValue("value3");
            value4 = this.getLabelDataValue("value4");
            value5 = this.getLabelDataValue("value5");
            value6 = this.getLabelDataValue("value6");
            value7 = this.getLabelDataValue("value7");
            value8 = this.getLabelDataValue("value8");
            value9 = this.getLabelDataValue("value9");
            value10 = this.getLabelDataValue("value10");
            value11 =this.getLabelDataValue("value11");
            value12 = this.getLabelDataValue("value12");
            value13 = this.getLabelDataValue("value13");
            value14 = this.getLabelDataValue("value14");
            value15 = this.getLabelDataValue("value15");
            value16 = this.getLabelDataValue("value16");
            value17 = this.getLabelDataValue("value17");
            value18 =this.getLabelDataValue("value18");

            barcode = this.getLabelDataValue("barcode");
        } catch (Exception e) {
            logger.error("[" + printerName + "]" + e.getMessage());
            return PAGE_EXISTS;
        }

        font = new Font("黑体", Font.BOLD, 7);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value8 + "#", _lableWidth - 3 * (int) x, (_lineHeight - fm.getHeight()) + (int) y + 10);
        //工单,配对
        graphics2D.drawString(value1, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight) + _offerY);

        font = new Font("黑体", Font.BOLD, 5);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value6, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 2) + _offerY);
        //时间
        font = new Font("黑体", Font.PLAIN, 7);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value3, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 3) + _offerY);

        font = new Font("黑体", Font.BOLD, 7);
        graphics2D.setFont(font);
        graphics2D.drawString(value9, (int) x + _leftwhite + _titileWidth2, (int) (y + _lineHeight * 4) + _offerY);

        graphics2D.setFont(font);
        graphics2D.drawString(value13, (int) x + _leftwhite + _titileWidth2, (int) (y + _lineHeight * 5) + _offerY);

        font = new Font("黑体", Font.BOLD, 7);
        graphics2D.setFont(font);
        graphics2D.drawString(value4, (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(value2, (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(value18, (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht), (int) (y + _lineHeight * 3) + _offerY);

        font = new Font("黑体", Font.BOLD, 5);
        graphics2D.setFont(font);
        graphics2D.drawString(value17, (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth, (int) (y + _lineHeight * 1) + _offerY);

        font = new Font("黑体", Font.BOLD, 7);
        graphics2D.setFont(font);

        graphics2D.drawString(value7, (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(value5, (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth, (int) (y + _lineHeight * 3) + _offerY);


        graphics2D.drawString(value10, (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) + _titileWidth2, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(value14, (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) + _titileWidth2, (int) (y + _lineHeight * 5) + _offerY);

        font = new Font("黑体", Font.BOLD, 7);
        graphics2D.setFont(font);
        graphics2D.drawString(value11, (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 2 + _titileWidth2, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(value15, (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 2 + _titileWidth2, (int) (y + _lineHeight * 5) + _offerY);

        font = new Font("黑体", Font.BOLD, 7);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(value12, (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 3 + _titileWidth2, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(value16, (int) x + _leftwhite + (_titileWidth2 + _contextWidht2) * 3 + _titileWidth2, (int) (y + _lineHeight * 5) + _offerY);

        //打印条码
        font = new Font("黑体", Font.PLAIN, 9);
        graphics2D.setFont(font);//设置字体
        BufferedImage _barcode = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());

        graphics2D.drawImage(_barcode, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 6 + 12), null);

        int img_Height = _barcode.getHeight();
        int img_width = _barcode.getWidth();
        center = (img_width - fm.stringWidth(barcode)) / 2;
        font = new Font("黑体", Font.BOLD, 9);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(barcode, center + (int) x + _leftwhite + _titileWidth, (int) y + _lineHeight * 4 + img_Height + fm.getHeight() + 12);

        return PAGE_EXISTS;
    }

}
