package com.project.pro.validator;

import com.project.pro.model.entity.Endereco;
import com.project.pro.utils.StringUtil;

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
        validarCep(endereco);
        validarCamposObrigatorios(endereco);
    }

    private void validarCep(Endereco endereco) {

        ValidateFieldSize validateFieldSize = new ValidateFieldSize();
        validateFieldSize.validarTamanhoIgual(StringUtil.normalize(endereco.getCep()), 8);
    }
}
