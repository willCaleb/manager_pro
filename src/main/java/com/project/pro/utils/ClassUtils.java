package com.project.pro.utils;

import com.project.pro.exception.CustomException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClassUtils {

//    public static Method getGetterMethod(String name, Class<?> clazz) {
//        Method method;
//        try {
//            method = clazz.getMethod("get" + StringUtils.capitalize(name));
//        } catch (NoSuchMethodException e) {
//            throw new CustomException(e.getMessage());
//        }
//        return method;
//    }
//
//    public static Method getSetterMethod(String name, Class<?> clazz) {
//        List<Method> setterMethodList = getSetterMethodList(clazz);
//        final String finalName = "set" + StringUtils.capitalize(name);
//        return setterMethodList.stream().filter(method -> finalName.equalsIgnoreCase(method.getName())).findFirst().orElse(null);


//        try {
//
//            method = clazz.getMethod(name);
//            return method;
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    //    public static Method[] getSetterMethods(Class<?> clazz) {
//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        Method[] returnMethods = new Method[declaredMethods.length];
//        for (int i = 0; i < declaredMethods.length; i++) {
//            if (declaredMethods[i].getName().startsWith("set")) {
//                returnMethods[i] = declaredMethods[i];
//            }
//        }
//        return returnMethods;
//    }
//
//    public static Method[] getGetterMethods(Class<?> clazz) {
//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        Method[] returnMethods = {};
//        for (int i = 0; i < declaredMethods.length; i++) {
//            if (declaredMethods[i].getName().startsWith("get")) {
//                returnMethods[i] = declaredMethods[i];
//            }
//        }
//        return returnMethods;
//    }
    public static Method getGetterMethod(String fieldName, Class<?> javaBeanClass) {
        return Stream.of(javaBeanClass.getDeclaredMethods())
                .filter(method -> isGetterMethod(method, fieldName))
                .findFirst()
                .orElse(null);
    }

    private static boolean isGetterMethod(Method method, String name) {
        return method.getParameterCount() == 0
                && !Modifier.isStatic(method.getModifiers())
                && (method.getName().equalsIgnoreCase("get" + name) || method.getName().equalsIgnoreCase("is" + name));
    }

    public static Method getSetterMethod(String fieldName, Class<?> clazz) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter(method -> isSetterMethod(method, fieldName))
                .findFirst()
                .orElse(null);
    }

    public static boolean isSetterMethod(Method method, String name) {
        return method.getName().startsWith("set") && method.getName().equalsIgnoreCase("set".concat(name));
    }

    public static <T> T getInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CustomException("Não foi possível criar nova instância de ", clazz.getName());
    }
//
//    public static List<Method> getGetterMethodList(Class<?> clazz) {
//        return Arrays.asList(getGetterMethods(clazz));
//    }
//
//    public static List<Method> getSetterMethodList(Class<?> clazz) {
//        return Arrays.asList(getSetterMethods(clazz));
//    }


}
