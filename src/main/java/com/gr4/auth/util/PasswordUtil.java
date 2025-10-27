package com.gr4.auth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para el manejo seguro de contraseñas.
 * Proporciona métodos para hashear y verificar contraseñas.
 */
public class PasswordUtil {
    
    private static final String HASH_ALGORITHM = "SHA-256";
    
    /**
     * Genera un hash de la contraseña usando SHA-256
     * @param password la contraseña en texto plano
     * @return el hash de la contraseña en Base64
     */
    public static String hashPassword(String password) {
        if (password == null) {
            return null;
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }
    
    /**
     * Verifica si una contraseña coincide con un hash
     * @param password la contraseña en texto plano
     * @param hashedPassword el hash almacenado
     * @return true si coinciden, false en caso contrario
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            return false;
        }
        
        String hashedInput = hashPassword(password);
        return hashedInput.equals(hashedPassword);
    }
    
    /**
     * Genera una contraseña temporal aleatoria
     * @param length longitud de la contraseña
     * @return contraseña aleatoria
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}
