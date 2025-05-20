package com.x.print.ui.screen.poitltemplate;

import com.x.print.domain.model.poitltemplate.PoitlTemplate;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("PoitlTemplate.edit")
@UiDescriptor("poitl-template-edit.xml")
@EditedEntityContainer("poitlTemplateDc")
public class PoitlTemplateEdit extends StandardEditor<PoitlTemplate> {
}