package com.vitortenorio.descomplicando.core.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    public static String reverseString(String input) {
        char[] charArray = input.toCharArray();
        int left = 0;
        int right = charArray.length - 1;

        while (left < right) {
            char temp = charArray[left];
            charArray[left] = charArray[right];
            charArray[right] = temp;
            left++;
            right--;
        }

        return new String(charArray);
    }

    public static String divideAndCleanWord(final String word) {
        final var words = divideWordByKey(word, "-");

        var cleanWord = new StringBuilder();
        for (String s : words) {
            cleanWord.append(cleanWord(s)).append(" ");
        }

        return cleanWord.toString().trim();
    }

    public static String[] divideWordByKey(String word, String key) {
        return word.split(key);
    }

    public static String cleanWord(String word) {
        return word.replaceAll("\\b\\w*\\d\\w*\\b", "");
    }
}

