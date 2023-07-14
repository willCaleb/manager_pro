package com.project.pro.utils;

import org.springframework.util.ObjectUtils;

public class Utils {

    public static boolean isEmpty(Object object) {

        return object == null || ObjectUtils.isEmpty(object);
    }

    public static boolean isNotEmpty(Object object) {

        return !isEmpty(object);
    }

    public static boolean equals(Object obj, Object compareTo) {

        return obj.equals(compareTo);
    }

    public static <T> T nvl(Object change, T changeTo) {
        if (isNotEmpty(change)) {
        return (T)change;
        }
        return changeTo;
    }
}
