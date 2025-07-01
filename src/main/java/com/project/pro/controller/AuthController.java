package com.project.pro.controller;

import com.project.pro.config.security.JwtTokenProvider;
import com.project.pro.enums.EnumRole;
import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.model.dto.UsuarioDTO;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.UsuarioRepository;
import com.project.pro.service.IAuthService;
import com.project.pro.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController extends AbstractController<Usuario, UsuarioDTO>{

    private final PasswordEncoder passwordEncoder;

    private final IUsuarioService usuarioService;

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            return authService.getJwtAuthenticationResponseEntity(loginRequest);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }


    @PostMapping("/register")
    public UsuarioDTO registerUser(@RequestBody UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioDTO.toEntity();

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario novoUsuario = usuarioService.incluir(usuario);

        return novoUsuario.toDto();
    }
}
