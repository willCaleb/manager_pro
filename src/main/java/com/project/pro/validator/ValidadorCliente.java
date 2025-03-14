package com.project.pro.validator;

import com.project.pro.model.entity.Cliente;

public class ValidadorCliente implements IValidador<Cliente>{
    @Override
    public void validarCamposObrigatorios(Cliente cliente) {
        ValidateFields validate = new ValidateFields();

        validate.add(cliente.getPessoa().getEmail(), "E-mail");
        validate.add(cliente.getPessoa().getSenha(), "Senha");
        validate.validate();
    }

}
