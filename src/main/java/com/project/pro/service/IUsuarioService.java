package com.project.pro.service;

import com.project.pro.model.dto.UsuarioDTO;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.UsuarioRepository;

public interface IUsuarioService extends IAbstractService<Usuario, UsuarioDTO, UsuarioRepository>{

    Usuario incluir(Usuario usuario);

    Usuario findByUsername(String username);

    Usuario editar(Integer idUsuario, Usuario usuario);

}
