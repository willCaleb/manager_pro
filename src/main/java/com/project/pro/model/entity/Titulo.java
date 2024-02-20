package com.project.pro.model.entity;

import com.project.pro.enums.EnumFormaPagamento;
import com.project.pro.enums.EnumStatusTitulo;
import com.project.pro.model.dto.TituloDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pro_titulo")
public class Titulo extends AbstractEntity<Integer, TituloDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor")
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "juros")
    private BigDecimal juros;

    @Column(name = "acrescimos")
    private BigDecimal acrescimos;

    @Column(name = "descontos")
    private BigDecimal descontos;

    @Transient
    private List<Observacao> observacoes;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @Column(name = "qntd_parcelas")
    private Integer qntdParcelas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "titulo")
    private List<TituloParcela> parcelas;

    @Column(name = "forma_pagamento")
    @Convert(converter = EnumFormaPagamento.EnumConverter.class)
    private EnumFormaPagamento formaPagamento;

    @Column(name = "status")
    @Convert(converter = EnumStatusTitulo.EnumConverter.class)
    private EnumStatusTitulo status;

}
