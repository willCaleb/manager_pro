package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumStatusTituloParcela implements IEnum<String>{

    ABERTO("ABERTO", "Aberto"),
    PAGO("PAGO", "Pago"),
    PAGO_PARCIALMENTE("PAGO_PARCIALMENTE", "Pago parcialmente"),
    ATRASADO("ATRASADO", "Atrasado")

    ;

    private final String key;
    private final String value;

    EnumStatusTituloParcela(String key, String value) {
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
    public static class EnumConverter extends AbstractEnumConverter<EnumStatusTituloParcela, String> {
    }
}
