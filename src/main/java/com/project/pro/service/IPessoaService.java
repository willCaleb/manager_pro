package com.project.pro.service;

import com.project.pro.model.dto.PessoaDTO;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.PessoaRepository;

import java.util.List;

public interface IPessoaService extends IAbstractService<Pessoa, PessoaDTO, PessoaRepository>{

    Pessoa incluir(Pessoa pessoa);

    List<Pessoa> findAll();
}
