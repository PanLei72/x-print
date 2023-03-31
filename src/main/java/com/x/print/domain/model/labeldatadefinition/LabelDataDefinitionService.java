package com.x.print.domain.model.labeldatadefinition;

import com.x.print.domain.model.label.ILabelService;
import com.x.print.domain.model.label.Label;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class LabelDataDefinitionService implements ILabelDataDefinitionService {
    @Autowired
    private DataManager dataManager;


    @Override
    public LabelDataDefinition loadLabelDataDefinitionById(UUID id) {
        if (id == null) {
            return null;
        }
        LabelDataDefinition labelDataDefinition = dataManager.load(LabelDataDefinition.class).id(id).optional().orElse(null);
        return labelDataDefinition;
    }
}
