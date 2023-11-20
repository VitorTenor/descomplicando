package com.vitortenorio.descomplicando.core.util;

import org.springframework.stereotype.Component;

@Component
public class Base64Util {

    public static Integer convertBase64ToIntegerWithReverse(String base64) {
        String number = convertBase64ToString(base64);
        return Integer.parseInt(StringUtil.reverseString(number));
    }

    public static String convertBase64ToString(String base64) {
        byte[] decodedBytes  = java.util.Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }
}
