package com.gr4.auth.model;

/**
 * Clase que representa un Usuario en el sistema.
 * Cumple con el SRP (Single Responsibility Principle) - 
 * Solo se encarga de mantener los datos del usuario.
 */
public class Usuario {
    private String nombre;
    private String correo;
    private String contraseña;
    private boolean activo;

    public Usuario(String nombre, String correo, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.activo = true;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}