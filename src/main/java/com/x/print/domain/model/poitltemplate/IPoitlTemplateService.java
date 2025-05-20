package com.x.print.domain.model.poitltemplate;

import com.x.print.domain.model.labeldesign.LabelDesign;

import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2025-05-20
 */
public interface IPoitlTemplateService {

    List<PoitlTemplate> loadAllPoitlTemplates();

    PoitlTemplate loadPoitlTemplateByTemplateName(String templateName);

}
