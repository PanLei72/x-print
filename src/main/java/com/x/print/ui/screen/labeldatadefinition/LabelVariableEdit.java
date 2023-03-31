package com.x.print.ui.screen.labeldatadefinition;

import io.jmix.ui.screen.*;
import com.x.print.domain.model.labeldatadefinition.LabelVariable;

@UiController("LabelVariable.edit")
@UiDescriptor("label-variable-edit.xml")
@EditedEntityContainer("labelVariableDc")
public class LabelVariableEdit extends StandardEditor<LabelVariable> {
}