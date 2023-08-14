package com.project.pro.validator;

import com.project.pro.model.entity.Pedido;

public class ValidadorPedido implements IValidador<Pedido>{
    @Override
    public void validarCamposObrigatorios(Pedido pedido) {
        ValidateFields validateFields = new ValidateFields();
        validateFields.add(pedido.getCliente(), "Cliente");
        validateFields.add(pedido.getItens(), "Itens");

        validateFields.validate();
    }

    @Override
    public void validarInsert(Pedido pedido) {
        validarCamposObrigatorios(pedido);
    }
}
