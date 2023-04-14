package com.group.p2_socializer.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils{
    private static final int SALT_LENGTH = 16;
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hexSalt = bytesToHex(salt);
        String hexHashedPassword = bytesToHex(hashedPassword);
        return hexSalt + hexHashedPassword;
    }

    public static boolean verifyPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        String hexSalt = hashedPassword.substring(0, SALT_LENGTH * 2);
        String hexHashedPassword = hashedPassword.substring(SALT_LENGTH * 2);
        // Split salt  from password, * 2 because hash is hex

        byte[] salt = hexToBytes(hexSalt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPasswordToCheck = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hexHashedPasswordToCheck = bytesToHex(hashedPasswordToCheck);
        return hexHashedPassword.equals(hexHashedPasswordToCheck);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        return bytes;
    }
}
