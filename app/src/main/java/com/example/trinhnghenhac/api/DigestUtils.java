package com.example.trinhnghenhac.api;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Getting `sig` parameter using this class
 */
public class DigestUtils {
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String getSha256(String strHash) {
        try {
            byte[] bytesInput = strHash.getBytes(StandardCharsets.UTF_8);
            byte[] bytesOutput = MessageDigest.getInstance("SHA-256").digest(bytesInput);
            return byteArrayToHexString(bytesOutput);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String getHmacSha512(String str, String key) {
        try {
            byte[] bytesInput = str.getBytes(StandardCharsets.UTF_8);
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            Mac hmac = Mac.getInstance("HmacSHA512");
            hmac.init(new SecretKeySpec(keyBytes, "HmacSHA512"));
            byte[] bytesOutput = hmac.doFinal(bytesInput);
            return byteArrayToHexString(bytesOutput);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
    }
}