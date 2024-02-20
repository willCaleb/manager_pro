package com.project.pro.repository;

import com.project.pro.model.entity.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TituloRepository extends JpaRepository<Titulo, Integer>, JpaSpecificationExecutor<Titulo>{
}
