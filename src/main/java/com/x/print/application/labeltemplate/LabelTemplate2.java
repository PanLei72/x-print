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
public class LabelTemplate2 extends LabelPrintable {
    private static final Logger logger = LogManager.getLogger(LabelTemplate2.class);

    //打印起点坐标
    double x = 5;
    double y = 5;

    private int lineHeight = 22; //行高
    private int titleWidth = 134;

    private int titleWidth2 = 50;

    private int labelWidth;//284 标签宽度
    private int labelHeight; //标签高度

    private int leftWhite = 3;

    private int offerY = 15;

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

//        int _lableWidth = 284; //标签宽度
//        int _lableHeight = 170; //标签高度
        labelWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 16;//284 标签宽度
        labelHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 14; //标签高度

        this.drawLine(graphics2D);
        this.drawDefinitionData(graphics2D);
        this.drawLabelData(graphics2D);

        return PAGE_EXISTS;
    }

    private void drawLine(Graphics2D graphics2D) {
        graphics2D.translate(x, y);//转换坐标，确定打印边界

        //画四边框
        graphics2D.drawLine((int) x, (int) (y), (int) x + labelWidth, (int) (y));
        graphics2D.drawLine((int) x, (int) (y), (int) x, (int) (y + labelHeight));
        graphics2D.drawLine((int) x, (int) (y + labelHeight), (int) x + labelWidth, (int) (y + labelHeight));
        graphics2D.drawLine((int) x + labelWidth, (int) (y), (int) x + labelWidth, (int) (y + labelHeight));

        //画内部横线
        graphics2D.drawLine((int) x, (int) (y + lineHeight), (int) x + labelWidth, (int) (y + lineHeight));
        graphics2D.drawLine((int) x, (int) (y + lineHeight * 2), (int) x + labelWidth, (int) (y + lineHeight * 2));
        graphics2D.drawLine((int) x, (int) (y + lineHeight * 3), (int) x + labelWidth, (int) (y + lineHeight * 3));
        graphics2D.drawLine((int) x, (int) (y + lineHeight * 4), (int) x + labelWidth, (int) (y + lineHeight * 4));
        graphics2D.drawLine((int) x, (int) (y + lineHeight * 5), (int) x + labelWidth, (int) (y + lineHeight * 5));


        //画内部竖线//画内部竖线
        graphics2D.drawLine((int) x + titleWidth2, (int) (y + lineHeight), (int) x + titleWidth2, (int) (y + lineHeight * 7) + 2);
        graphics2D.drawLine((int) x + titleWidth + 30, (int) (y + lineHeight * 1), (int) x + titleWidth + 30, (int) (y + lineHeight * 5));
        graphics2D.drawLine((int) x + titleWidth2 + titleWidth + 30, (int) (y + lineHeight), (int) x + titleWidth2 + titleWidth + 30, (int) (y + lineHeight * 5));

    }

    private void drawDefinitionData(Graphics2D graphics2D) {
        Font font = new Font("黑体", Font.BOLD, 12);
        graphics2D.setFont(font);//设置字体

        String title = this.getLabelDefinitionDataValue("name1");

        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int center = (labelWidth - fontMetrics.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x, (lineHeight - fontMetrics.getHeight()) + (int) y + 7);

        font = new Font("黑体", Font.BOLD, 10);
        graphics2D.setFont(font);//设置字体


        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + leftWhite, (int) (y + lineHeight) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + leftWhite, (int) (y + lineHeight * 2) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + leftWhite, (int) (y + lineHeight * 3) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + leftWhite, (int) (y + lineHeight * 4) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + leftWhite, (int) (y + lineHeight * 5) + offerY * 2);

        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + leftWhite + titleWidth + 30, (int) (y + lineHeight * 1) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + leftWhite + titleWidth + 30, (int) (y + lineHeight * 2) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + leftWhite + titleWidth + 30, (int) (y + lineHeight * 3) + offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + leftWhite + titleWidth + 30, (int) (y + lineHeight * 4) + offerY);
    }

    private void drawLabelData(Graphics2D graphics2D) {
        //填写内容
        JSONObject labelDataJSONObject = getLabelData();
        String mPrinterName = getPrinterName();
        logger.warn("[" + mPrinterName + "]" + "JSON:" + labelDataJSONObject.toJSONString());

        String _sfc = "test";//批次编码

        try {
            if(labelDataJSONObject.getString("sfc")  != null) {
                _sfc = labelDataJSONObject.getString("sfc");
            }

        } catch (Exception e) {
            logger.error("[" + mPrinterName + "]" + e.getMessage());
            throw new RuntimeException(e);
        }

        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        Font font = new Font("黑体", Font.PLAIN, 9);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(this.getLabelDataValue("value1"), (int) x + leftWhite + titleWidth2, (int) (y + lineHeight) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value2"), (int) x + leftWhite + titleWidth2, (int) (y + lineHeight * 2) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value3"), (int) x + leftWhite + titleWidth2, (int) (y + lineHeight * 3) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value4"), (int) x + leftWhite + titleWidth2, (int) (y + lineHeight * 4) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value5"), (int) x + leftWhite + titleWidth2 + titleWidth + 30, (int) (y + lineHeight * 1) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value6"), (int) x + leftWhite + titleWidth2 + titleWidth + 30, (int) (y + lineHeight * 2) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value7"), (int) x + leftWhite + titleWidth2 + titleWidth + 30, (int) (y + lineHeight * 3) + offerY);
        graphics2D.drawString(this.getLabelDataValue("value8"), (int) x + leftWhite + titleWidth2 + titleWidth + 30, (int) (y + lineHeight * 4) + offerY);

        //画条形码
        BufferedImage barcode = Barcode4jUtility.generateBarcode(_sfc, true, OutputStream.nullOutputStream());
        graphics2D.drawImage(barcode, (int) x + leftWhite + titleWidth2, (int) (y + lineHeight * 5 + 4), null);
        int imageHeight = barcode.getHeight();
        int imageWidth = barcode.getWidth();
        int center = (imageWidth - fontMetrics.stringWidth(_sfc)) / 2;
        graphics2D.drawString(_sfc, center + (int) x + leftWhite + titleWidth2, (int) y + lineHeight * 5 + imageHeight + fontMetrics.getHeight() + 2);

    }
}
