package com.project.pro.service;

import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.ChangeLogs;

import java.util.List;

public interface IChangeLogService {

    <T extends AbstractEntity> List<ChangeLogs> incluir(T originalObject, T changedObject);
}
