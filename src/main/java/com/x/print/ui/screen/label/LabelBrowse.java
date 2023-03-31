package com.x.print.ui.screen.label;

import com.x.print.domain.model.label.Label;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Label.browse")
@UiDescriptor("label-browse.xml")
@LookupComponent("labelHistoriesTable")
public class LabelBrowse extends StandardLookup<Label> {
}