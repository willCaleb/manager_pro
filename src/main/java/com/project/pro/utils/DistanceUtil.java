package com.project.pro.utils;

import com.project.pro.model.entity.Endereco;

import java.math.BigDecimal;

public class DistanceUtil {

    public static BigDecimal calcularDistanciaKm(Endereco enderecoA, Endereco enderecoB) {
        if (Utils.isEmpty(enderecoA.getLatitude()) || Utils.isEmpty(enderecoA.getLongitude())) {

        }
        return BigDecimal.ZERO;
    }

}
