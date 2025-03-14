package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.exception.CustomRuntimeException;
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

    public void validarTemObservacao(Pedido pedido) {
        if (StringUtil.isNullOrEmpty(pedido.getObservacao())) {
            throw new CustomRuntimeException("Observação é obrigatória ao editar um pedido.");
        }
    }

    public void validarPedidoCancelado(Pedido pedidoManaged) {
        if (EnumStatusPedido.CANCELADO.equals(pedidoManaged.getStatusPedido()) || EnumStatusPedido.FINALIZADO.equals(pedidoManaged.getStatusPedido())) {
            throw new CustomRuntimeException(EnumCustomException.PEDIDO_CANCELADO_OU_FINALIZADO_SEM_PERMISSAO_ALTERAR);
        }
    }
}
