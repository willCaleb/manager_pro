package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumConfig implements IEnum<String>{

    PERMITE_EDITAR_AGENDA_CANCELADA("PERMITE_EDITAR_AGENDA_CANCELADA", Boolean.class),
    PERMITE_EDITAR_AGENDA_FINALIZADA("PERMITE_EDITAR_AGENDA_FINALIZADA", Boolean.class)
    ;

    private final String key;
    private final Class<?> clazz;

    EnumConfig(String key, Class<?> clazz) {
        this.key = key;
        this.clazz = clazz;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return clazz.getSimpleName();
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumConfig, String> {

    }
}
