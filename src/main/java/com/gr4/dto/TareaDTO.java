package com.gr4.dto;

import java.time.LocalDate;

/**
 * DTO para transferir datos de Tarea entre capas
 * Basado en el diagrama de clases y diagramas de secuencia
 */
public class TareaDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaVencimiento; // String para facilitar recepción desde formulario
    private String estado;
    private String prioridad;

    // Constructores
    public TareaDTO() {
    }

    public TareaDTO(String titulo, String descripcion, String fechaVencimiento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaVencimiento = fechaVencimiento;
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

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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
        // Validar campos obligatorios
        if (esCampoVacio(titulo) || esCampoVacio(descripcion) || esCampoVacio(fechaVencimiento)) {
            return false;
        }
        
        // Validar formato de fecha
        try {
            LocalDate.parse(fechaVencimiento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si un campo es nulo o está vacío
     */
    private boolean esCampoVacio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }

    // Método para convertir la fecha String a LocalDate
    public LocalDate getFechaVencimientoAsLocalDate() {
        try {
            return LocalDate.parse(fechaVencimiento);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", prioridad='" + prioridad + '\'' +
                '}';
    }
}
