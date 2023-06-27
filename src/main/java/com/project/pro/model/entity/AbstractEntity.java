package com.project.pro.model.entity;

import com.project.pro.model.IIdentificador;
import com.project.pro.model.dto.AbstractDTO;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractEntity<I extends Number, DTO extends AbstractDTO> implements IIdentificador, Serializable {

    public DTO toDto() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        Class<DTO> entityClass = (Class) genericTypes[1];

        return new ModelMapper().map(this, entityClass);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + getId() + ")";
    }

}
