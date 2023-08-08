package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumTipoPessoa implements IEnum<String> {
    FISICA("F", "Física"),
    JURIDICA("J", "Jurídica")
    ;

    private final String key;
    private final String value;
    EnumTipoPessoa(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumTipoPessoa, String> {
    }
}
