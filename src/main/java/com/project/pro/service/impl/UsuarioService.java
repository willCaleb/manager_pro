package com.project.pro.service.impl;

import com.project.pro.model.dto.UsuarioDTO;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.UsuarioRepository;
import com.project.pro.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService extends AbstractService<Usuario, UsuarioDTO, UsuarioRepository> implements IUsuarioService {

    private final UserDetailsServiceImpl userDetailsService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario incluir(Usuario usuario) {
        return userDetailsService.incluir(usuario);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
//
//        if (Utils.isNotEmpty(usuario)) {
//            return usuario;
//        }
//        throw new CustomException(EnumCustomException.USUARIO_NAO_ENCONTRADO);
    }

    @Override
    public Usuario editar(Integer idUsuario, Usuario usuario) {
        return null;
    }
}
