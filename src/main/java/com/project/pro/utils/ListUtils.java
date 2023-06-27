package com.project.pro.utils;

import java.util.ArrayList;
import java.util.Collection;

public class ListUtils {

    public static boolean isNullOrEmpty (Collection<?> list) {
        return  list.size() == 0 || Utils.isEmpty(list);
    }

    public static boolean isNotNullOrEmpty(Collection<?> list) {
        return !isNullOrEmpty(list);
    }

    public static Collection<?> ofNullable(Collection<?> list) {
        return isNotNullOrEmpty(list) ? list : new ArrayList<>();
    }
}
