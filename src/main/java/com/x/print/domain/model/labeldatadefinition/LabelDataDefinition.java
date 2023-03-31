package com.x.print.domain.model.labeldatadefinition;

import com.x.print.domain.model.NamedEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "LABEL_DATA_DEFINITION")
@Entity
public class LabelDataDefinition extends NamedEntity {

    @InstanceName
    @Column(name = "LABEL_DATA_DEFINITION_NAME")
    @NotNull
    private String labelDataDefinitionName;

    @Column(name = "DESCRIPTION")
    private String description;


    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OrderBy("labelVariableName")
    @OneToMany(mappedBy = "labelDataDefinition", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<LabelVariable> labelVariables;

    public String getLabelDataDefinitionName() {
        return labelDataDefinitionName;
    }

    public void setLabelDataDefinitionName(String labelDataDefinitionName) {
        this.labelDataDefinitionName = labelDataDefinitionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LabelVariable> getLabelVariables() {
        return labelVariables;
    }

    public void setLabelVariables(List<LabelVariable> labelVariables) {
        this.labelVariables = labelVariables;
    }
}