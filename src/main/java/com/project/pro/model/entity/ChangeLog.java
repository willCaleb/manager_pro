package com.project.pro.model.entity;

import com.project.pro.model.dto.ChangeLogDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "pro_change_logs")
public class ChangeLog extends AbstractEntity<Integer, ChangeLogDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor_anterior")
    private String valorAnterior;

    @Column(name = "novo_valor")
    private String novoValor;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "campo_alterado")
    private String campoAlterado;

    @Column(name = "entity")
    private String entity;

    @Column(name = "entity_id")
    private Integer entityId;

}
