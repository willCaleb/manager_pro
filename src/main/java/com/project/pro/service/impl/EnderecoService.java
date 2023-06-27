package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Endereco;
import com.project.pro.repository.EnderecoRepository;
import com.project.pro.service.IEnderecoService;
import com.project.pro.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService implements IEnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Endereco incluir(Endereco endereco) throws Exception{

        if (Utils.isEmpty(endereco.getCep())) {
            throw new CustomException("CEP não pode estar vazio");
        }

        Endereco endByCep = CepService.buscaEnderecoPeloCep(endereco.getCep());

        endByCep.setNumero(endereco.getNumero());
        endByCep.setComplemento(endereco.getComplemento());
        return endByCep;
    }
}
