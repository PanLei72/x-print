package com.x.print.ui.screen.labeldesign;

import com.x.print.domain.model.labeldesign.ILabelDesignService;
import com.x.print.domain.model.labeldesign.LabelDesign;
import io.jmix.ui.component.Image;
import io.jmix.ui.component.StreamResource;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;

@UiController("Label.view")
@UiDescriptor("label-view.xml")
@EditedEntityContainer("labelDesignDc")
public class LabelView extends StandardEditor<LabelDesign> {
    @Autowired
    private Image labelImage;
    @Autowired
    private ILabelDesignService labelDesignService;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) throws PrinterException {
        LabelDesign labelDesign = getEditedEntity();
        if (StringUtils.isEmpty(labelDesign.getClassName())) {
            return;
        }

        byte[] imageBytes = labelDesignService.createLabelImageSample(labelDesign);

        this.labelImage.setSource(StreamResource.class).setStreamSupplier(() -> new ByteArrayInputStream(imageBytes));

    }



}