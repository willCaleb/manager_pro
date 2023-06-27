package com.project.pro.model.dto;

import com.project.pro.model.IIdentificador;
import com.project.pro.model.entity.AbstractEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Data
@SuppressWarnings("unchecked")
public abstract class AbstractDTO<I extends Number, E extends AbstractEntity> implements IIdentificador, Serializable {

    public E toEntity() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        Class<E> entityClass = (Class) genericTypes[1];

        return new ModelMapper().map(this, entityClass);
    }
}
