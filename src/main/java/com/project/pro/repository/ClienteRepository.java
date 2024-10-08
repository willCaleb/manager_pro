package com.project.pro.repository;

import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {

    Cliente findByUsuario(Usuario usuario);

}
