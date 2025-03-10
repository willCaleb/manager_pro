package com.project.pro.enums;

import javax.persistence.Converter;

public enum Role implements IEnum<String>{

    PROFISSIONAL("PROFISSIONAL", "Profissional"),
    CLIENTE("CLIENTE", "Cliente"),
    ADMIN("ADMIN", "Admin")
    ;

    private final String key;
    private final String value;

    Role(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<Role, String> {
    }
}
