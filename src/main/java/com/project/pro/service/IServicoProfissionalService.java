package com.project.pro.service;

import com.project.pro.model.dto.ServicoProfissionalDTO;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Servico;
import com.project.pro.model.entity.ServicoProfissional;
import com.project.pro.repository.ServicoProfissionalRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IServicoProfissionalService extends IAbstractService<ServicoProfissional, ServicoProfissionalDTO, ServicoProfissionalRepository>{

    ServicoProfissional incluir(ServicoProfissional servicoProfissional);

    void excluir(Integer idServicoProfissional);

    @Query("select sp from ServicoProfissional sp where profissional = :profissional and servico = :servico")
    ServicoProfissional findByProfissionalAndServico(@Param("profissional") Profissional profissional, @Param("servico") Servico servico);
}
