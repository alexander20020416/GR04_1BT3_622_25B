package com.gr4.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tarea> tareas = new ArrayList<>();

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    // Constantes para estados de urgencia
    public static final String URGENCIA_VENCIDO = "VENCIDO";
    public static final String URGENCIA_URGENTE = "URGENTE";
    public static final String URGENCIA_PROXIMO = "PROXIMO";
    public static final String URGENCIA_A_TIEMPO = "A_TIEMPO";
    public static final String URGENCIA_SIN_FECHA = "SIN_FECHA";

    private static final int DIAS_URGENTE = 3;
    private static final int DIAS_PROXIMO = 7;

    // Constructor
    public Proyecto() {
    }

    public Proyecto(String titulo, String descripcion) {
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

    public List<Tarea> getTareas() {
        return tareas;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        tarea.setProyecto(this); // Establecer la relación bidireccional
    }

    public int calcularProgreso() {
        if (tareas.isEmpty()) {
            return 0;
        }
        int completadas = (int) tareas.stream()
                .filter(tarea -> Tarea.ESTADO_COMPLETADA.equals(tarea.getEstado()))
                .count();
        return (int) ((completadas / (double) tareas.size()) * 100);
    }

    /**
     * Calcula los días restantes hasta la fecha de vencimiento.
     * @return número de días restantes (negativo si ya venció)
     */
    private long calcularDiasRestantes() {
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
    }

    /**
     * Determina el estado de urgencia del proyecto según su fecha de vencimiento.
     * @return estado de urgencia (VENCIDO, URGENTE, PROXIMO, A_TIEMPO, SIN_FECHA)
     */
    public String determinarEstadoUrgencia() {
        if (fechaVencimiento == null) {
            return URGENCIA_SIN_FECHA;
        }
        
        long diasRestantes = calcularDiasRestantes();
        
        if (diasRestantes < 0) {
            return URGENCIA_VENCIDO;
        } else if (diasRestantes <= DIAS_URGENTE) {
            return URGENCIA_URGENTE;
        } else if (diasRestantes <= DIAS_PROXIMO) {
            return URGENCIA_PROXIMO;
        } else {
            return URGENCIA_A_TIEMPO;
        }
    }

    /**
     * Obtiene el color del indicador visual según el estado de urgencia.
     * @return color CSS (red, orange, green, gray)
     */
    public String getColorIndicador() {
        String estadoUrgencia = determinarEstadoUrgencia();
        
        switch (estadoUrgencia) {
            case URGENCIA_VENCIDO:
            case URGENCIA_URGENTE:
                return "red";
            case URGENCIA_PROXIMO:
                return "orange";
            case URGENCIA_A_TIEMPO:
                return "green";
            default:
                return "gray";
        }
    }
}