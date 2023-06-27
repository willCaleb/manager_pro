package com.project.pro.repository;

import com.project.pro.model.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer>, JpaSpecificationExecutor<Comentario> {
}
