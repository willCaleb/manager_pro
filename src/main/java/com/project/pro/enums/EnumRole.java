package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumRole implements IEnum<String>{

    PROFESSIONAL("PROFISSIONAL", "Profissional"),
    USER("CLIENTE", "Cliente"),
    ADMIN("ADMIN", "Admin"),
    ANNONYMOUS("ANNONYMOUS", "Anônimo")
    ;

    private final String key;
    private final String value;

    EnumRole(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumRole, String> {
    }
}
