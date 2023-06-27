package com.project.pro.validator;

import com.project.pro.model.entity.Servico;

public class ValidadorServico implements IValidador<Servico> {

    @Override
    public void validarCamposObrigatorios(Servico servico) {
        ValidateFields validate = new ValidateFields();
        validate.add(servico.getCategoria(), "Categoria");
        validate.add(servico.getDescricao(), "Descrição");
        validate.add(servico.getNome(), "Nome");
        validate.validate();
    }

    @Override
    public void validarInsert(Servico servico) {
        validarCamposObrigatorios(servico);
    }
}
