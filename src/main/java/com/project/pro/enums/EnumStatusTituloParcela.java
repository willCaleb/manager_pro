package com.project.pro.enums;

public enum EnumStatusTituloParcela implements IEnum<String>{

    ABERTO("ABERTO", "Aberto"),
    PAGO("PAGO", "Pago"),
    PAGO_PARCIALMENT("PAGO_PARCIALMENTE", "Pago parcialment"),
    ATRASADO("ATRASADO", "Atrasado")

    ;

    EnumStatusTituloParcela(String key, String value) {

    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
