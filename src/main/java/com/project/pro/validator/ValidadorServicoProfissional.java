package com.project.pro.validator;

import com.project.pro.model.entity.ServicoProfissional;

public class ValidadorServicoProfissional implements IValidador<ServicoProfissional> {
    @Override
    public void validarCamposObrigatorios(ServicoProfissional servicoProfissional) {
        ValidateFields validate = new ValidateFields();
        validate.add(servicoProfissional.getProfissional(), "Profissional");
        validate.add(servicoProfissional.getServico(), "Serviço");
    }

    @Override
    public void validarInsert(ServicoProfissional servicoProfissional) {
        validarCamposObrigatorios(servicoProfissional);
    }
}
