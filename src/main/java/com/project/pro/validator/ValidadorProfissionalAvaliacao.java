package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.model.entity.ProfissionalAvaliacao;
import com.project.pro.repository.ProfissionalAvaliacaoRepository;
import com.project.pro.utils.Utils;

public class ValidadorProfissionalAvaliacao implements IValidador<ProfissionalAvaliacao>{

    private ProfissionalAvaliacaoRepository profissionalAvaliacaoRepository;

    public void setProfissionalAvaliacaoRepository(ProfissionalAvaliacaoRepository profissionalAvaliacaoRepository) {
        this.profissionalAvaliacaoRepository = profissionalAvaliacaoRepository;
    }

    @Override
    public void validarCamposObrigatorios(ProfissionalAvaliacao avaliacao) {
        ValidateFields validate = new ValidateFields();

        validate.add(avaliacao.getNota(), "Avaliação");
        validate.add(avaliacao.getCliente(), "Cliente");
        validate.validate();
    }

    @Override
    public void validarInsert(ProfissionalAvaliacao avaliacao) {
        validarCamposObrigatorios(avaliacao);
        validarJaAvaliou(avaliacao);
    }

    private void validarJaAvaliou(ProfissionalAvaliacao avaliacao) {
        ProfissionalAvaliacao byProfissionalAndCliente = profissionalAvaliacaoRepository.findByProfissionalAndCliente(
                avaliacao.getProfissional(),
                avaliacao.getCliente()
        );

        if (Utils.isNotEmpty(byProfissionalAndCliente)) {
            throw new CustomRuntimeException(EnumCustomException.AVALIACAO_PROFISSIONAL_JA_AVALIADO);
        }

    }
}
