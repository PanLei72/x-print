package com.x.print.ui.screen.labeldatadefinition;

import io.jmix.ui.screen.*;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;

@UiController("LabelDataDefinition.edit")
@UiDescriptor("label-data-definition-edit.xml")
@EditedEntityContainer("labelDataDefinitionDc")
public class LabelDataDefinitionEdit extends StandardEditor<LabelDataDefinition> {
}