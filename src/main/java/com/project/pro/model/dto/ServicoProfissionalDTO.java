package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.enums.EnumTipoCobranca;
import com.project.pro.model.entity.ServicoProfissional;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServicoProfissionalDTO extends AbstractDTO<Integer, ServicoProfissional> {

    private Integer id;

    private ProfissionalDTO profissional;

    private ServicoDTO servico;

    private BigDecimal valor;

    private EnumTipoCobranca tipoCobranca;
}
