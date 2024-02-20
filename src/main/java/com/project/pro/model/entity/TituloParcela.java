package com.project.pro.model.entity;

import com.project.pro.enums.EnumStatusTituloParcela;
import com.project.pro.model.dto.TituloParcelaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "pro_titulo_parcela")
public class TituloParcela extends AbstractEntity<Integer, TituloParcelaDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Column(name = "ordem_parcela")
    private Integer ordemParcela;

    @ManyToOne
    @JoinColumn(name = "id_titulo", referencedColumnName = "id")
    private Titulo titulo;

    @Column(name = "acrescimo")
    private BigDecimal acrescimo;

    @Column(name = "desconto")
    private BigDecimal desconto;

    @Column(name = "valor_total")
    private BigDecimal valorPagamento;

    @Column(name = "status")
    private EnumStatusTituloParcela status;
}
