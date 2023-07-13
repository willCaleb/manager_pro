package com.project.pro.service;

import com.project.pro.model.dto.EnderecoDTO;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.EnderecoRepository;

import java.util.List;

public interface IEnderecoService extends IAbstractService<Endereco, EnderecoDTO, EnderecoRepository> {
    Endereco incluir(Endereco endereco) throws Exception;

    List<Endereco> incluir(List<Endereco> enderecos, Pessoa pessoa);

    void incluirCoordenadas(Endereco endereco);

    List<Endereco> findAllWithoutCoordenate();

}
