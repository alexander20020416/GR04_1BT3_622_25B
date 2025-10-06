package com.gr4.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que representa una Tarea dentro de una Actividad
 * Basado en el diagrama de clases del Incremento 1
 */
@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_vencimiento", columnDefinition = "DATE")
    private LocalDate fechaVencimiento;

    @Column(length = 50)
    private String estado; // Pendiente, En Progreso, Completada

    @Column(length = 20)
    private String prioridad; // Alta, Media, Baja

    // Relación muchos a uno con Actividad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    // Constructores
    public Tarea() {
        this.estado = "Pendiente";
        this.prioridad = "Media";
    }

    public Tarea(String titulo, String descripcion, LocalDate fechaVencimiento) {
        this();
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

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
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

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaVencimiento=" + fechaVencimiento +
                ", estado='" + estado + '\'' +
                ", prioridad='" + prioridad + '\'' +
                '}';
    }
}