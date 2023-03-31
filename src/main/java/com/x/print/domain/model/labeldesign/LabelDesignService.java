package com.x.print.domain.model.labeldesign;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.application.LabelPrintable;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;
import com.x.print.domain.model.labeldatadefinition.LabelVariable;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class LabelDesignService implements ILabelDesignService {
    @Autowired
    private DataManager dataManager;

    @Override
    public List<LabelDesign> loadAllLabelDesign() {
        List<LabelDesign> labelDesignList = dataManager.load(LabelDesign.class).all().sort(Sort.by("labelName")).list();

        return labelDesignList;
    }

    @Override
    public LabelDesign loadLabelDesignByLabelName(String labelName) {
        LabelDesign labelDesign =  dataManager.load(LabelDesign.class)
                .query("select e from LabelDesign e where e.labelName=:labelName")
                .parameter("labelName", labelName)
                .optional()
                .orElse(null);
        return labelDesign;
    }

    @Override
    public byte[] createLabelImageSample(LabelDesign labelDesign) {
        double width = labelDesign.getWidth();
        double height = labelDesign.getHeight();
        Integer orientation = labelDesign.getOrientation().getId();

        Paper paper = new Paper();
        PageFormat pageFormat = new PageFormat();
        paper.setSize(width, height);//设置一页纸的大小
        paper.setImageableArea(10, 10, width, height);//设置打印区域的大小

        pageFormat.setPaper(paper);
        pageFormat.setOrientation(orientation);

        Class<?> cls = null;
        Object obj = null;
        try {
            cls = Class.forName(labelDesign.getClassName());
            obj = cls.newInstance();//初始化标签实例
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        LabelPrintable labelPrintable = (LabelPrintable) obj;


        JSONObject labelData = new JSONObject();
        labelPrintable.setLabelData(labelData);

        LabelDataDefinition labelDataDefinition = labelDesign.getLabelDataDefinition();
        JSONObject labelDefinitionData = this.coverteLabelDataDefinitionToJSONObject(labelDataDefinition);
        labelPrintable.setLabelDefinitionData(labelDefinitionData);

        BufferedImage labelBufferedImage = new BufferedImage((int) width, (int)height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = labelBufferedImage.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //画一个填充矩形
        graphics.fillRect(0, 0, (int) width, (int)height);

        try {
            labelPrintable.print(graphics, pageFormat, 0);
        } catch (PrinterException e) {
            throw new RuntimeException(e);
        }
        byte[] imageBytes = this.imageToBytes(labelBufferedImage);

        return imageBytes;
    }

    public static byte[] imageToBytes(BufferedImage bImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ignored) {
            return null;
        }
    }

    @Override
    public JSONObject coverteLabelDataDefinitionToJSONObject(LabelDataDefinition labelDataDefinition) {
        JSONObject labelDefinitionData = new JSONObject();
        if(labelDataDefinition == null)
        {
            return labelDefinitionData;
        }
        List<LabelVariable> labelVariables = labelDataDefinition.getLabelVariables();
        if(labelVariables == null || labelVariables.size() ==0)
        {
            return new JSONObject();
        }
        for(LabelVariable labelVariable : labelVariables)
        {
            labelDefinitionData.put(labelVariable.getLabelVariableName(), labelVariable.getDefaultValue());
        }
        return labelDefinitionData;
    }
}
