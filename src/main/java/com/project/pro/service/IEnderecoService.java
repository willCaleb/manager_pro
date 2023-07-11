package com.project.pro.service;

import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;

import java.util.List;

public interface IEnderecoService {
    Endereco incluir(Endereco endereco) throws Exception;

    List<Endereco> incluir(List<Endereco> enderecos, Pessoa pessoa);
}
