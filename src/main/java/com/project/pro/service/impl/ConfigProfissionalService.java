package com.project.pro.service.impl;

import com.project.pro.model.dto.ConfigProfissionalDTO;
import com.project.pro.model.entity.Config;
import com.project.pro.model.entity.ConfigProfissional;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ConfigProfissionalRepository;
import com.project.pro.service.IConfigProfissionalService;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorConfigProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigProfissionalService extends AbstractService<ConfigProfissional, ConfigProfissionalDTO, ConfigProfissionalRepository> implements IConfigProfissionalService {

    private ValidadorConfigProfissional validadorConfigProfissional = new ValidadorConfigProfissional();

    private final ConfigProfissionalRepository configProfissionalRepository;

    @PostConstruct
    private void setValidadorConfigProfissionalRepository() {
        validadorConfigProfissional.setConfigProfissionalRepository(configProfissionalRepository);
    }

    @Override
    public ConfigProfissional incluir(ConfigProfissional configProfissional) {

        onPrepareInsert(configProfissional);

        validadorConfigProfissional.validarInsert(configProfissional);


        return save(configProfissional);
    }

    private void onPrepareInsert(ConfigProfissional configProfissional) {
        configProfissional.setProfissional(getProfissional());
    }

    @Override
    public void editar(Integer idProfissionalConfig, ConfigProfissional configProfissional) {
        ConfigProfissional configProfissionalManaged = findAndValidate(idProfissionalConfig);

        configProfissionalManaged.setValor(Utils.nvl(configProfissional.getValor(), configProfissionalManaged.getValor()));

        save(configProfissionalManaged);
    }

    @Override
    public Optional<ConfigProfissional> findByConfigAndProfissional(Config config, Profissional profissional) {
        return configProfissionalRepository.findByConfigAndProfissional(config, profissional);
    }
}
