package com.project.pro.repository;

import com.project.pro.model.entity.TituloParcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TituloParcelaRepository extends JpaRepository<TituloParcela, Integer>, JpaSpecificationExecutor<TituloParcela>{
}
