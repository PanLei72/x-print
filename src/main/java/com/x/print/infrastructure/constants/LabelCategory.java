package com.x.print.infrastructure.constants;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum LabelCategory implements EnumClass<String> {

    LABEL("Label"),
    POITL("Poitl");

    private final String id;

    LabelCategory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static LabelCategory fromId(String id) {
        for (LabelCategory at : LabelCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}