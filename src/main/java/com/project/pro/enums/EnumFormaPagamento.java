package com.project.pro.enums;

import javax.persistence.Converter;

public enum EnumFormaPagamento implements IEnum<String>{

    DINHEIRO("DINHEIRO", "Dinheiro"),
    CARTAO_DEBITO("CARTAO_DEBITO", "Cartão de débito"),
    CARTAO_CREDITO("CARTAO_CREDITO", "Cartão de crédito"),
    PIX("PIX", "Pix"),
    BOLETO("BOLETO", "Boleto")
    ;

    private final String key;
    private final String value;

    EnumFormaPagamento(String key, String value) {
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
}
