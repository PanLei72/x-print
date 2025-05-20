package com.x.print.domain.model.poitltemplate;

import com.x.print.domain.model.NamedEntity;
import io.jmix.core.FileRef;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "POITL_TEMPLATE")
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