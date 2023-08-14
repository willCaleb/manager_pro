package com.project.pro.service;

import com.project.pro.model.dto.ClienteDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.repository.ClienteRepository;

public interface IClienteService extends IAbstractService<Cliente, ClienteDTO, ClienteRepository>{

    Cliente incluir(Cliente cliente);
}
