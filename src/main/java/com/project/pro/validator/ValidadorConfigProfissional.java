package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.ConfigProfissional;
import com.project.pro.repository.ConfigProfissionalRepository;

import java.util.Optional;

public class ValidadorConfigProfissional implements IValidador<ConfigProfissional> {

    private ConfigProfissionalRepository configProfissionalRepository;

    public void setConfigProfissionalRepository(ConfigProfissionalRepository configProfissionalRepository ) {
        this.configProfissionalRepository = configProfissionalRepository;
    }

    @Override
    public void validarCamposObrigatorios(ConfigProfissional configProfissional) {

        ValidateFields validate = new ValidateFields();
        validate.add(configProfissional.getValor(), "Valor");
        validate.add(configProfissional.getConfig(), "Config");

        validate.validate();

    }

    @Override
    public void validarInsert(ConfigProfissional configProfissional) {
        validarCamposObrigatorios(configProfissional);
        validarConfigJaCadastrada(configProfissional);
    }

    void validarConfigJaCadastrada(ConfigProfissional configProfissional) {
        Optional<ConfigProfissional> optionalConfigProfissional = configProfissionalRepository
                .findByConfigAndProfissional(configProfissional.getConfig(), configProfissional.getProfissional());

        if(optionalConfigProfissional.isPresent()) {
            throw new CustomException(EnumCustomException.CONFIGURACAO_JA_CADASTRADA_PARA_PROFISSIONAL);
        }
    }
}
