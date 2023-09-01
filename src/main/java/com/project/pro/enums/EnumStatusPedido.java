package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumStatusPedido implements IEnum<String>{
    ABERTO("ABERTO", "Aberto"),
    CONFIRMADO("CONFIRMADO", "Confirmado"),
    CANCELADO("CANCELADO", "Cancelado"),
    FINALIZADO("FINALIZADO", "Finalizado")
    ;

    private final String key;
    private final String value;

    EnumStatusPedido(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumStatusPedido, String> {
    }
}
