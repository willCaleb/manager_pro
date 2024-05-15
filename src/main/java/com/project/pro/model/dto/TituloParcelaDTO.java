package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.enums.EnumStatusTituloParcela;
import com.project.pro.model.entity.TituloParcela;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TituloParcelaDTO extends AbstractDTO<Integer, TituloParcela>{

    private Integer id;

    private Date dataVencimento;

    private Date dataPagamento;

    private Integer ordemParcela;

    @OnlyField(fields = {"id", "valor", "dataVencimento", "formaPagamento"})
    private TituloDTO titulo;

    private BigDecimal acrescimo;

    private BigDecimal desconto;

    private BigDecimal valorPagamento;

    private EnumStatusTituloParcela status;

}
