package com.project.pro.service.impl;

import com.project.pro.model.entity.Cliente;
import com.project.pro.repository.ClienteRepository;
import com.project.pro.service.IClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService{

    private final ClienteRepository clienteRepository;

    public Cliente incluir(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
