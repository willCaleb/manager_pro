package com.project.pro.model.entity;

import com.project.pro.enums.EnumStatusPedido;
import com.project.pro.model.dto.PedidoItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "pedido_item")
public class PedidoItem extends AbstractEntity<Integer, PedidoItemDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "acrescimo")
    private BigDecimal acrescimo;

    @Column(name = "desconto")
    private BigDecimal desconto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "ordem")
    private Integer ordem;

    @Column
    @Convert(converter = EnumStatusPedido.EnumConverter.class)
    private EnumStatusPedido status;

    @OneToOne
    @JoinColumn(name = "id_servico_profissional", referencedColumnName = "id")
    private ServicoProfissional servicoProfissional;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private Pedido pedido;
}
