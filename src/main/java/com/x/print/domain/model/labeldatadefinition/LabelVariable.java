package com.x.print.domain.model.labeldatadefinition;

import com.x.print.domain.model.NamedEntity;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "LABEL_VARIABLE")
@Entity
public class LabelVariable extends NamedEntity {

    @JoinColumn(name = "LABEL_DATA_DEFINITION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LabelDataDefinition labelDataDefinition;

    @Column(name = "LABEL_VARIABLE_NAME")
    @NotNull
    private String labelVariableName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    public LabelDataDefinition getLabelDataDefinition() {
        return labelDataDefinition;
    }

    public void setLabelDataDefinition(LabelDataDefinition labelDataDefinition) {
        this.labelDataDefinition = labelDataDefinition;
    }

    public String getLabelVariableName() {
        return labelVariableName;
    }

    public void setLabelVariableName(String labelVariableName) {
        this.labelVariableName = labelVariableName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}