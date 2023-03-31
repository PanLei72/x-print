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


public class LabelTemplate9 extends LabelPrintable {

    private static final Logger logger = LogManager.getLogger(LabelTemplate9.class);

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

        //当打印页号大于需要打印的总页数时，打印工作结束
//        if (pageIndex >= getPages()) {
//            return Printable.NO_SUCH_PAGE;
//        }

        //转换成Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;
        //设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

        //打印起点坐标
        double x = 5;
        double y = 5;

//        int _lableWidth = 284; //标签宽度
//        int _lableHeight = 170; //标签高度
        int labelWidth = Double.valueOf(pageFormat.getPaper().getWidth()).intValue() - 16;//284 标签宽度
        int labelHeight = Double.valueOf(pageFormat.getPaper().getHeight()).intValue() - 14; //标签高度

        int _lineHeight = 12; //行高
        int titleWidth = 134;

        int _titileWidth2 = 67;

        int _centerOffset = 20;

        graphics2D.translate(x, y);//转换坐标，确定打印边界
        //画四边框
        graphics2D.drawLine((int) x, (int) (y), (int) x + labelWidth, (int) (y));
        graphics2D.drawLine((int) x, (int) (y), (int) x, (int) (y + labelHeight));
        graphics2D.drawLine((int) x, (int) (y + labelHeight), (int) x + labelWidth, (int) (y + labelHeight));
        graphics2D.drawLine((int) x + labelWidth, (int) (y), (int) x + labelWidth, (int) (y + labelHeight));

        //画内部横线
        graphics2D.drawLine((int) x, (int) (y + _lineHeight), (int) x + labelWidth, (int) (y + _lineHeight));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + labelWidth - _titileWidth2, (int) (y + _lineHeight * 2));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 3), (int) x + labelWidth - _titileWidth2, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 4), (int) x + labelWidth - _titileWidth2, (int) (y + _lineHeight * 4));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 5), (int) x + labelWidth, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 6), (int) x + labelWidth, (int) (y + _lineHeight * 6));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 7), (int) x + labelWidth, (int) (y + _lineHeight * 7));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 8), (int) x + labelWidth, (int) (y + _lineHeight * 8));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 9), (int) x + labelWidth, (int) (y + _lineHeight * 9));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 10), (int) x + labelWidth, (int) (y + _lineHeight * 10));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 11), (int) x + labelWidth, (int) (y + _lineHeight * 11));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 12), (int) x + labelWidth, (int) (y + _lineHeight * 12));

        //画内部竖线
        graphics2D.drawLine((int) x + titleWidth, (int) (y + _lineHeight * 5), (int) x + titleWidth, (int) (y + labelHeight));

        graphics2D.drawLine((int) x + _titileWidth2, (int) (y + _lineHeight), (int) x + _titileWidth2, (int) (y + _lineHeight * 13));

        graphics2D.drawLine((int) x + _titileWidth2 * 3, (int) (y + _lineHeight), (int) x + _titileWidth2 * 3, (int) (y + _lineHeight * 13));


        Font font = new Font("黑体", Font.BOLD, 10);
        graphics2D.setFont(font);//设置字体

        String title = this.getLabelDefinitionDataValue("title");
        FontMetrics fm = graphics2D.getFontMetrics();
        int center = (labelWidth - fm.stringWidth(title)) / 2;
        graphics2D.drawString(title, center + (int) x, (_lineHeight - fm.getHeight()) + (int) y + 10);

        font = new Font("黑体", Font.BOLD, 8);
        graphics2D.setFont(font);//设置字体

        int _leftWhite = 3;
        int _offerY = 9;
        graphics2D.drawString(this.getLabelDefinitionDataValue("name1"), (int) x + _leftWhite, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name2"), (int) x + _leftWhite, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name3"), (int) x + _leftWhite, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name4"), (int) x + _leftWhite, (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name5"), (int) x + _leftWhite, (int) (y + _lineHeight * 6) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name6"), (int) x + _leftWhite, (int) (y + _lineHeight * 7) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftWhite, (int) (y + _lineHeight * 8) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftWhite, (int) (y + _lineHeight * 9) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + _leftWhite, (int) (y + _lineHeight * 10) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name10"), (int) x + _leftWhite, (int) (y + _lineHeight * 11) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name11"), (int) x + _leftWhite, (int) (y + _lineHeight * 12) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name12"), (int) x + _leftWhite + _centerOffset + (_titileWidth2), (int) (y + _lineHeight * 5) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name13"), (int) x + _leftWhite + _centerOffset + (_titileWidth2 * 2), (int) (y + _lineHeight * 5) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name14"), (int) x + _leftWhite + _centerOffset + (_titileWidth2 * 3), (int) (y + _lineHeight * 5) + _offerY);

        //填写内容
        JSONObject labelData = getLabelData();
        logger.warn("[" + printerName + "]" + "JSON:" + labelData.toJSONString());

        font = new Font("黑体", Font.PLAIN, 7);
        graphics2D.setFont(font);//设置字体
        String barcode = this.getLabelDataValue("barcode");

        graphics2D.drawString(barcode, (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value2"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value3"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value4"), (int) x + _leftWhite + _titileWidth2, (int) (y + _lineHeight * 4) + _offerY);

        BufferedImage _sfcBarcode = QRCodeUtility.createQRCode(barcode, _lineHeight * 4 - 2, _lineHeight * 4 - 2);
        graphics2D.drawImage(_sfcBarcode, (int) x + _leftWhite + titleWidth + _titileWidth2, (int) (y + _lineHeight) + 1, null);

        return PAGE_EXISTS;
    }


}
