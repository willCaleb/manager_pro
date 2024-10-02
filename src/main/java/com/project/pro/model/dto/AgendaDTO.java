package com.project.pro.model.dto;

import com.project.pro.model.entity.Agenda;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class AgendaDTO extends AbstractDTO<Integer, Agenda>{

    private Integer id;

    private Date dataInclusao;

    private Date dataInicio;

    private Date dataFim;

    private ProfissionalDTO profissional;

    private List<PedidoItemDTO> itens;

    private ClienteDTO cliente;
}
