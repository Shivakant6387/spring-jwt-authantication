package com.example.spring_boot_role_base_authentication.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.util.Base64;

public class HmacSha256Signer {

    public static byte[] sign(String data, SecretKey key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            return mac.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error signing JWT", e);
        }
    }

    public static String signToBase64Url(String data, SecretKey key) {
        byte[] signature = sign(data, key);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
    }
}
