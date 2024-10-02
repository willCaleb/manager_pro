package com.project.pro.service;

import com.project.pro.model.dto.ObservacaoDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Observacao;
import com.project.pro.repository.ObservacaoRepository;

import java.util.List;

public interface IObservacaoService extends IAbstractService<Observacao, ObservacaoDTO, ObservacaoRepository>{

    Observacao incluir(Observacao observacao);

    Observacao incluir(String observacao, AbstractEntity entity);

    Observacao incluir(Observacao observacao, AbstractEntity entity);

    void editar(Observacao observacao, Integer idObservacao);

    void excluir(Integer idObservacao);

    List<Observacao> listAllByEntity(AbstractEntity entity);

}
