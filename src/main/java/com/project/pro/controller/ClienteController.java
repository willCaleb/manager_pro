package com.project.pro.controller;

import com.project.pro.model.dto.ClienteDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.service.IClienteService;
import com.project.pro.service.impl.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController extends AbstractController<Cliente, ClienteDTO>{

    private final IClienteService clienteService;

    @PostMapping
    public ClienteDTO incluir(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.incluir(clienteDTO.toEntity()).toDto();
    }
}
