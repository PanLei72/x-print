package com.x.print.infrastructure.constants;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Orientation implements EnumClass<Integer> {

    LANDSCAPE(0), PORTRAIT(1);

    private Integer id;

    Orientation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Orientation fromId(Integer id) {
        for (Orientation at : Orientation.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}