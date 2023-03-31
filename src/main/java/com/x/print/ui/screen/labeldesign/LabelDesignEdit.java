package com.x.print.ui.screen.labeldesign;

import com.x.print.application.labeltemplate.*;
import com.x.print.domain.model.labeldesign.ILabelDesignService;
import com.x.print.domain.model.labeldesign.LabelDesign;
import com.x.print.infrastructure.constants.Orientation;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.StreamResource;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UiController("LabelDesign.edit")
@UiDescriptor("label-design-edit.xml")
@EditedEntityContainer("labelDesignDc")
public class LabelDesignEdit extends StandardEditor<LabelDesign> {
    @Autowired
    private Image labelImage;
    @Autowired
    private ComboBox<String> classNameField;
    @Autowired
    private ILabelDesignService labelDesignService;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> labelTampleList = new ArrayList<>();

        labelTampleList.add(LabelTemplate1.class.getName());
        labelTampleList.add(LabelTemplate2.class.getName());
        labelTampleList.add(LabelTemplate3.class.getName());
        labelTampleList.add(LabelTemplate4.class.getName());
        labelTampleList.add(LabelTemplate5.class.getName());
        labelTampleList.add(LabelTemplate6.class.getName());
        labelTampleList.add(LabelTemplate7.class.getName());
        labelTampleList.add(LabelTemplate8.class.getName());
        labelTampleList.add(LabelTemplate9.class.getName());
        labelTampleList.add(LabelTemplate10.class.getName());
        labelTampleList.add(LabelTemplate11.class.getName());
        labelTampleList.add(LabelTemplate12.class.getName());
        labelTampleList.add(LabelTemplate13.class.getName());
        labelTampleList.add(LabelTemplate14.class.getName());
        labelTampleList.add(LabelTemplate15.class.getName());
        labelTampleList.add(LabelTemplate16.class.getName());

        classNameField.setOptionsList(labelTampleList);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        LabelDesign labelDesign = getEditedEntity();
        if (StringUtils.isEmpty(labelDesign.getClassName())) {
            labelDesign.setWidth(284D);
            labelDesign.setHeight(170D);
            labelDesign.setOrientation(Orientation.PORTRAIT);
            return;
        }

        byte[] imageBytes = labelDesignService.createLabelImageSample(labelDesign);

        this.labelImage.setSource(StreamResource.class).setStreamSupplier(() -> new ByteArrayInputStream(imageBytes));

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

}