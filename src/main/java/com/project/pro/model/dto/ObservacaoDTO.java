package com.project.pro.model.dto;

import com.project.pro.model.entity.Observacao;
import lombok.Data;

import java.util.Date;

@Data
public class ObservacaoDTO extends AbstractDTO<Integer, Observacao>{

    private Integer id;

    private String observacao;

    private Date dataInclusao;

    private Date dataAlteracao;

    private String entity;

    private Integer entityId;
}
