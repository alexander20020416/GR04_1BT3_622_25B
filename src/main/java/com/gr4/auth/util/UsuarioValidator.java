package com.gr4.auth.util;

import com.gr4.auth.model.Usuario;

/**
 * Clase responsable de validar datos de usuarios.
 * Cumple con el SRP - Solo se encarga de validaciones.
 */
public class UsuarioValidator {

    /**
     * Valida que el formato del correo electrónico sea válido
     * @param correo el correo a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public boolean validarFormatoCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return correo.contains("@") && 
               correo.indexOf("@") > 0 && 
               correo.indexOf("@") < correo.length() - 1;
    }

    /**
     * Valida que la contraseña cumpla con la longitud mínima requerida
     * @param contraseña la contraseña a validar
     * @return true si la contraseña tiene 8 o más caracteres, false en caso contrario
     */
    public boolean validarLongitudContraseña(String contraseña) {
        return contraseña != null && contraseña.length() >= 8;
    }

    /**
     * Valida que el nombre tenga una longitud entre 3 y 50 caracteres
     * @param nombre el nombre a validar
     * @return true si el nombre cumple con la longitud requerida, false en caso contrario
     */
    public boolean validarLongitudNombre(String nombre) {
        return nombre != null && 
               nombre.length() >= 3 && 
               nombre.length() <= 50;
    }

    /**
     * Valida todos los datos de un usuario
     * @param usuario el usuario a validar
     * @return true si todos los datos son válidos, false en caso contrario
     */
    public boolean validarUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }
        return validarLongitudNombre(usuario.getNombre()) &&
               validarFormatoCorreo(usuario.getCorreo()) &&
               validarLongitudContraseña(usuario.getContraseña());
    }
}
