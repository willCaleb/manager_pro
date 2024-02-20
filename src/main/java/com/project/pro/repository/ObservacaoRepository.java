package com.project.pro.repository;

import com.project.pro.model.entity.Observacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ObservacaoRepository extends JpaRepository<Observacao, Integer>, JpaSpecificationExecutor<Observacao>{

    List<Observacao> findAllByEntityAndEntityId(String entity, Integer id);


}
