package com.x.print.application.labeltemplate;

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

public class LabelTemplate6 extends LabelPrintable {

    private static final Logger logger = LogManager.getLogger(LabelTemplate6.class);

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

        logger.warn("[" + printerName + "]" + "Start Print");
        // 转换成Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;
        // 设置打印颜色为黑色
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

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
        graphics2D.drawLine((int) x, (int) (y + _lineHeight), (int) x + _lableWidth, (int) (y + _lineHeight));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 2), (int) x + _lableWidth, (int) (y + _lineHeight * 2));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 3), (int) x + _lableWidth, (int) (y + _lineHeight * 3));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 4), (int) x + _lableWidth, (int) (y + _lineHeight * 4));
        graphics2D.drawLine((int) x, (int) (y + _lineHeight * 5), (int) x + _lableWidth, (int) (y + _lineHeight * 5));

        // 画内部竖线
        graphics2D.drawLine((int) x + _titileWidth, (int) (y + _lineHeight), (int) x + _titileWidth, (int) (y + _lableHeight));
        graphics2D.drawLine((int) x + _titileWidth + _contextWidht, (int) (y),
                (int) x + _titileWidth + _contextWidht, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht, (int) (y),
                (int) x + _titileWidth * 2 + _contextWidht, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x + _titileWidth * 2 + _contextWidht * 2, (int) (y),
                (int) x + _titileWidth * 2 + _contextWidht * 2, (int) (y + _lineHeight * 5));
        graphics2D.drawLine((int) x + _titileWidth * 3 + _contextWidht * 2, (int) (y),
                (int) x + _titileWidth * 3 + _contextWidht * 2, (int) (y + _lineHeight * 5));

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
        graphics2D.drawString(this.getLabelDefinitionDataValue("name7"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name8"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name9"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name10"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name11"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name12"), (int) x + _leftwhite + (_titileWidth + _contextWidht), (int) (y + _lineHeight * 4) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name13"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name14"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name15"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDefinitionDataValue("name16"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2, (int) (y + _lineHeight * 4) + _offerY);

        // 填写内容
        graphics2D.drawString(this.getLabelDataValue("value1"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value2"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 2) + _offerY);

        font = new Font("新宋体", Font.PLAIN, 7);
        graphics2D.setFont(font);// 设置字体
        graphics2D.drawString(this.getLabelDataValue("value3"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 3) + _offerY);

        font = new Font("黑体", Font.BOLD, 14);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(this.getLabelDataValue("value4"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht),
                (int) (y) + _offerY);
        font = new Font("黑体", Font.BOLD, 12);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(this.getLabelDataValue("value5"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth, (int) (y) + _offerY);

        font = new Font("黑体", Font.BOLD, 14);
        graphics2D.setFont(font);//设置字体
        graphics2D.drawString(this.getLabelDataValue("value6"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht),
                (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value7"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht),
                (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value8"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth,
                (int) (y + _lineHeight) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value9"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth,
                (int) (y + _lineHeight * 2) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value10"), (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 4) + _offerY);

        font = new Font("新宋体", Font.PLAIN, 9);
        graphics2D.setFont(font);// 设置字体

        graphics2D.drawString(this.getLabelDataValue("value11"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht),
                (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value12"), (int) x + _leftwhite + (_titileWidth * 2 + _contextWidht),
                (int) (y + _lineHeight * 4) + _offerY);

        graphics2D.drawString(this.getLabelDataValue("value13"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth,
                (int) (y + _lineHeight * 3) + _offerY);
        graphics2D.drawString(this.getLabelDataValue("value14"), (int) x + _leftwhite + (_titileWidth + _contextWidht) * 2 + _titileWidth,
                (int) (y + _lineHeight * 4) + _offerY);

        // 打印条码
        font = new Font("新宋体", Font.PLAIN, 12);
        graphics2D.setFont(font);// 设置字体

        try {
            String barcode = this.getLabelDataValue("barcode");
            String qrCode = this.getLabelDataValue("qrCode");
            BufferedImage bufferedImage = Barcode4jUtility.generateBarcode(barcode, true, OutputStream.nullOutputStream());
            graphics2D.drawImage(bufferedImage, (int) x + _leftwhite + _titileWidth, (int) (y + _lineHeight * 5 + 5), null);

            // 打印二维码
            BufferedImage qrCodeImage = QRCodeUtility.createQRCode(qrCode, 2);
            BufferedImage _qrcode_Bi_Zoom = QRCodeUtility.zoomImage(qrCodeImage, 40, 40);
            graphics2D.drawImage(_qrcode_Bi_Zoom, _lableWidth - 42, (int) (y + _lineHeight * 5 + 3), null);
            int img_Height = bufferedImage.getHeight();
            int img_width = bufferedImage.getWidth();
            center = (img_width - fm.stringWidth(barcode)) / 2;
            graphics2D.drawString(barcode, center + (int) x + _leftwhite + _titileWidth,
                    (int) y + _lineHeight * 5 + img_Height + fm.getHeight() + 2);
        } catch (Exception ex) {
            logger.error("[" + printerName + "]" + ex.getMessage());
            return Printable.NO_SUCH_PAGE;
        }
        logger.debug("[" + printerName + "]" + "输出至打印机，开始打印");
        return PAGE_EXISTS;
    }


}
