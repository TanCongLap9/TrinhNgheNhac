package com.example.trinhnghenhac.utils;

import java.text.Normalizer;

public class SearchUtils {
    public static String removeAccents(String str) {
        return Normalizer.normalize(str.toLowerCase(), Normalizer.Form.NFKD)
                .replaceAll("[\\u0300-\\u036f]", "");
    }

    public static boolean matches(String str, String subStr) {
        return str.toLowerCase().contains(subStr.toLowerCase());
    }

    public static boolean matchesIgnoreAccents(String str, String subStr) {
        return removeAccents(str).toLowerCase()
                .contains(removeAccents(subStr).toLowerCase());
    }
}
