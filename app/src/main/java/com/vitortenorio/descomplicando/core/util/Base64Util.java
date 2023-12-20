package com.vitortenorio.descomplicando.core.util;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Util {
    public static Integer convertBase64ToIntegerWithReverse(final String base64) {
        final var number = convertBase64ToString(base64);
        return Integer.parseInt(StringUtil.reverseString(number));
    }

    public static String convertBase64ToString(final String base64) {
        final byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }
}
