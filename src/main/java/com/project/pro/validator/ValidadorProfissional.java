package com.project.pro.validator;

import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ProfissionalRepository;
import com.project.pro.service.IPessoaService;
import com.project.pro.service.impl.PessoaService;
import com.project.pro.utils.ListUtils;

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
        validarPessoaJaCadastradaEmProfissional(profissional);
    }

    @Override
    public void validarCamposObrigatorios(Profissional profissional) {
        ValidateFields validate = new ValidateFields();
        validate.add(profissional.getPessoa(), "Pessoa");
    }

    private void validarPessoaJaCadastradaEmProfissional(Profissional profissional) {
        if (profissional.getPessoa().hasId()) {
            Pessoa pessoa = pessoaService.findAndValidate(profissional.getPessoa().getId());
            List<Profissional> validateList = profissionalRepository.findAllByPessoa(pessoa);

            if (ListUtils.isNotNullOrEmpty(validateList)) {
                throw new CustomException("Já existe um profissional cadastrado para a pessoa " + pessoa.getNome());
            }
        }
    }

}
