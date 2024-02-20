package com.project.pro.model.dto;

import com.project.pro.annotation.OnlyField;
import com.project.pro.enums.EnumFormaPagamento;
import com.project.pro.enums.EnumStatusTitulo;
import com.project.pro.model.entity.Titulo;
import lombok.Data;

import javax.persistence.PostLoad;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TituloDTO extends AbstractDTO<Integer, Titulo>{

    private Integer id;

    private BigDecimal valor;

    private ProfissionalDTO profissional;

    private ClienteDTO cliente;

    private BigDecimal juros;

    private BigDecimal acrescimos;

    private BigDecimal descontos;

    private Date dataInclusao;

    private Date dataAlteracao;

    private Date dataVencimento;

    private Integer qntdParcelas;

    @OnlyField(fields = {"id", "observacao"})
    private List<ObservacaoDTO> observacoes;

    private EnumFormaPagamento formaPagamento;

    @OnlyField(fields = {"id", "valorPagamento", "ordemParcela", "dataVencimento", "dataPagamento"})
    private List<TituloParcelaDTO> parcelas;

    private EnumStatusTitulo status;

    @PostLoad
    public void setQntdParcelas() {
        if (!EnumFormaPagamento.CARTAO_CREDITO.equals(formaPagamento)) {
            this.qntdParcelas = 1;
        }
    }
}
