package com.project.pro.utils;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.pattern.Constants;
import com.project.pro.pattern.OperationsParam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class ObjectUtils {

    public static <T> Map<String, Map<String, Object>> getDifferences(T originalObject, T changedObject) {

        if (!ClassUtils.isSameClass(originalObject, changedObject)) {
            throw new CustomException(EnumCustomException.OBJETOS_CLASSES_DIFERENTES);
        }

        Class<?> aClass = originalObject.getClass();

        List<Field> fields = ClassUtils.getFieldsAsList(aClass);
        Map<String, Map<String, Object>> returnMap = new HashMap<>();

        for (Field field : fields) {
            if (isRelevant(field)) {
                try {
                    Map<String, Object> changedFields = new HashMap<>();

                    Object objRetornoOriginal = field.get(originalObject);
                    Object objRetornoAlterado = field.get(changedObject);

                    if (Utils.isEmpty(objRetornoOriginal) && Utils.isEmpty(objRetornoAlterado)) {
                        continue;
                    }
                    if (objRetornoOriginal instanceof List && !ListUtils.equals((Collection) objRetornoOriginal, (Collection) objRetornoAlterado)) {
                        if (ListUtils.isNotNullOrEmpty((Collection<?>) objRetornoAlterado)) {
                            changedFields.put(Constants.VALOR_ANTERIOR, objRetornoOriginal);
                            changedFields.put(Constants.NOVO_VALOR, objRetornoAlterado);
                            returnMap.put(field.getName(), changedFields);
                            continue;
                        }
                    } else if (Utils.isEmpty(objRetornoOriginal) && Utils.isNotEmpty(objRetornoAlterado)){
                        changedFields.put(Constants.VALOR_ANTERIOR, "");
                        changedFields.put(Constants.NOVO_VALOR, objRetornoAlterado);
                        returnMap.put(field.getName(), changedFields);
                        continue;
                    }else if (Utils.isNotEmpty(objRetornoOriginal) && Utils.isEmpty(objRetornoAlterado)) {
                        changedFields.put(Constants.VALOR_ANTERIOR, objRetornoOriginal);
                        changedFields.put(Constants.NOVO_VALOR, "");
                        returnMap.put(field.getName(), changedFields);
                        continue;
                    } else if(!objRetornoOriginal.equals(objRetornoAlterado)) {
                        changedFields.put(Constants.VALOR_ANTERIOR, objRetornoOriginal);
                        changedFields.put(Constants.NOVO_VALOR, objRetornoAlterado);
                        returnMap.put(field.getName(), changedFields);
                        continue;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return returnMap;
    }

    private static boolean isRelevant(Field field ) {
        return !field.getName().equalsIgnoreCase(Constants.DATA_INCLUSAO) &&
                !field.getName().equalsIgnoreCase(Constants.DATA_ALTERACAO) &&
                !field.getName().equalsIgnoreCase(OperationsParam.ID);
    }

    public static boolean equalNullableFields(Object fieldA, Object fieldB) {
        if (fieldA == null && fieldB == null) {
            return true;
        }
        try {
            return Utils.equals(fieldA, fieldB);
        } catch (Exception e) {
            return false;
        }
    }

    public static <T extends AbstractEntity> T copyAllValuesWithoutId(T toCopyObject, T managedObject) {
        Class<? extends AbstractEntity> aClass = toCopyObject.getClass();

        List<Field> fields = ClassUtils.getFieldsAsList(aClass);

        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase(OperationsParam.ID)) {
                try {
                    if (field.getName().equalsIgnoreCase(Constants.DATA_ALTERACAO)) {
                        Method setDataAlteracao = ClassUtils.getSetterMethod(field.getName(), aClass);
                        setDataAlteracao.invoke(managedObject, DateUtils.getDate());
                        continue;
                    }
                    Method getterMethod = ClassUtils.getGetterMethod(field.getName(), aClass);

                    Method setterMethod = ClassUtils.getSetterMethod(field.getName(), aClass);

                    Object toChangeValue = getterMethod.invoke(toCopyObject);

                    Class<?> type = field.getType();

                    Object cast = convertObject(toChangeValue, type);

                    if (managedObject.getClass().isAssignableFrom(aClass)) {
                        if (Utils.isNotEmpty(toChangeValue) && cast.getClass().isAssignableFrom(type)) {
                            setterMethod.invoke(managedObject, cast);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return managedObject;
    }

    public static <C> C convertObject(Object toChangeValue, Class<?> targetType) {
        if (toChangeValue == null) {
            return null;
        }

        if (targetType.isAssignableFrom(toChangeValue.getClass())) {
            return (C) toChangeValue;
        }
        throw new UnsupportedOperationException("Cannot convert object of type " +
                toChangeValue.getClass() + " to type " + targetType);
    }
}
