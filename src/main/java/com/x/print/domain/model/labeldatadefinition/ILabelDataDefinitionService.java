package com.x.print.domain.model.labeldatadefinition;

import java.util.UUID;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
public interface ILabelDataDefinitionService {

    LabelDataDefinition loadLabelDataDefinitionById(UUID id);

}
