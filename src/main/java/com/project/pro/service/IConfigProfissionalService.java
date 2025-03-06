package com.project.pro.service;

import com.project.pro.model.dto.ConfigProfissionalDTO;
import com.project.pro.model.entity.Config;
import com.project.pro.model.entity.ConfigProfissional;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ConfigProfissionalRepository;

import java.util.Optional;

public interface IConfigProfissionalService extends IAbstractService<ConfigProfissional, ConfigProfissionalDTO, ConfigProfissionalRepository>{

    ConfigProfissional incluir(ConfigProfissional configProfissional);

    void editar(Integer idProfissionalConfig, ConfigProfissional configProfissional);

    Optional<ConfigProfissional> findByConfigAndProfissional(Config config, Profissional profissional);

}
