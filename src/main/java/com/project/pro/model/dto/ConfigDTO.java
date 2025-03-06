package com.project.pro.model.dto;

import com.project.pro.annotation.DtoFieldIgnore;
import com.project.pro.enums.EnumConfig;
import com.project.pro.model.entity.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigDTO extends AbstractDTO<Integer, Config>{

    private Integer id;

    private String nome;

    private String valorPadrao;

    private EnumConfig key;

    private Boolean ativo;

    private List<ConfigProfissionalDTO> configsProfissional;

    private Object valorUtilizado;

}
