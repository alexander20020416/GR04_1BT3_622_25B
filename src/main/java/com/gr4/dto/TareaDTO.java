package com.gr4.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object para Tarea
 * Usado para transferir información de tareas entre capas
 * Basado en el diagrama de clases del Incremento 1
 */
public class TareaDTO {
    
    private String descripcion;
    private String estado;
    private LocalDateTime fechaVencimiento;
    private int orden;

    // Constructor vacío
    public TareaDTO() {
    }

    // Constructor con todos los campos
    public TareaDTO(String descripcion, String estado, LocalDateTime fechaVencimiento, int orden) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaVencimiento = fechaVencimiento;
        this.orden = orden;
    }

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "TareaDTO{" +
                "descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaVencimiento=" + fechaVencimiento +
                ", orden=" + orden +
                '}';
    }
}
