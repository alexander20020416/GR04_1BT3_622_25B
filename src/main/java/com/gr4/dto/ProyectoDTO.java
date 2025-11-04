package com.gr4.dto;

public class ProyectoDTO {

    private Long id;
    private String titulo;
    private String descripcion;

    // Constructores
    public ProyectoDTO() {
    }

    public ProyectoDTO(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
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
}