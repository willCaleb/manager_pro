package com.project.pro.repository;

import com.project.pro.model.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ConfigRepository extends JpaRepository<Config, Integer>, JpaSpecificationExecutor<Config> {
    List<Config> findAllByAtivoTrue();
}
