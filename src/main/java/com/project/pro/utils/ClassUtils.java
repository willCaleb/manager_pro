package com.project.pro.utils;

import com.project.pro.exception.CustomException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ClassUtils {

    public static Method getGetterMethod(String name, Class<?> clazz) {
        Method method;
        try {
            method = clazz.getMethod("get" + StringUtils.capitalize(name));
        } catch (NoSuchMethodException e) {
            throw new CustomException(e.getMessage());
        }
        return method;
    }

    public static Method getSetterMethod(String name, Class<?> clazz) {
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

    public static List<Method> getGetterMethodList(Class<?> clazz) {
        return Arrays.asList(getGetterMethods(clazz));
    }

    public static List<Method> getSetterMethodList(Class<?> clazz) {
        return Arrays.asList(getSetterMethods(clazz));
    }


}
