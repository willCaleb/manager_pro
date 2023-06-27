package com.project.pro.service;

import com.project.pro.model.entity.Profissional;

public interface IProfissionalService {

    Profissional incluir(Profissional profissional);

    void editar(Integer idProfissional, Profissional profissional);

    Profissional incluirServico(Integer idServico, Integer idProfissional);
}
