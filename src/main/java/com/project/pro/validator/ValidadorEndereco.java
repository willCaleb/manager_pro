package com.project.pro.validator;

import com.project.pro.model.entity.Endereco;

public class ValidadorEndereco implements IValidador<Endereco>{
    @Override
    public void validarCamposObrigatorios(Endereco endereco) {
        final ValidateFields validateFields = new ValidateFields();

        validateFields.add(endereco.getCep(), "CEP");
        validateFields.add(endereco.getNumero(), "Número");
        validateFields.validate();
    }

    @Override
    public void validarInsert(Endereco endereco) {
        validarCamposObrigatorios(endereco);
    }
}
