package com.project.pro.service;

import com.project.pro.model.entity.Endereco;

public interface ICepService {

    Endereco buscarEnderecoPorCep(String cep) throws Exception;

}
