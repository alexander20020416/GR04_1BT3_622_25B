package com.gr4.auth.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA para Usuario.
 * Cumple con el patrón Entity y está preparada para persistencia en H2.
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = {
    @UniqueConstraint(columnNames = "correo")
})
public class UsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;
    
    @Column(name = "contraseña", nullable = false, length = 255)
    private String contraseña;
    
    @Column(name = "activo", nullable = false)
    private boolean activo = true;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;
    
    // Constructor por defecto requerido por JPA
    public UsuarioEntity() {
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Constructor con parámetros
    public UsuarioEntity(String nombre, String correo, String contraseña) {
        this();
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.activo = true;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }
    
    /**
     * Convierte esta entidad JPA al modelo de dominio Usuario
     */
    public Usuario toUsuario() {
        return new Usuario(this.nombre, this.correo, this.contraseña);
    }
    
    /**
     * Crea una entidad JPA desde el modelo de dominio Usuario
     */
    public static UsuarioEntity fromUsuario(Usuario usuario) {
        return new UsuarioEntity(usuario.getNombre(), usuario.getCorreo(), usuario.getContraseña());
    }
    
    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", activo=" + activo +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioEntity)) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return correo != null ? correo.equals(that.correo) : that.correo == null;
    }
    
    @Override
    public int hashCode() {
        return correo != null ? correo.hashCode() : 0;
    }
}