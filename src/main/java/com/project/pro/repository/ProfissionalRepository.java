package com.project.pro.repository;

import com.project.pro.model.entity.Pessoa;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer>, JpaSpecificationExecutor<Profissional> {

    List<Profissional> findAllByPessoa(Pessoa pessoa);

    @Query(value = "" +
            "select pro from Profissional pro " +
            "where pro.pessoa.nome like :nome ")
    List<Profissional> findAllByNome(@Param("nome") String nome);

    Profissional findByCpf(String cpf);

    Optional<Profissional> findByUsuario(Usuario usuario);
}
