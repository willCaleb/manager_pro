package com.project.pro.controller;

import com.project.pro.model.dto.EnderecoDTO;
import com.project.pro.model.entity.Endereco;
import com.project.pro.service.IEnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoController extends AbstractController<Endereco, EnderecoDTO>{

    private final IEnderecoService enderecoService;

    @GetMapping
    public List<EnderecoDTO> findAllWithoutCoordenates() {
        return toDtoList(enderecoService.findAllWithoutCoordenate());
    }

}
