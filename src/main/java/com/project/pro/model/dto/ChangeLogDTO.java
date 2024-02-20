package com.project.pro.model.dto;

import com.project.pro.model.entity.ChangeLog;
import lombok.Data;

import java.util.Date;

@Data
public class ChangeLogDTO extends AbstractDTO<Integer, ChangeLog>{

    private Integer id;

    private String valorAnterior;

    private String novoValor;

    private Date dataInclusao;

    private String campoAlterado;

    private String entity;

    private Integer entityId;

}
