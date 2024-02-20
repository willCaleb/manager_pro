package com.project.pro.service.impl;

import com.project.pro.model.dto.ClienteDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.repository.ClienteRepository;
import com.project.pro.service.IClienteService;
import com.project.pro.service.IPessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService extends AbstractService<Cliente, ClienteDTO, ClienteRepository> implements IClienteService{

    private final ClienteRepository clienteRepository;
    private final IPessoaService pessoaService;

    public Cliente incluir(Cliente cliente) {

//        pessoaService.incluir(cliente.getPessoa());
        return clienteRepository.save(cliente);
    }
}
