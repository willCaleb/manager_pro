package com.project.pro.model.dto;

import com.project.pro.model.entity.Cliente;
import lombok.Data;

@Data
public class ClienteDTO extends AbstractDTO<Integer, Cliente>{

    private Integer id;

    private PessoaDTO pessoa;
}
