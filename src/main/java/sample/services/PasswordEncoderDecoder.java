package sample.services;

import java.nio.charset.StandardCharsets;

import org.apache.axis.encoding.Base64;

public class PasswordEncoderDecoder {
    public static String encode(String text) {
        return Base64.encode(text.getBytes(StandardCharsets.UTF_8));
    }
    public static String decode(String encodeText) {
        byte[] decodeBytes = Base64.decode(encodeText);
        String str = new String(decodeBytes, StandardCharsets.UTF_8);
        return str;    }
}
