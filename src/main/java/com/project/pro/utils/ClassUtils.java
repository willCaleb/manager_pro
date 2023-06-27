package com.project.pro.utils;

import com.project.pro.exception.CustomException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class ClassUtils {

    public static Method getMethod(String name, Class<?> clazz) {
        Method method;
        try {
            method = clazz.getMethod("get" + StringUtils.capitalize(name));
        } catch (NoSuchMethodException e) {
            throw new CustomException(e.getMessage());
        }
        return method;
    }

    public static Method setMethod(String name, Class<?> clazz) {
        Method method;
        try {
            method = clazz.getMethod("set" + StringUtils.capitalize(name));
        } catch (NoSuchMethodException e) {
            throw new CustomException(e.getMessage());
        }
        return method;
    }

    public static Method[] getSetterMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] returnMethods = new Method[declaredMethods.length];
        for (int i = 0; i < declaredMethods.length; i++) {
            if (declaredMethods[i].getName().startsWith("set")) {
                returnMethods[i] = declaredMethods[i];
            }
        }
        return returnMethods;
    }

    public static Method[] getGetterMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] returnMethods = {};
        for (int i = 0; i < declaredMethods.length; i++) {
            if (declaredMethods[i].getName().startsWith("get")) {
                returnMethods[i] = declaredMethods[i];
            }
        }
        return returnMethods;
    }
}
