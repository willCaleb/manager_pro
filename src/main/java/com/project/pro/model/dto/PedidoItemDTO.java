package com.project.pro.model.dto;

import com.project.pro.model.entity.PedidoItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoItemDTO extends AbstractDTO<Integer, PedidoItem> {

    private Integer id;

    private BigDecimal valor;

    private BigDecimal acrescimo;

    private BigDecimal desconto;

    private Integer quantidade;

    private Integer ordem;
}
