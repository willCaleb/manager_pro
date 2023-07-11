package com.project.pro.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.Normalizer;

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

    public static String removeSpaces(String field) {
        return field.replaceAll(" ", "");
    }

    public static String normalize(String field) {
        field.replaceAll("^\\p{L}\\p{N}", "").replaceAll("-", "");
//        StringUtils.
        return Normalizer.normalize(field, Normalizer.Form.NFD);
    }
}
