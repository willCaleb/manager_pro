package com.project.pro.model.entity;

import com.project.pro.model.dto.ObservacaoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "pro_observacao")
public class Observacao extends AbstractEntity<Integer, ObservacaoDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Column(name = "entity")
    private String entity;

    @Column(name = "entity_id")
    private Integer entityId;

}
