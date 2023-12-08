package com.project.pro.service.impl;

import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.ChangeLogs;
import com.project.pro.repository.ChangeLogsRepository;
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
public class ChangeLogsService implements IChangeLogService{

    private final ChangeLogsRepository changeLogsRepository;

    @Override
    public <T extends AbstractEntity> List<ChangeLogs> incluir(T originalObject, T changedObject) {

        Class<?> aClass = originalObject.getClass();

        List<ChangeLogs> changeLogsList = new ArrayList<>();

        Map<String, Map<String, Object>> differences = ObjectUtils.getDifferences(originalObject, changedObject);

        differences.forEach((fieldName, changedValues) -> {
            ChangeLogs changeLogs = new ChangeLogs();
            changeLogs.setDataInclusao(DateUtils.getDate());
            changeLogs.setEntity(aClass.getSimpleName());
            changeLogs.setEntityId(originalObject.getId());
            changeLogs.setValorAnterior(changedValues.get(ObjectUtils.VALOR_ANTERIOR).toString());
            changeLogs.setNovoValor(changedValues.get(ObjectUtils.NOVO_VALOR).toString());
            changeLogs.setCampoAlterado(fieldName);
            changeLogsList.add(changeLogs);
        });

        return changeLogsRepository.saveAll(changeLogsList);
    }
}
