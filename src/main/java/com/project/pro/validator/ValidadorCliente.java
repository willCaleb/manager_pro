package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Cliente;
import com.project.pro.utils.Utils;

public class ValidadorCliente implements IValidador<Cliente>{
    @Override
    public void validarCamposObrigatorios(Cliente cliente) {
        ValidateFields validate = new ValidateFields();

        validate.add(cliente.getPessoa().getEmail(), "E-mail");
        validate.add(cliente.getPessoa().getSenha(), "Senha");
        validate.validate();
    }

    public void validarClienteCadastrado(Cliente cliente) {
        if (Utils.isEmpty(cliente)) {
            throw new CustomException(EnumCustomException.CLIENTE_NAO_CADASTRADO);
        }
    }
}
