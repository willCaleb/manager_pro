package com.project.pro.repository;

import com.project.pro.model.entity.Pessoa;
import com.project.pro.model.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer>, JpaSpecificationExecutor<Profissional> {

//    List<Profissional> findAllByCategorias(Set<Categoria> categorias);

    List<Profissional> findAllByPessoa(Pessoa pessoa);
}
