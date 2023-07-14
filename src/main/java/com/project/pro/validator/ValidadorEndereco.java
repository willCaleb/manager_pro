package com.project.pro.validator;

import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Endereco;
import com.project.pro.repository.EnderecoRepository;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.NumericUtils;
import com.project.pro.utils.StringUtil;
import com.project.pro.utils.Utils;

import java.util.List;

public class ValidadorEndereco implements IValidador<Endereco>{

    private EnderecoRepository enderecoRepository;

    public void setEnderecoRepository(EnderecoRepository repository) {
        this.enderecoRepository = repository;
    }

    @Override
    public void validarCamposObrigatorios(Endereco endereco) {
        final ValidateFields validateFields = new ValidateFields();

        validateFields.add(endereco.getCep(), "CEP");
        validateFields.add(endereco.getNumero(), "Número");
        validateFields.add(endereco.getPessoa(), "Pessoa");
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

    public void validarEnderecoJaPertenceAPessoa(Endereco endereco) {
        String logradouro = endereco.getLogradouro();
        Integer numero = endereco.getNumero();
        List<Endereco> enderecos = enderecoRepository.findAllByPessoaAndLogradouroAndNumero(endereco.getPessoa(), logradouro, numero);
        if (ListUtils.isNotNullOrEmpty(enderecos)) {
            throw new CustomException("Esse endereço já está cadastrado para ", endereco.getPessoa().getNome());
        }
    }

    public void validarUnicoPrincipal(List<Endereco> enderecos) {
        if (NumericUtils.isGreater(enderecos.size(), 1)) {
            throw new CustomException("Apenas um endereço deve ser marcado como principal.");
        }
    }
}
