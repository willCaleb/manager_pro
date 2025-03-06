package com.project.pro.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pro.annotation.DtoFieldIgnore;
import com.project.pro.model.entity.ConfigProfissional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigProfissionalDTO extends AbstractDTO<Integer, ConfigProfissional> {

    private Integer id;

    @DtoFieldIgnore
    private ConfigDTO config;

    private ProfissionalDTO profissional;

    private String valor;
}
