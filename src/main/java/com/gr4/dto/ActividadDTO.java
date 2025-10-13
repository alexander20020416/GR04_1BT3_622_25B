package com.gr4.dto;

import java.time.LocalDate;

/**
 * DTO para transferir datos de Actividad entre capas
 * Basado en el diagrama de clases y diagramas de secuencia
 */
public class ActividadDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaEntrega; // String para facilitar recepción desde formulario
    private String estado;
    private String prioridad;

    // Constructores
    public ActividadDTO() {
    }

    public ActividadDTO(String titulo, String descripcion, String fechaEntrega) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    // Método de validación según diagrama de secuencia
    public boolean validar() {
        if (titulo == null || titulo.trim().isEmpty()) {
            return false;
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return false;
        }
        if (fechaEntrega == null || fechaEntrega.trim().isEmpty()) {
            return false;
        }
        // Validar formato de fecha
        try {
            LocalDate.parse(fechaEntrega);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para convertir la fecha String a LocalDate
    public LocalDate getFechaEntregaAsLocalDate() {
        try {
            return LocalDate.parse(fechaEntrega);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "ActividadDTO{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaEntrega='" + fechaEntrega + '\'' +
                ", prioridad='" + prioridad + '\'' +
                '}';
    }
}