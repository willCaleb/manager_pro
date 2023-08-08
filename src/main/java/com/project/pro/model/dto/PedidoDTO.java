package com.project.pro.model.dto;

import com.project.pro.enums.EnumTipoCobranca;
import com.project.pro.model.entity.Pedido;
import com.project.pro.model.entity.Profissional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoDTO extends AbstractDTO<Integer, Pedido> {
    private Integer id;

    private EnumTipoCobranca tipoCobranca;

    private ClienteDTO cliente;

    private ProfissionalDTO profissional;

    private double distancia;

}
