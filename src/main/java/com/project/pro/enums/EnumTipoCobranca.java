package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumTipoCobranca implements IEnum<String> {
    TEMPO("TEMPO", "Tempo"),
    EMPREITA("EMPREITA", "Empreita");

    private final String key;
    private final String value;

    EnumTipoCobranca(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumTipoCobranca, String> {
    }
}
