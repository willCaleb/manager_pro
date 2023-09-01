package com.project.pro.validator;

import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Pedido;
import com.project.pro.utils.StringUtil;

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

    public void validarFinalizadoOuCancelado(Pedido pedido) {
        if (EnumStatusPedido.FINALIZADO.equals(pedido)) {
            throw new CustomException("O pedido já foi finalizado e não pode ser alterado.");
        }
        if (EnumStatusPedido.CANCELADO.equals(pedido)) {
            throw new CustomException("O pedido foi cancelado e não pode ser alterado.");
        }
    }

    public void validarTemObservacao(Pedido pedido) {
        if (StringUtil.isNullOrEmpty(pedido.getObservacao())) {
            throw new CustomException("Observação é obrigatória ao editar um pedido.");
        }
    }
}
