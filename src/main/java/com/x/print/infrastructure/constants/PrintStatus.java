package com.x.print.infrastructure.constants;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PrintStatus implements EnumClass<String> {

    UNPRINTED("unprinted"), PRINTED("Printed");

    private String id;

    PrintStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PrintStatus fromId(String id) {
        for (PrintStatus at : PrintStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}