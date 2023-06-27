package com.project.pro.controller;

import com.project.pro.model.dto.ServicoDTO;
import com.project.pro.model.entity.Servico;
import com.project.pro.service.IServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servico")
@RequiredArgsConstructor
public class ServicoController extends AbstractController<Servico, ServicoDTO>{

    private final IServicoService servicoService;

    @PostMapping
    public ServicoDTO incluir(@RequestBody ServicoDTO servicoDTO) {
        return servicoService.incluir(servicoDTO.toEntity()).toDto();
    }

}
