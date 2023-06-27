package com.project.pro.service;

import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAbstractService<E extends AbstractEntity<?, D>, D extends AbstractDTO<?, E>, R extends JpaRepository> {

    E findAndValidate(Integer id);
}
