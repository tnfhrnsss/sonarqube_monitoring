package com.lzdk.monitoring.sonarqube.utils;

public class CamelcaseUtil {
    public static String toCamelName(String name) {
        boolean shouldConvertNextCharToLower = true;
        StringBuilder builder = new StringBuilder(name.length());

        for(int i = 0; i < name.length(); ++i) {
            char currentChar = name.charAt(i);
            if (currentChar == '_') {
                shouldConvertNextCharToLower = false;
            } else if (shouldConvertNextCharToLower) {
                builder.append(Character.toLowerCase(currentChar));
            } else {
                builder.append(Character.toUpperCase(currentChar));
                shouldConvertNextCharToLower = true;
            }
        }

        return builder.toString();
    }
}
