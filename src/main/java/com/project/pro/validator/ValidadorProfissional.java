package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ProfissionalRepository;
import com.project.pro.service.IPessoaService;
import com.project.pro.service.impl.PessoaService;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;

import java.util.List;

public class ValidadorProfissional implements IValidador<Profissional> {

    private ProfissionalRepository profissionalRepository;

    private IPessoaService pessoaService;

    public void setPessoaService(IPessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }


    public void setRepository(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public void validarInsert(Profissional profissional) {
        validarCamposObrigatorios(profissional);
        validarCpfJaCadastrado(profissional);
        validarPessoaJaCadastradaEmProfissional(profissional);
    }

    public void validarCpfJaCadastrado(Profissional profissional) {
        validarCpf(profissional);
        Profissional byCpf = profissionalRepository.findByCpf(profissional.getCpf());
        if (Utils.isNotEmpty(byCpf)) {
            throw new CustomException(EnumCustomException.PROFISSIONAL_CPF_JA_CADASTRADO, profissional.getCpf());
        }
    }

    @Override
    public void validarCamposObrigatorios(Profissional profissional) {
        ValidateFields validate = new ValidateFields();
//        validate.add(profissional.getPessoa(), "Pessoa");
        validate.add(profissional.getCpf(), "CPF");
        validate.add(profissional.getEmail(), "E-mail");
        validate.validate();
    }

    private void validarPessoaJaCadastradaEmProfissional(Profissional profissional) {
        if (Utils.isNotEmpty(profissional.getPessoa()) && profissional.getPessoa().hasId()) {
            Pessoa pessoa = pessoaService.findAndValidate(profissional.getPessoa().getId());
            List<Profissional> validateList = profissionalRepository.findAllByPessoa(pessoa);

            if (ListUtils.isNotNullOrEmpty(validateList)) {
                throw new CustomException(EnumCustomException.PROFISSIONAL_JA_CADASTRADO_PESSOA, pessoa.getNome());
            }
        }
    }

    public void validarCpf(Profissional profissional) {
        if (!ValidadorCpf.isValidCpf(profissional.getCpf())) {
            throw new CustomException(EnumCustomException.CPF_INVALIDO);
        }
    }
}
