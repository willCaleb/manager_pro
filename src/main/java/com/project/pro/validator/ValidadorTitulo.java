package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.EnumStatusTitulo;
import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.model.entity.Titulo;
import com.project.pro.utils.ListUtils;

public class ValidadorTitulo implements IValidador<Titulo> {


    @Override
    public void validarCamposObrigatorios(Titulo titulo) {
        ValidateFields validate = new ValidateFields();

        validate.add(titulo.getFormaPagamento(), "Forma de pagamento");
        validate.add(titulo.getValor(), "Valor");
        validate.validate();
    }

    @Override
    public void validarInsert(Titulo titulo) {

    }

    public void validarCancelado(Titulo titulo) {
        if (EnumStatusTitulo.CANCELADO.equals(titulo.getStatus())) {
            throw new CustomRuntimeException(EnumCustomException.TITULO_CANCELADO_NAO_PERMITE_ALTERACAO);
        }
    }

    public void validarLiquidado(Titulo titulo) {
        if (EnumStatusTitulo.LIQUIDADO.equals(titulo.getStatus())) {
            throw new CustomRuntimeException(EnumCustomException.TITULO_LIQUIDADO_NAO_PERMITE_ALTERACAO);
        }
    }


    public void validarTemObservacao(Titulo titulo) {
        if (EnumStatusTitulo.PARCIALMENTE_LIQUIDADO.equals(titulo.getStatus()) && ListUtils.isNullOrEmpty(titulo.getObservacoes())) {
            throw new CustomRuntimeException(EnumCustomException.TITULO_PARCIALMENTE_LIQUIDADO_SEM_OBSERVACAO, titulo.getId());
        }
    }
}
