package com.project.pro.converter;

import java.lang.reflect.Constructor;

public class Converter{


    public static Object converter(String valor, Class<?> clazz) {
        try {
            Constructor constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(valor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
