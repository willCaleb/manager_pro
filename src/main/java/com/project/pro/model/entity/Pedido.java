package com.project.pro.model.entity;

import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.model.dto.PedidoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pedido")
public class Pedido extends AbstractEntity<Integer, PedidoDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "status_pedido")
    @Convert(converter = EnumStatusPedido.EnumConverter.class)
    private EnumStatusPedido statusPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private Profissional profissional;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PedidoItem> itens;

    @Transient
    private double distancia;

}
