package com.project.pro.service.impl;

import com.project.pro.model.dto.TituloDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Titulo;
import com.project.pro.model.entity.TituloParcela;
import com.project.pro.repository.TituloRepository;
import com.project.pro.service.*;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorTitulo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TituloService extends AbstractService<Titulo, TituloDTO, TituloRepository> implements ITituloService {

    private final TituloRepository tituloRepository;
    private final IProfissionalService profissionalService;
    private final ITituloParcelaService tituloParcelaService;
    private final IClienteService clienteService;
    private final IObservacaoService observacaoService;
    private ValidadorTitulo validadorTitulo = new ValidadorTitulo();

    @Override
    public Titulo incluir(Titulo titulo) {

        onPrepareInsert(titulo);
        Titulo tituloSalvo = tituloRepository.save(titulo);
        incluirObservacoes(tituloSalvo);

        return tituloSalvo;
    }

    private void onPrepareInsert(Titulo titulo) {
        validadorTitulo.validarCamposObrigatorios(titulo);
        Cliente cliente = clienteService.findAndValidate(titulo.getCliente().getId());
        Profissional profissional = profissionalService.findAndValidate(titulo.getProfissional().getId());
        titulo.setCliente(cliente);
        titulo.setProfissional(profissional);
        titulo.setDataInclusao(DateUtils.getDate());
        titulo.setParcelas(gerarParcelas(titulo));
    }

    private void incluirObservacoes(Titulo titulo) {
        if (ListUtils.isNotNullOrEmpty(titulo.getObservacoes())) {
            titulo.getObservacoes().forEach(observacao -> observacaoService.incluir(observacao, titulo));
        }
    }

    @Override
    public void editar(Integer idTitulo, Titulo titulo) {

        Titulo tituloManaged = findAndValidate(idTitulo);

        validadorTitulo.validarCancelado(tituloManaged);
        validadorTitulo.validarLiquidado(tituloManaged);
        validadorTitulo.validarTemObservacao(titulo);

        Profissional profissional = profissionalService.findAndValidate(titulo.getProfissional().getId());

        tituloManaged.setAcrescimos(titulo.getAcrescimos());
        tituloManaged.setDataVencimento(Utils.nvl(titulo.getDataVencimento(), titulo.getDataVencimento()));
        tituloManaged.setDescontos(titulo.getDescontos());
        tituloManaged.setJuros(titulo.getJuros());
        tituloManaged.setProfissional(Utils.nvl(profissional, tituloManaged.getProfissional()));
        tituloManaged.setObservacoes(titulo.getObservacoes());

        onPrepareUpdate(tituloManaged);
        incluirObservacoes(tituloManaged);
        if (!ListUtils.equals(titulo.getParcelas(), tituloManaged.getParcelas())) {
            tituloManaged.setParcelas(titulo.getParcelas());
            recalcularParcelas(tituloManaged);
        }
        tituloRepository.save(tituloManaged);
    }

    private void onPrepareUpdate(Titulo titulo) {
        titulo.setDataAlteracao(DateUtils.getDate());
    }

    private void recalcularParcelas(Titulo titulo) {
        titulo.getParcelas().forEach(this::calcularValorParcela);
    }

    private void calcularValorParcela(TituloParcela parcela) {
        BigDecimal valor = parcela.getValorPagamento()
                .subtract(Utils.nvl(parcela.getDesconto(), BigDecimal.ZERO))
                .add(Utils.nvl(parcela.getAcrescimo(), BigDecimal.ZERO));
        parcela.setValorPagamento(valor);
    }

    private List<TituloParcela> gerarParcelas(Titulo titulo) {
        resolverQntdParcelas(titulo);
        return tituloParcelaService.gerarParcelas(titulo);
    }

    private void resolverQntdParcelas(Titulo titulo) {
        if (Utils.isEmpty(titulo.getQntdParcelas())) {
            titulo.setQntdParcelas(1);
        }
    }
}
