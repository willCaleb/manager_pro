package com.project.pro.repository;

import com.project.pro.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>, JpaSpecificationExecutor<Usuario> {

    Usuario findByUsername(String username);

}
