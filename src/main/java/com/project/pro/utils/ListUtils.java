package com.project.pro.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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

    public static <T> List<T> toList(T... args) {
        return Arrays.asList(args);
    }

    public static int size(Collection<?> list) {
        return isNullOrEmpty(list) ? 0 : list.size();
    }

    public static Stream stream(Collection<String> list) {
        return list.stream();
    }
}
