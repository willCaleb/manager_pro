package com.project.pro.validator;

import com.project.pro.model.entity.Observacao;

public class ValidadorObservacao implements IValidador<Observacao>{

    @Override
    public void validarCamposObrigatorios(Observacao observacao) {
        ValidateFields validate = new ValidateFields();

        validate.add(observacao.getEntity(), "Entity");
        validate.add(observacao.getEntityId(), "Entity id");

        validate.validate();
    }
}
