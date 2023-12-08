package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumDiaSemana implements IEnum<Integer>{
    DOMINGO(0, "Domingo", "Domingo"),
    SEGUNDA(1, "Segunda-feira", "Segunda"),
    TERCA(2, "Terça-feira", "Terça"),
    QUARTA(3, "Quarta-feira", "Quarta"),
    QUINTA(4, "Quinta-feira", "Quinta"),
    SEXTA(5, "Sexta-feira", "Sexta"),
    SABADO(6, "Sábado", "Sábado");

    private final Integer key;
    private final String value;
    private final String description;

    EnumDiaSemana(Integer key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumDiaSemana, Integer> {
    }
}
