package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumStatusTitulo implements IEnum<String> {

    ABERTO("ABERTO", "Aberto"),
    PARCIALMENTE_LIQUIDADO("PARCIALMENTE_LIQUIDADO", "Parcialmente liquidado"),
    LIQUIDADO("LIQUIDADO", "Liquidado"),
    CANCELADO("CANCELADO", "Cancelado");

    private final String key;
    private final String value;

    EnumStatusTitulo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumStatusTitulo, String> {
    }
}
