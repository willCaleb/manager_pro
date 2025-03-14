package com.project.pro.model.dto;

import com.project.pro.model.entity.Role;
import com.project.pro.model.entity.Usuario;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioDTO extends AbstractDTO<Integer, Usuario> {

    private Integer id;

    private String username;

    private String password;

    private Role role;
}
