package com.project.pro.repository;

import com.project.pro.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicoRepository extends JpaRepository<Servico, Integer>, JpaSpecificationExecutor<Servico> {
}
