package com.project.pro.repository;

import com.project.pro.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer>, JpaSpecificationExecutor<Endereco> {

    @Query(value = "select * from endereco e " +
            "where e.cep is not null and (e.latitude is null or e.longitude is null)", nativeQuery = true)
    List<Endereco> findAllWithoutCoordenate();

}
