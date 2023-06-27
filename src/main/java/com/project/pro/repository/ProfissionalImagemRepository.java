package com.project.pro.repository;

import com.project.pro.model.entity.ProfissionalImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfissionalImagemRepository extends JpaRepository<ProfissionalImagem, Integer>, JpaSpecificationExecutor<ProfissionalImagem> {
}
