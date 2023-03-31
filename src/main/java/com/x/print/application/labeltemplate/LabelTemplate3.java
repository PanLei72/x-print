package com.x.print.application.labeltemplate;

import com.x.print.application.LabelPrintable;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-31
 */
public class LabelTemplate3 extends LabelPrintable {


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex != 0)
        {
            return NO_SUCH_PAGE;
        }
        //设置打印颜色为黑色
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke(0.6f));

        int x = 10;
        int y = 10;
        int cellWidth = 80;
        int cellHeight = 30;
        int rows = 5;
        int cols = 3;

        // 画表格边框
        graphics2D.drawRect(x, y, cellWidth * cols, cellHeight * rows);

        // 画横线
        for (int i = 1; i < rows; i++) {
            graphics2D.drawLine(x, y + cellHeight * i, x + cellWidth * cols, y + cellHeight * i);
        }

        // 画竖线
        for (int i = 1; i < cols; i++) {
            graphics2D.drawLine(x + cellWidth * i, y, x + cellWidth * i, y + cellHeight * rows);
        }

        // 添加文字
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String text = "Row " + i + " Col " + j;
                graphics2D.drawString(text, x + cellWidth * j + 5, y + cellHeight * i + 20);
            }
        }

        return PAGE_EXISTS;
    }
}
