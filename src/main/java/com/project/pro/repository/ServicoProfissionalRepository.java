package com.project.pro.repository;

import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Servico;
import com.project.pro.model.entity.ServicoProfissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicoProfissionalRepository extends JpaRepository<ServicoProfissional, Integer>, JpaSpecificationExecutor<ServicoProfissional> {


    ServicoProfissional findByProfissionalAndServico(Profissional profissional, Servico servico);
}
