package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumClassificacaoPessoa implements IEnum<Integer>{
    RUIM(1, "Ruim"),
    REGULAR(2, "Regular"),
    MEDIO(3, "Médio"),
    BOM(4, "Bom"),
    OTIMO(5, "Ótimo");

    private final Integer key;
    private final String value;

    EnumClassificacaoPessoa(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumClassificacaoPessoa, Integer> {
    }
}
