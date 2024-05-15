package com.project.pro.service.impl;

import com.project.pro.model.dto.ObservacaoDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Observacao;
import com.project.pro.repository.ObservacaoRepository;
import com.project.pro.service.IObservacaoService;
import com.project.pro.utils.DateUtils;
import com.project.pro.validator.ValidadorObservacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObservacaoService extends AbstractService<Observacao, ObservacaoDTO, ObservacaoRepository> implements IObservacaoService{

    private final ObservacaoRepository observacaoRepository;

    private ValidadorObservacao validadorObservacao = new ValidadorObservacao();

    @Override
    public Observacao incluir(Observacao observacao) {

        observacao.setDataInclusao(DateUtils.getDate());

        validadorObservacao.validarCamposObrigatorios(observacao);

        return observacaoRepository.save(observacao);
    }

    @Override
    public void excluir(Integer idObservacao) {
        Observacao observacao = findAndValidate(idObservacao);
        observacaoRepository.delete(observacao);
    }

    @Override
    public void editar(Observacao observacao, Integer idObservacao) {

        Observacao obervacaoManaged = findAndValidate(idObservacao);

        obervacaoManaged.setDataAlteracao(DateUtils.getDate());
        obervacaoManaged.setObservacao(observacao.getObservacao());
        validadorObservacao.validarCamposObrigatorios(obervacaoManaged);

        observacaoRepository.save(obervacaoManaged);
    }

    @Override
    public List<Observacao> listAllByEntity(AbstractEntity entity) {
        String entityName = entity.getClass().getSimpleName();
        Integer id = entity.getId();
        return observacaoRepository.findAllByEntityAndEntityId(entityName, id);
    }

    @Override
    public Observacao incluir(String observacao, AbstractEntity entity) {

        Observacao objObservacao = new Observacao();

        objObservacao.setObservacao(observacao);
        return incluir(objObservacao, entity);
    }

    @Override
    public Observacao incluir(Observacao observacao, AbstractEntity entity) {

        observacao.setEntity(entity.getClass().getSimpleName());
        observacao.setEntityId(entity.getId());

        return incluir(observacao);
    }
}
