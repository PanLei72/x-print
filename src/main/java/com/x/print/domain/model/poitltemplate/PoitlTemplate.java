package com.x.print.domain.model.poitltemplate;

import com.x.print.domain.model.NamedEntity;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;
import io.jmix.core.FileRef;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "POITL_TEMPLATE", indexes = {
        @Index(name = "IDX_POITL_TEMPLATE_LABEL_DATA_DEFINITION", columnList = "LABEL_DATA_DEFINITION_ID")
})
@Entity
public class PoitlTemplate extends NamedEntity {


    @Column(name = "TEMPLATE_NAME", nullable = false)
    @NotNull
    private String templateName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "TEMPLATE_FILE", length = 1024)
    private FileRef templateFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LABEL_DATA_DEFINITION_ID")
    private LabelDataDefinition labelDataDefinition;

    public LabelDataDefinition getLabelDataDefinition() {
        return labelDataDefinition;
    }

    public void setLabelDataDefinition(LabelDataDefinition labelDataDefinition) {
        this.labelDataDefinition = labelDataDefinition;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public FileRef getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(FileRef templateFile) {
        this.templateFile = templateFile;
    }

    @InstanceName
    @DependsOnProperties({"templateName", "description"})
    public String getDisplayName() {
        return String.format("%s %s", (templateName != null ? templateName : ""),
                (description != null ? description : ""));
    }
}