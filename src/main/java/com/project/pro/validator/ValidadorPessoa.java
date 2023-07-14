package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.PessoaRepository;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import lombok.Setter;

import java.util.List;

@Setter
public class ValidadorPessoa implements IValidador<Pessoa>{

    private PessoaRepository pessoaRepository;

    @Override
    public void validarCamposObrigatorios(Pessoa pessoa) {
        ValidateFields validateFields = new ValidateFields();

        validateFields.add(pessoa.getNome(), "Nome");
        validateFields.add(pessoa.getIdade(), "Idade");
        validateFields.add(pessoa.getCpfCnpj(), "CPF");

        validateFields.validate();
    }

    @Override
    public void validarTamanhoCampo(Pessoa pessoa) {
        ValidateFieldSize validateFieldSize = new ValidateFieldSize();
        validateFieldSize.validarTamanhoMaximo("Nome", 50);
        validateFieldSize.validarTamanhoMinimo(pessoa.getNome(), 6);
    }

    public void validarCpf(Pessoa pessoa) {
        boolean isValidCpf = ValidadorCpf.isValidCpf(pessoa.getCpfCnpj());
        if (!isValidCpf) {
            throw new CustomException(EnumCustomException.CPF_INVALIDO, pessoa.getCpfCnpj());
        }

        Pessoa pessoaCad = pessoaRepository.findByCpfCnpj(pessoa.getCpfCnpj());
        if (Utils.isNotEmpty(pessoaCad)) {
            throw new CustomException("Pessoa já cadastrada com o CPF ", pessoa.getCpfCnpj());
        }
    }

    private void validarCpfDuplicado(Pessoa pessoa) {
        List<Pessoa> pessoasDuplicadas = pessoaRepository.findAllByCpfCnpj(pessoa.getCpfCnpj());

        if (ListUtils.isNotNullOrEmpty(pessoasDuplicadas)) {
            throw new CustomException("CPF {0} já cadastrado.", pessoa.getCpfCnpj());
        }
    }

    @Override
    public void validarInsert(Pessoa pessoa) {
//        validarCamposObrigatorios(pessoa);
//        validarTamanhoCampo(pessoa);
        validarCpfDuplicado(pessoa);
//        validarCpf(pessoa);
    }
}
