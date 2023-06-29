package com.project.pro.model.entity;

import com.project.pro.annotation.OnlyField;
import com.project.pro.model.IIdentificador;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.utils.ClassUtils;
import com.project.pro.utils.Utils;
import org.modelmapper.ModelMapper;
import sun.plugin2.message.Message;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractEntity<I extends Number, DTO extends AbstractDTO> implements IIdentificador, Serializable {

    public DTO toDto() {
        return new ModelMapper().map(this, getDtoClass());
    }

    private DTO filter(AbstractEntity entity) {
        OnlyField onlyField = getDtoClass().getAnnotation(OnlyField.class);
        if (Utils.isNotEmpty(onlyField.fields())) {
            try {
                List<String> fieldsToFilter = Arrays.asList(onlyField.fields());
                DTO dtoToFilter = getDtoClass().newInstance();
                List<Field> allFieldsFromDto = Arrays.asList(dtoToFilter.getClass().getDeclaredFields());

                fieldsToFilter.forEach(field -> {
                    allFieldsFromDto
                            .stream()
                            .filter(dtoField -> dtoField.getName()
                                    .equals(field))
                            .findFirst()
                            .ifPresent(d -> {
                                try {
                                    Method setterMethod = ClassUtils.getSetterMethod(field, getDtoClass());
                                    Method getterMethod = ClassUtils.getGetterMethod(field, entity.getClass());
                                    setterMethod.invoke(dtoToFilter, getterMethod);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                });
                return dtoToFilter;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelMapper().map(entity, getDtoClass());
    }

    private Class<DTO> getDtoClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class) genericTypes[1];
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + getId() + ")";
    }

}
