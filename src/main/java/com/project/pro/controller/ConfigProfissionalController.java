package com.project.pro.controller;

import com.project.pro.model.dto.ConfigProfissionalDTO;
import com.project.pro.service.IConfigProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConfigProfissionalController.PATH)
public class ConfigProfissionalController {

    public static final String PATH = ProfissionalController.PATH + "/config";

    private final IConfigProfissionalService configProfissionalService;

    @PostMapping
    public ConfigProfissionalDTO incluir(@RequestBody ConfigProfissionalDTO configProfissionalDTO) {
        return configProfissionalService.incluir(configProfissionalDTO.toEntity()).toDto();
    }

}
