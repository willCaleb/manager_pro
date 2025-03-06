package com.project.pro.service.impl;

import com.project.pro.model.dto.ConfigDTO;
import com.project.pro.model.entity.Config;
import com.project.pro.model.entity.ConfigProfissional;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ConfigRepository;
import com.project.pro.service.IConfigProfissionalService;
import com.project.pro.service.IConfigService;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigService extends AbstractService<Config, ConfigDTO, ConfigRepository> implements IConfigService {

    private final ConfigRepository configRepository;

    private final IConfigProfissionalService configProfissionalService;

    @Override
    public Config incluir(Config config) {
        return save(config);
    }

    @Override
    public void editar(Integer idConfig, Config config) {

        Config configManaged = findAndValidate(idConfig);

        configManaged.setKey(Utils.nvl(config.getKey(), configManaged.getKey()));
        configManaged.setNome(Utils.nvl(config.getNome(), configManaged.getNome()));
        configManaged.setValorPadrao(Utils.nvl(config.getValorPadrao(), configManaged.getValorPadrao()));

        save(configManaged);

    }

    @Override
    public List<Config> listarTodos() {
        List<Config> configs = configRepository.findAllByAtivoTrue();

        Profissional profissional = getProfissional();

        configs.forEach(config -> {
            Optional<ConfigProfissional> optionalConfigProfissional = configProfissionalService.findByConfigAndProfissional(config, profissional);

//            config.setV
        });

        return new ArrayList<>();
    }
}
