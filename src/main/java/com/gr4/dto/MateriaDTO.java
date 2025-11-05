package com.gr4.dto;

import java.time.LocalDate;

/**
 * DTO para transferir datos de Tarea entre capas
 * Basado en el diagrama de clases y diagramas de secuencia
 */

public class MateriaDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private String color;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
