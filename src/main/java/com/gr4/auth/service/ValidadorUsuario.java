package com.gr4.auth.service;

import com.gr4.auth.model.Usuario;

public class ValidadorUsuario {

    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public static boolean esCorreoValido(String correo) {
        if (correo == null || correo.trim().isEmpty()) return false;
        return correo.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean esContrasenaValida(String contrasena) {
        return contrasena != null && contrasena.length() >= 6;
    }

    public static boolean validarUsuario(String nombre, String correo, String contrasena) {
        return esNombreValido(nombre) && esCorreoValido(correo) && esContrasenaValida(contrasena);
    }

}
