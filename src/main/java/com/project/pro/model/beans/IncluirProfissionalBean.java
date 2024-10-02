package com.project.pro.model.beans;

import com.project.pro.annotation.DtoFieldIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class IncluirProfissionalBean {

    private Integer id;

    private String nome;

    private String cpfCnpj;

    private String telefone;

    private String email;

    private String senha;

    private Integer idade;

    private Date dataNascimento;
}
