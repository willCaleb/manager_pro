package com.project.pro.model.dto;

import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.model.entity.PedidoItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoItemDTO extends AbstractDTO<Integer, PedidoItem> {

    private Integer id;

    private BigDecimal valor;

    private BigDecimal acrescimo;

    private BigDecimal desconto;

    private Integer quantidade;

    private EnumStatusPedido statusPedido;

    private ServicoProfissionalDTO servicoProfissional;

    private Integer ordem;
}
