package com.project.pro.controller;

import com.project.pro.model.dto.ConfigDTO;
import com.project.pro.model.entity.Config;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ConfigController.PATH)
public class ConfigController{

    public static final String PATH = "/config";

    private final IConfigService configService;

    @PostMapping
    public ConfigDTO incluir(@RequestBody ConfigDTO configDTO) {
        return configService.incluir(configDTO.toEntity()).toDto();
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@PathVariable(OperationsParam.ID) Integer idConfig, @RequestBody ConfigDTO configDTO) {
        configService.editar(idConfig, configDTO.toEntity());
    }

    @GetMapping
    public List<ConfigDTO> findAll() {
        return configService.listarTodos().stream().map(Config::toDto).collect(Collectors.toList());
    }

}
