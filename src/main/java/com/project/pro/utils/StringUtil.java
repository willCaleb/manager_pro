package com.project.pro.utils;

import java.text.MessageFormat;

public class StringUtil {

    public static boolean isNullOrEmpty(String field) {
        return field == null || field.equals("") || field.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String field) {
        return !isNullOrEmpty(field);
    }

    public static String notNullable(String field) {
        return Utils.isNotEmpty(field) ? field : "";
    }

    public static String formatMessage(String msg, Object... arguments) {
        return MessageFormat.format(msg, arguments);
    }
}
