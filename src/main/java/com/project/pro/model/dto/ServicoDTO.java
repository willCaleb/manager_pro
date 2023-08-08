package com.project.pro.model.dto;

import com.project.pro.enums.EnumEtapaServico;
import com.project.pro.model.entity.Categoria;
import com.project.pro.model.entity.Servico;
import com.project.pro.model.entity.ServicoProfissional;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServicoDTO extends AbstractDTO<Integer, Servico>{

    private Integer id;

    private String nome;

    private String descricao;

    private Integer tempoExecucao;

    private BigDecimal valor;

    private Boolean servico;

    private EnumEtapaServico etapaServico;

    private Categoria categoria;

    private List<ServicoProfissional> servicoProfissionais;
}
