package com.project.pro.model.dto;

import com.project.pro.model.entity.Produto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends AbstractDTO<Integer, Produto>{

    private Integer id;

}
