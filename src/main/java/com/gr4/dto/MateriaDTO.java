package com.gr4.dto;

import java.time.LocalDate;

/**
 * DTO para transferir datos de Tarea entre capas
 * Basado en el diagrama de clases y diagramas de secuencia
 */

public class MateriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;

    public MateriaDTO(){
    }

    public MateriaDTO(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /* Getters y Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
