package com.project.pro.validator;

import com.project.pro.model.entity.PedidoItem;

public class ValidadorPedidoItem implements IValidador<PedidoItem>{

    @Override
    public void validarCamposObrigatorios(PedidoItem pedidoItem) {
        ValidateFields validate = new ValidateFields();
        validate.add(pedidoItem.getValor(), "Valor");
        validate.add(pedidoItem.getQuantidade(), "Quantidade");
        validate.add(pedidoItem.getServicoProfissional(), "Pedido Profissional");
        validate.validate();
    }
}
