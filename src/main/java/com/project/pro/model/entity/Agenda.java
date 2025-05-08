package com.project.pro.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.pro.enums.EnumStatusAgenda;
import com.project.pro.model.dto.AgendaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pro_agenda")
public class Agenda extends AbstractEntity<Integer, AgendaDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "data_inicio")
    private Date dataInicio;

    @Column(name = "data_fim")
    private Date dataFim;

    @ManyToOne
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private Profissional profissional;

    @JsonManagedReference
    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL)
    private List<PedidoItem> itens;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "status")
    private EnumStatusAgenda status;
}
