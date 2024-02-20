package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.model.entity.Pedido;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoDTO extends AbstractDTO<Integer, Pedido> {
    private Integer id;

    private ClienteDTO cliente;

//    @OnlyField(fields = {"pessoa", "id"})
    private ProfissionalDTO profissional;

    private EnumStatusPedido statusPedido;

//    @ElementCollection(fetch = FetchType.LAZY)
    private List<PedidoItemDTO> itens;

    private double distancia;

}
