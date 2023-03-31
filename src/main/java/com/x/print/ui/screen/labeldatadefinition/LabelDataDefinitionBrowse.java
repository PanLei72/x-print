package com.x.print.ui.screen.labeldatadefinition;

import io.jmix.ui.screen.*;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;

@UiController("LabelDataDefinition.browse")
@UiDescriptor("label-data-definition-browse.xml")
@LookupComponent("labelDataDefinitionsTable")
public class LabelDataDefinitionBrowse extends StandardLookup<LabelDataDefinition> {
}