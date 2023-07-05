package com.x.print.ui.screen.labeldesign;

import com.x.print.domain.model.labeldesign.ILabelDesignService;
import com.x.print.domain.model.labeldesign.LabelDesign;
import io.jmix.core.LoadContext;
import io.jmix.core.MetadataTools;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.List;

@UiController("LabelDesign.browse")
@UiDescriptor("label-design-browse.xml")
@LookupComponent("labelsTable")
public class LabelBrowse extends StandardLookup<LabelDesign> {
    @Autowired
    private ILabelDesignService labelDesignService;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FlowBoxLayout flowBox;
    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private DataGrid<LabelDesign> labelsTable;
    @Autowired
    private ScrollBoxLayout scrollBox;
    @Autowired
    private CollectionLoader<LabelDesign> labelsDl;
    @Autowired
    private Button tableBtn;
    @Autowired
    private Button viewImageBtn;

    @Install(to = "labelsDl", target = Target.DATA_LOADER)
    private List<LabelDesign> labelsDlLoadDelegate(LoadContext<LabelDesign> loadContext) {
       return labelDesignService.loadAllLabelDesign();
    }

    @Subscribe("viewImageBtn")
    public void onViewImageBtnClick(Button.ClickEvent event) {
        scrollBox.setVisible(true);
        labelsTable.setVisible(false);

        tableBtn.setEnabled(true);
        viewImageBtn.setEnabled(false);

        List<LabelDesign> labelDesignList = labelDesignService.loadAllLabelDesign();
        if(labelDesignList == null || labelDesignList.size() == 0)
        {
            return;
        }
        flowBox.removeAll();
        for(LabelDesign labelDesign : labelDesignList)
        {
            GroupBoxLayout groupBoxLayout = uiComponents.create(GroupBoxLayout.class);
            groupBoxLayout.setWidth(labelDesign.getWidth()+50+"");
            groupBoxLayout.setHeight(labelDesign.getHeight()+80+"");

            String displayName = metadataTools.getInstanceName(labelDesign);
            groupBoxLayout.setCaption(displayName);

            Image image = uiComponents.create(Image.class);

            if (StringUtils.isEmpty(labelDesign.getClassName())) {
                return;
            }

            byte[] imageBytes = labelDesignService.createLabelImageSample(labelDesign);

            image.setSource(StreamResource.class).setStreamSupplier(() -> new ByteArrayInputStream(imageBytes));

            groupBoxLayout.add(image);

            flowBox.add(groupBoxLayout);
        }
    }

    @Subscribe("tableBtn")
    public void onTableBtnClick(Button.ClickEvent event) {
        scrollBox.setVisible(false);
        labelsTable.setVisible(true);

        tableBtn.setEnabled(false);
        viewImageBtn.setEnabled(true);
        labelsDl.load();
    }
}