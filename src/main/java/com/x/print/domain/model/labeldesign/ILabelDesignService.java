package com.x.print.domain.model.labeldesign;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;

import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
public interface ILabelDesignService {

    List<LabelDesign> loadAllLabelDesign();

    LabelDesign loadLabelDesignByLabelName(String labelName);

    byte[] createLabelImageSample(LabelDesign labelDesign);

    JSONObject coverteLabelDataDefinitionToJSONObject(LabelDataDefinition labelDataDefinition);
}
