package com.project.pro.repository;

import com.project.pro.model.entity.Config;
import com.project.pro.model.entity.ConfigProfissional;
import com.project.pro.model.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ConfigProfissionalRepository extends JpaRepository<ConfigProfissional, Integer>, JpaSpecificationExecutor<ConfigProfissional> {
    Optional<ConfigProfissional> findByConfigAndProfissional(Config config, Profissional profissional);
}
