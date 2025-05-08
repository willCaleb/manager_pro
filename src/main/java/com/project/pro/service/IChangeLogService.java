package com.project.pro.service;

import com.project.pro.model.dto.ChangeLogDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.ChangeLog;
import com.project.pro.repository.ChangeLogRepository;

import java.util.List;

public interface IChangeLogService extends IAbstractService<ChangeLog, ChangeLogDTO, ChangeLogRepository>{

    <T extends AbstractEntity> List<ChangeLog> incluir(T originalObject, T changedObject);
}
