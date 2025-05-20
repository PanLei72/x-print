package com.x.print.ui.screen.poitltemplate;

import com.x.print.domain.model.poitltemplate.PoitlTemplate;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("PoitlTemplate.browse")
@UiDescriptor("poitl-template-browse.xml")
@LookupComponent("poitlTemplatesTable")
public class PoitlTemplateBrowse extends StandardLookup<PoitlTemplate> {
}