package com.project.pro.model.dto;

import com.project.pro.model.entity.Profissional;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProfissionalDTO extends AbstractDTO<Integer, Profissional>{

    private Integer id;

//    @JsonIgnore
    private PessoaDTO pessoa;

    private BigDecimal avaliacao;

    private Integer qndtAvaliacao;

    private BigDecimal mediaAvaliacao;

    List<CategoriaDTO> categorias;
}
