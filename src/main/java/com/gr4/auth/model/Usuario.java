package com.gr4.auth.model;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private static Set<String> correosRegistrados = new HashSet<>();
    private String nombre;
    private String correo;
    private String contraseña;
    private boolean activo;

    public Usuario(String nombre, String correo, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.activo = true; // Usuario activo por defecto
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Valida que el formato del correo electrónico sea válido
     * @return true si el formato es válido, false en caso contrario
     */
    public boolean validarFormatoCorreo() {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return correo.contains("@") && 
               correo.indexOf("@") > 0 && 
               correo.indexOf("@") < correo.length() - 1;
    }

    /**
     * Valida que la contraseña cumpla con la longitud mínima requerida
     * @return true si la contraseña tiene 8 o más caracteres, false en caso contrario
     */
    public boolean validarLongitudContraseña() {
        return contraseña != null && contraseña.length() >= 8;
    }

    /**
     * Valida que el nombre tenga una longitud entre 3 y 50 caracteres
     * @return true si el nombre cumple con la longitud requerida, false en caso contrario
     */
    public boolean validarLongitudNombre() {
        return nombre != null && 
               nombre.length() >= 3 && 
               nombre.length() <= 50;
    }

    /**
     * Registra el correo del usuario si no existe uno igual
     * @return true si el correo se registró exitosamente, false si ya existe o es inválido
     */
    public boolean registrarCorreo() {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        String correoNormalizado = correo.trim().toLowerCase();
        if (correosRegistrados.contains(correoNormalizado)) {
            return false;
        }
        correosRegistrados.add(correoNormalizado);
        return true;
    }

    /**
     * Método para limpiar los correos registrados (usado en pruebas)
     */
    public static void limpiarCorreosRegistrados() {
        correosRegistrados.clear();
    }

    /**
     * Autentica al usuario verificando sus credenciales
     * @param correo el correo electrónico del usuario
     * @param contraseña la contraseña del usuario
     * @return true si las credenciales son correctas y el usuario está registrado y activo, false en caso contrario
     */
    public boolean autenticar(String correo, String contraseña) {
        if (correo == null || contraseña == null || 
            correo.trim().isEmpty() || contraseña.trim().isEmpty()) {
            return false;
        }
        
        if (this.correo == null || this.contraseña == null) {
            return false;
        }
        
        // Verificar que el usuario esté activo
        if (!this.activo) {
            return false;
        }

        String correoNormalizado = correo.trim().toLowerCase();
        String correoUsuario = this.correo.trim().toLowerCase();
        
        // Verificar que el correo esté registrado Y que coincida con este usuario Y que la contraseña coincida
        return correosRegistrados.contains(correoNormalizado) &&
               correoUsuario.equals(correoNormalizado) &&
               this.contraseña.equals(contraseña);
    }

    /**
     * Actualiza el nombre del usuario si cumple con las validaciones
     * @param nuevoNombre el nuevo nombre a establecer
     * @return true si el nombre se actualizó correctamente, false si no cumple validaciones
     */
    public boolean actualizarNombre(String nuevoNombre) {
        if (nuevoNombre == null || 
            nuevoNombre.length() < 3 || 
            nuevoNombre.length() > 50) {
            return false;
        }
        this.nombre = nuevoNombre;
        return true;
    }

    /**
     * Actualiza la contraseña del usuario si cumple con las validaciones
     * @param nuevaContraseña la nueva contraseña a establecer
     * @return true si la contraseña se actualizó correctamente, false si no cumple validaciones
     */
    public boolean actualizarContraseña(String nuevaContraseña) {
        if (nuevaContraseña == null || nuevaContraseña.length() < 8) {
            return false;
        }
        this.contraseña = nuevaContraseña;
        return true;
    }

    /**
     * Verifica si el usuario está activo
     * @return true si el usuario está activo, false si está inactivo
     */
    public boolean estaActivo() {
        return this.activo;
    }

    /**
     * Desactiva el usuario si está activo
     * @return true si el usuario se desactivó correctamente, false si ya estaba inactivo
     */
    public boolean desactivar() {
        if (!this.activo) {
            return false;
        }
        this.activo = false;
        return true;
    }

    /**
     * Activa el usuario si está inactivo
     * @return true si el usuario se activó correctamente, false si ya estaba activo
     */
    public boolean activar() {
        if (this.activo) {
            return false;
        }
        this.activo = true;
        return true;
    }
}