package com.project.pro.model.dto;

import com.project.pro.enums.EnumTipoCobranca;
import com.project.pro.model.entity.ServicoProfissional;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicoProfissionalDTO extends AbstractDTO<Integer, ServicoProfissional> {

    private Integer id;

    private ProfissionalDTO profissional;

    private ServicoDTO servico;

    private BigDecimal valor;

    private EnumTipoCobranca tipoCobranca;
}
