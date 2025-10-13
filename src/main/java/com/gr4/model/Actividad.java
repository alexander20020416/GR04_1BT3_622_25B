package com.gr4.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una Actividad Universitaria
 * Basado en el diagrama de clases del Incremento 1
 */
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_entrega", nullable = false, columnDefinition = "DATE")
    private LocalDate fechaEntrega;

    @Column(length = 50)
    private String estado; // Pendiente, En Progreso, Completada

    @Column(length = 20)
    private String prioridad; // Alta, Media, Baja

    // Relación uno a muchos con Tarea
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Tarea> tareas = new ArrayList<>();

    // Relación uno a muchos con Alerta
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Alerta> alertas = new ArrayList<>();

    // Constructores
    public Actividad() {
        this.estado = "Pendiente";
        this.prioridad = "Media";
    }

    public Actividad(String titulo, String descripcion, LocalDate fechaEntrega) {
        this();
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

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
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

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }

    // Métodos de utilidad para mantener la bidireccionalidad
    public void addTarea(Tarea tarea) {
        tareas.add(tarea);
        tarea.setActividad(this);
    }

    public void removeTarea(Tarea tarea) {
        tareas.remove(tarea);
        tarea.setActividad(null);
    }

    public void addAlerta(Alerta alerta) {
        alertas.add(alerta);
        alerta.setActividad(this);
    }

    public void removeAlerta(Alerta alerta) {
        alertas.remove(alerta);
        alerta.setActividad(null);
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", estado='" + estado + '\'' +
                ", prioridad='" + prioridad + '\'' +
                '}';
    }
}