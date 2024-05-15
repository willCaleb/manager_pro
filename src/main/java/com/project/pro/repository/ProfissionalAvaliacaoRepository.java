package com.project.pro.repository;

import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.ProfissionalAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfissionalAvaliacaoRepository extends JpaRepository<ProfissionalAvaliacao, Integer>, JpaSpecificationExecutor<ProfissionalAvaliacao> {

    ProfissionalAvaliacao findByProfissionalAndCliente(Profissional profissional, Cliente cliente);

}
