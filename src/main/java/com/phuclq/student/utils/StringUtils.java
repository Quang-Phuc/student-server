package com.phuclq.student.utils;

public class StringUtils {
    public static boolean isStringNotNullAndHasValue(String value) {
        return isStringNotNull(value) && isStringHasValue(value);
    }

    public static boolean isStringNotNull(String value) {
        return value != null;
    }

    public static boolean isStringHasValue(String value) {
        return !value.isEmpty();
    }
}
