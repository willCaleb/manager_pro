package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumEtapaServico implements IEnum<String> {
    CONFIRMADO("CONFIRMADO", "Confirmado"),
    AGUARDANDO_CONFIRMACAO("AGUARDANDO_CONFIRMACAO", "Aguardando confirmação"),
    EM_EXECUCAO("EM_EXECUCAO", "Em execução"),
    EXECUTADO("EXECUTADO", "Executado"),
    CANCELADO("CANCELADO", "Cancelado");

    private final String key;
    private final String value;

    EnumEtapaServico(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumEtapaServico, String> {
    }
}
