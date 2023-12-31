package com.project.pro.model.dto;

import com.project.pro.enums.EnumCategoriaPrincipal;
import com.project.pro.model.entity.Categoria;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CategoriaDTO extends AbstractDTO<Integer, Categoria> {

    private Integer id;

    private String nome;

    private CategoriaDTO categoriaSuperior;

    private EnumCategoriaPrincipal categoriaPrincipal;
}
