package com.project.pro.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pro.annotation.OnlyField;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import lombok.Data;

@Data
public class EnderecoDTO extends AbstractDTO<Integer, Endereco>{

    private Integer id;

    private String logradouro;

    private String cep;

    private Integer numero;

    private String complemento;

    private String bairro;

    private String localidade;

    @OnlyField(fields = {"id"})
    private PessoaDTO pessoa;
}
