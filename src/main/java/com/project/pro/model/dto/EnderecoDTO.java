package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.model.entity.Endereco;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnderecoDTO extends AbstractDTO<Integer, Endereco>{

    private Integer id;

    private String logradouro;

    private String cep;

    private Integer numero;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private Double latitude;

    private Double longitude;

    private Boolean principal;

    @OnlyField(fields = {"id"})
    private PessoaDTO pessoa;
}
