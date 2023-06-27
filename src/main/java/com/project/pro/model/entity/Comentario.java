package com.project.pro.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.pro.model.dto.ComentarioDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "comentarios")
public class Comentario extends AbstractEntity<Integer, ComentarioDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "dataInclusao")
    private Date dataInclusao;

    @Column(name = "dataAlteracao")
    private Date dataAlteracao;

    @Column(name = "publico")
    private boolean publico;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_pessoa_remetente", referencedColumnName = "id")
    private Pessoa remetente;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_pessoa_destinatario", referencedColumnName = "id")
    private Pessoa destinatario;
}
