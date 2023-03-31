package com.x.print.domain.model.labeldesign;

import com.x.print.domain.model.NamedEntity;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;
import com.x.print.infrastructure.constants.Orientation;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "LABEL_DESIGN")
@Entity
public class LabelDesign extends NamedEntity {


    @Column(name = "LABEL_NAME")
    @NotNull
    private String labelName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "HEIGHT")
    @NotNull
    private Double height;

    @Column(name = "WIDTH")
    @NotNull
    private Double width;

    @Column(name = "CLASS_NAME")
    @NotNull
    private String className;

    @Column(name = "ORIENTATION")
    @NotNull
    private Integer orientation;

    @JoinColumn(name = "LABEL_DATA_DEFINITION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private LabelDataDefinition labelDataDefinition;

    public LabelDataDefinition getLabelDataDefinition() {
        return labelDataDefinition;
    }

    public void setLabelDataDefinition(LabelDataDefinition labelDataDefinition) {
        this.labelDataDefinition = labelDataDefinition;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Orientation getOrientation() {
        return orientation != null ? Orientation.fromId(orientation) : null;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation == null ? null : orientation.getId();
    }

    @InstanceName
    @DependsOnProperties({"labelName", "description"})
    public String getDisplayName() {
        return String.format("%s %s", (labelName != null ? labelName : ""),
                (description != null ? description : ""));
    }
}