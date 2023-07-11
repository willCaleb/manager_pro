package com.project.pro.model.entity;

import com.project.pro.annotation.OnlyField;
import com.project.pro.model.IIdentificador;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.utils.ClassUtils;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import org.modelmapper.ModelMapper;
import sun.plugin2.message.Message;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class AbstractEntity<I extends Number, DTO extends AbstractDTO> implements IIdentificador, Serializable {

    public DTO toDto() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<DTO> dtoClass = (Class) genericTypes[1];
        return filter(this, dtoClass, null);
    }

    private DTO toDto(List<String> onlyFields) {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<DTO> dtoClass = (Class) genericTypes[1];
        return filter(this, dtoClass, onlyFields);
    }

    private DTO filter(AbstractEntity entity, Class<DTO> dtoType, List<String> onlyFields) {
        try {
            List<Field> allFieldsFromDto = ListUtils.toList(dtoType.getDeclaredFields());

            DTO dtoReturn = (DTO) dtoType.newInstance();

            allFieldsFromDto.forEach(field -> {
                if (Utils.isEmpty(onlyFields) || onlyFields.contains(field.getName())) {
                    try {
                        Method getterMethod = ClassUtils.getGetterMethod(field.getName(), entity.getClass());
                        Method setterMethod = ClassUtils.getSetterMethod(field.getName(), dtoReturn.getClass());
                        Object invoke = getterMethod.invoke(entity);

                        if (invoke == null) return;

                        final List<String> fieldsToFilter = getOnlyFields(field);

                        if (AbstractDTO.class.isAssignableFrom(field.getType())) {
                            AbstractEntity invokeEntity = (AbstractEntity) invoke;
                            setterMethod.invoke(dtoReturn, invokeEntity.toDto(fieldsToFilter));

                        } else if (field.getType().isAssignableFrom(List.class)) {
                            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                            Class<?> aClass = (Class<?>) genericType.getActualTypeArguments()[0];
                            if (AbstractDTO.class.isAssignableFrom(aClass)) {
                                List<AbstractEntity> invokeList = (List<AbstractEntity>) invoke;
                                if (ListUtils.isNotNullOrEmpty(invokeList)) {
                                    List<DTO> dtos = invokeList
                                            .stream()
                                            .map(inv -> (DTO) inv.toDto(fieldsToFilter))
                                            .collect(Collectors.toList());

                                    setterMethod.invoke(dtoReturn, dtos);
                                }
                            }
                        } else {
                            setterMethod.invoke(dtoReturn, getterMethod.invoke(entity));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            return dtoReturn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getOnlyFields(Field field) {
        List<String> fieldsToFilter = new ArrayList<>();
        if (field.isAnnotationPresent(OnlyField.class)) {
            OnlyField onlyField = field.getAnnotation(OnlyField.class);
            fieldsToFilter = ListUtils.toList(onlyField.fields());
        }
        return fieldsToFilter;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + getId() + ")";
    }

}
