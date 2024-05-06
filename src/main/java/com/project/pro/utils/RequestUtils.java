package com.project.pro.utils;

import com.project.pro.exception.CustomException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtils {

    public static String getSubdomain(String url) {
        String subdominio = url.replaceAll("https://", "")
            .replaceAll(".com", "")
            .replaceAll(".br", "")
            .replaceAll("www.", "");

        return subdominio;
    }

    public static boolean containsSpecialCharacters(String input) {
        // Defina a expressão regular para corresponder a caracteres especiais
        String regex = "[/\\.&%#@]";

        // Crie um padrão para a expressão regular
        Pattern pattern = Pattern.compile(regex);

        // Crie um matcher para a entrada
        Matcher matcher = pattern.matcher(input);

        // Verifique se há correspondências
        return matcher.find();
    }

    public static Character getFirstSpecialCharacter(String input) {
        String regex = "[/\\.&%#@]";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            int start = matcher.start();
            char c = input.charAt(start);
            return c;
        } else {
            return null;
        }
    }

}
