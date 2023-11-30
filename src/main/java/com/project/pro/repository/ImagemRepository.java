package com.project.pro.repository;

import com.project.pro.model.entity.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Integer>, JpaSpecificationExecutor<Imagem>{

    List<Imagem> findAllByEntityNameAndEntityIdAndAtivoIsTrueAndDeletedIsFalse(String entityName, Integer entityId);

}
