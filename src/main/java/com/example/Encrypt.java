package com.example;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {
    public static byte[] ivBytes = new byte[16]; // IV zeroed per AES (128 bit block size)

    public static void main(String[] args) {
        try {
            String data = "sellerid=Company&returnsreference=0003&contents_value=101&contents_description=clothes&fullname=Joe%20Bloggs&company=Company&email=joe.bloggs@gmail.com&email_confirm=joe.bloggs@gmail.com&telephone=123&address_line_1=1&city=Acampo&postcode=952200001&country_code=US&weight=1&height=2&length=3&width=4";
            String key = "b1U995YFbERWuzO72GmKSBWpACVIb3L9"; // Chiave di 256 bit

            String encoded = encode(data, key);
            System.out.println("Encoded: " + encoded);
            String decoded = decode(encoded, key);
            System.out.println("Decoded: " + decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encode(String data, String key) throws Exception {
        byte[] textBytes = data.getBytes("UTF-8");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        byte[] encrypted = cipher.doFinal(textBytes);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decode(String data, String key) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(data);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        byte[] decrypted = cipher.doFinal(encryptedBytes);
        return new String(decrypted, "UTF-8");
    }
}
