package com.project.pro.model.dto;

import com.project.pro.model.entity.Produto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends AbstractDTO<Integer, Produto>{

    private Integer id;

    private String nome;

    private BigDecimal preco;

    private Date dataIncusao;

}
