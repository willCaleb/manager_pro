package com.project.pro.model.entity;

import com.project.pro.enums.EnumTipoCobranca;
import com.project.pro.model.dto.PedidoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pedido")
public class Pedido extends AbstractEntity<Integer, PedidoDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_cobranca")
    @Convert(converter = EnumTipoCobranca.EnumConverter.class)
    private EnumTipoCobranca tipoCobranca;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private Profissional profissional;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PedidoItem> itens;
}
