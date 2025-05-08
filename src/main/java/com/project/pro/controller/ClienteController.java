package com.project.pro.controller;

import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.model.dto.ClienteDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.service.IClienteService;
import com.project.pro.service.impl.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController extends AbstractController<Cliente, ClienteDTO>{

    private final IClienteService clienteService;

    @PostMapping("/incluir")
    public ClienteDTO incluir(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.incluir(clienteDTO.toEntity()).toDto();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return clienteService.loginCliente(loginRequest);
    }
}
