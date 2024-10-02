package com.project.pro.service.impl;

import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.UsuarioRepository;
import com.project.pro.validator.ValidadorUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private ValidadorUsuario validadorUsuario = new ValidadorUsuario();

    @PostConstruct
    private void setValidadorUsuarioRepository() {
        validadorUsuario.setUsuarioRepository(usuarioRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities("USER")
                .build();
    }

    public Usuario incluir(Usuario usuario) {
        validadorUsuario.validarInsert(usuario);
        return usuarioRepository.save(usuario);

//        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }
}
