package com.project.pro.service;

import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.ChangeLog;

import java.util.List;

public interface IChangeLogService {

    <T extends AbstractEntity> List<ChangeLog> incluir(T originalObject, T changedObject);
}
