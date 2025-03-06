package com.project.pro.service;

import com.project.pro.model.dto.ConfigDTO;
import com.project.pro.model.entity.Config;
import com.project.pro.repository.ConfigRepository;

import java.util.List;

public interface IConfigService extends IAbstractService<Config, ConfigDTO, ConfigRepository> {
    Config incluir(Config entity);

    void editar(Integer idConfig, Config config);

    List<Config> listarTodos();
}
