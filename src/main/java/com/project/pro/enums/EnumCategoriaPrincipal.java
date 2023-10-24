package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumCategoriaPrincipal implements IEnum<String> {
    CONSTRUCAO("CONSTRUCAO", "Construção"),
    LIMPEZA("LIMPEZA", "Limpeza"),
    TRANSPORTE("TRANSPORTE", "Transporte"),
    ALIMENTACAO("ALIMENTACAO", "Alimentação"),
    SAUDE("SAUDE", "Saúde"),
    BELEZA("BELEZA", "Beleza"),
    MANUTENCAO_AUTOMOTIVA("MANUTENCAO_AUTOMOTIVA", "Manutenção automotiva");


    private final String key;
    private final String value;

    EnumCategoriaPrincipal(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumCategoriaPrincipal, String> {
    }
}
