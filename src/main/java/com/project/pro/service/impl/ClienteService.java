package com.project.pro.service.impl;

import com.project.pro.config.security.JwtTokenProvider;
import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.Role;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.model.dto.ClienteDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.ClienteRepository;
import com.project.pro.service.IClienteService;
import com.project.pro.service.IPessoaService;
import com.project.pro.service.IUsuarioService;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService extends AbstractService<Cliente, ClienteDTO, ClienteRepository> implements IClienteService{

    private final ClienteRepository clienteRepository;
    private final IPessoaService pessoaService;
    private final AuthenticationManager authenticationManager;
    private final IUsuarioService usuarioService;
    private ValidadorCliente validadorCliente = new ValidadorCliente();
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @Transactional
    public Cliente incluir(Cliente cliente) {

//        pessoaService.incluir(cliente.getPessoa());
        validadorCliente.validarCamposObrigatorios(cliente);

        Usuario usuario = incluirUsuario(cliente);

        cliente.setUsuario(usuario);

        return clienteRepository.save(cliente);
    }

    private Usuario incluirUsuario(Cliente cliente) {
        Usuario usuario = new Usuario();
        usuario.setUsername(cliente.getPessoa().getEmail());
        usuario.setPassword(passwordEncoder.encode(cliente.getPessoa().getSenha()));
        return usuarioService.incluir(usuario);
    }

    @Override
    public ResponseEntity<?> loginCliente(LoginRequest loginRequest) {

        buscarPorUsuario(loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication, Role.CLIENTE);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    private Cliente buscarPorUsuario(String username) {
        Usuario usuario = usuarioService.findByUsername(username);

        if (Utils.isEmpty(usuario)) {
            throw new CustomException(EnumCustomException.USUARIO_NAO_ENCONTRADO);
        }

        Cliente cliente = clienteRepository.findByUsuario(usuario);

        validadorCliente.validarClienteCadastrado(cliente);

        return cliente;
    }
}
