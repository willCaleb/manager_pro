package com.project.pro.service;

import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.model.dto.ClienteDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;

public interface IClienteService extends IAbstractService<Cliente, ClienteDTO, ClienteRepository>{

    Cliente incluir(Cliente cliente);

    ResponseEntity<?> loginCliente(LoginRequest loginRequest);
}
