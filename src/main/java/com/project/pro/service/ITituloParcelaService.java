package com.project.pro.service;

import com.project.pro.model.dto.TituloParcelaDTO;
import com.project.pro.model.entity.Titulo;
import com.project.pro.model.entity.TituloParcela;
import com.project.pro.repository.TituloParcelaRepository;

import java.util.List;

public interface ITituloParcelaService extends IAbstractService<TituloParcela, TituloParcelaDTO, TituloParcelaRepository>{

    TituloParcela incluir(Integer idTitulo, TituloParcela parcela);

    void editar(Integer idParcela, TituloParcela parcela);

    List<TituloParcela> gerarParcelas(Titulo titulo);

}
