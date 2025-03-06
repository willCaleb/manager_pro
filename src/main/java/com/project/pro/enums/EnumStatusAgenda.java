package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumStatusAgenda implements IEnum<String>{

    AGUARDANDO_CONFIRMACAO("AGUARDANDO_CONFIRMACAO", "Aguardando confirmação"),
    CONFIRMADO("CONFIRMADO", "Confirmado"),
    CANCELADO("CANCELADO", "Cancelado"),
    FINALIZADO("FINALIZADO", "Finalizado")
    ;

    private final String key;
    private final String value;

    EnumStatusAgenda(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumFormaPagamento, String> {
    }

    public boolean permiteEditar() {
        return (this.equals(AGUARDANDO_CONFIRMACAO) || this.equals(FINALIZADO));
    }
}
