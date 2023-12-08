package com.project.pro.model.dto;

import com.project.pro.model.entity.ChangeLogs;
import lombok.Data;

import java.util.Date;

@Data
public class ChangeLogsDTO extends AbstractDTO<Integer, ChangeLogs>{

    private Integer id;

    private String valorAnterior;

    private String novoValor;

    private Date dataInclusao;

    private String campoAlterado;

    private String entity;

    private Integer entityId;

}
