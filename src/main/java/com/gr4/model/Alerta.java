package com.gr4.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa una Alerta asociada a una Tarea
 * Basado en el diagrama de clases del Incremento 2
 */
@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(name = "fecha_alerta", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaAlerta;

    @Column(length = 50)
    private String tipo; // Recordatorio, Urgente, Informativa

    @Column(name = "activa")
    private Boolean activa;

    // Relación muchos a uno con Tarea
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;

    // Constructores
    public Alerta() {
        this.activa = true;
        this.tipo = "Recordatorio";
    }

    public Alerta(String mensaje, LocalDateTime fechaAlerta) {
        this();
        this.mensaje = mensaje;
        this.fechaAlerta = fechaAlerta;
    }

    public Alerta(String mensaje, LocalDateTime fechaAlerta, String tipo) {
        this(mensaje, fechaAlerta);
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(LocalDateTime fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    // Métodos de negocio
    public void desactivar() {
        this.activa = false;
    }

    public void activar() {
        this.activa = true;
    }

    public boolean estaVencida() {
        return LocalDateTime.now().isAfter(this.fechaAlerta);
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", fechaAlerta=" + fechaAlerta +
                ", tipo='" + tipo + '\'' +
                ", activa=" + activa +
                '}';
    }
}