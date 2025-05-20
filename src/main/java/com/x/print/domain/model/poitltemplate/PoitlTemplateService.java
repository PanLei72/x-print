package com.x.print.domain.model.poitltemplate;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.application.LabelPrintable;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;
import com.x.print.domain.model.labeldatadefinition.LabelVariable;
import com.x.print.domain.model.labeldesign.LabelDesign;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class PoitlTemplateService implements IPoitlTemplateService {
    @Autowired
    private DataManager dataManager;

    @Override
    public List<PoitlTemplate> loadAllPoitlTemplates() {
        List<PoitlTemplate> poitlTemplateList = dataManager.load(PoitlTemplate.class).all().sort(Sort.by("templateName")).list();

        return poitlTemplateList;
    }

    @Override
    public PoitlTemplate loadPoitlTemplateByTemplateName(String templateName) {
        PoitlTemplate poitlTemplate =  dataManager.load(PoitlTemplate.class)
                .query("select e from PoitlTemplate e where e.templateName=:templateName")
                .parameter("templateName", templateName)
                .optional()
                .orElse(null);
        return poitlTemplate;
    }
}
