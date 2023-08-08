package com.project.pro.utils;

import java.util.Map;

public class MapUtils {

    public static boolean isNullOrEmpty(Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotNullOrNotEmpty(Map<?,?> map) {
        return !isNullOrEmpty(map);
    }

}
