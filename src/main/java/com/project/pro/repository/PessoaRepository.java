package com.project.pro.repository;

import com.project.pro.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, JpaSpecificationExecutor<Pessoa> {

    Pessoa findByCpfCnpj(String cpfCpnpj);

    List<Pessoa> findAllByCpfCnpj(String cpfCnpj);
}
