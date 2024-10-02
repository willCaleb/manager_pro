package com.project.pro.model.dto;

import com.google.gson.reflect.TypeToken;
import com.project.pro.model.IIdentificador;
import com.project.pro.model.entity.AbstractEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuppressWarnings("unchecked")
public abstract class AbstractDTO<I extends Number, E extends AbstractEntity> implements IIdentificador, Serializable {

    public E toEntity() {
        Class<E> entityClass = getEntityClass();

        List<Field> fields = Arrays.asList(entityClass.getDeclaredFields());

        fields.forEach(field -> {
            if (field.getClass().isAssignableFrom(List.class) && field.getGenericType() instanceof AbstractEntity) {
                try {
                    toEntity((List<E>)field.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(context -> context.getSource() != null ?
                modelMapper.map(context.getSource(), new TypeToken<List<E>>() {}.getType()) : null);

        return  modelMapper.map(this, entityClass);
    }

    private Class<E> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        return (Class) genericTypes[1];
    }

    public List<E> toEntity(List<E> dtoList) {
        return dtoList.stream().map(this::apply).collect(Collectors.toList());
    }

    private E apply(E dto) {
        return new ModelMapper().map(dto, getEntityClass());
    }
}
