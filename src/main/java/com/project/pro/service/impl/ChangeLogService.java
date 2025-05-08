package com.project.pro.service.impl;

import com.project.pro.model.dto.ChangeLogDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.ChangeLog;
import com.project.pro.pattern.Constants;
import com.project.pro.repository.ChangeLogRepository;
import com.project.pro.service.IChangeLogService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChangeLogService extends AbstractService<ChangeLog, ChangeLogDTO, ChangeLogRepository> implements IChangeLogService{

    private final ChangeLogRepository changeLogRepository;

    @Override
    public <T extends AbstractEntity> List<ChangeLog> incluir(T originalObject, T changedObject) {

        Class<?> aClass = originalObject.getClass();

        List<ChangeLog> changeLogList = new ArrayList<>();

        Map<String, Map<String, Object>> differences = ObjectUtils.getDifferences(originalObject, changedObject);

        differences.forEach((fieldName, changedValues) -> {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setDataInclusao(DateUtils.getDate());
            changeLog.setEntity(aClass.getSimpleName());
            changeLog.setEntityId(originalObject.getId());
            changeLog.setValorAnterior(changedValues.get(Constants.VALOR_ANTERIOR).toString());
            changeLog.setNovoValor(changedValues.get(Constants.NOVO_VALOR).toString());
            changeLog.setCampoAlterado(fieldName);
            changeLogList.add(changeLog);
        });

        return changeLogRepository.saveAll(changeLogList);
    }
}
