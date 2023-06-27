package com.project.pro.model.entity;

import com.project.pro.enums.EnumCategoriaPrincipal;
import com.project.pro.model.dto.CategoriaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Categoria extends AbstractEntity<Integer, CategoriaDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_superior", referencedColumnName = "id")
    private Categoria categoriaSuperior;

    @Column(name = "categoria_principal")
    @Convert(converter = EnumCategoriaPrincipal.EnumConverter.class)
    private EnumCategoriaPrincipal categoriaPrincipal;

}
