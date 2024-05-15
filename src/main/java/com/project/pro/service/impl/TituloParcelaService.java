package com.project.pro.service.impl;

import com.project.pro.model.dto.TituloParcelaDTO;
import com.project.pro.model.entity.Titulo;
import com.project.pro.model.entity.TituloParcela;
import com.project.pro.repository.TituloParcelaRepository;
import com.project.pro.service.ITituloParcelaService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TituloParcelaService extends AbstractService<TituloParcela, TituloParcelaDTO, TituloParcelaRepository> implements ITituloParcelaService {

    @Override
    public TituloParcela incluir(Integer idTitulo, TituloParcela parcela) {
        return null;
    }

    @Override
    public void editar(Integer idParcela, TituloParcela parcela) {

    }

    @Override
    public List<TituloParcela> gerarParcelas(Titulo titulo) {

        List<TituloParcela> parcelas = new ArrayList<>();

        Date dataVencimento = DateUtils.getDate();

        for (int i = 0; i <= titulo.getQntdParcelas(); i ++){

            if (!Utils.equals(i, 0)) {
                dataVencimento = DateUtils.add(dataVencimento, 30, Calendar.DAY_OF_MONTH);
            }

            TituloParcela parcela = new TituloParcela();
            parcela.setValorPagamento(resolverValorParcela(titulo));
            parcela.setOrdemParcela(i + 1);
            parcela.setTitulo(titulo);
            parcela.setDataVencimento(dataVencimento);
            parcelas.add(parcela);
        }
        return parcelas;
    }

    private BigDecimal resolverValorParcela(Titulo titulo) {
        prepareValues(titulo);
        return titulo.getValor()
                .add(titulo.getAcrescimos())
                .add(titulo.getJuros())
                .subtract(titulo.getDescontos())
                .divide(BigDecimal.valueOf(titulo.getQntdParcelas()), RoundingMode.FLOOR);
    }

    private void prepareValues(Titulo titulo) {
        resolverAcrescimo(titulo);
        resolverJuros(titulo);
        resolverDescontos(titulo);
    }

    private void resolverAcrescimo(Titulo titulo) {
        titulo.setAcrescimos(Utils.nvl(titulo.getAcrescimos(), BigDecimal.ZERO));
    }

    private void resolverJuros(Titulo titulo) {
        titulo.setJuros(Utils.nvl(titulo.getJuros(), BigDecimal.ZERO));
    }

    private void resolverDescontos(Titulo titulo) {
        titulo.setDescontos(Utils.nvl(titulo.getDescontos(), BigDecimal.ZERO));
    }
}
