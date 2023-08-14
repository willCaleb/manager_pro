package com.project.pro.service;

import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ProfissionalRepository;

public interface IProfissionalService extends IAbstractService<Profissional, ProfissionalDTO, ProfissionalRepository>{

    Profissional incluir(Profissional profissional);

    void editar(Integer idProfissional, Profissional profissional);

    Profissional incluirServico(Integer idServico, Integer idProfissional);
}
