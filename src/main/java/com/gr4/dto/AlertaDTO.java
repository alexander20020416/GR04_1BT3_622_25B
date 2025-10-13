package com.gr4.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO para transferir datos de Alerta entre capas
 * Basado en el diagrama de clases del Incremento 2
 */
public class AlertaDTO {

    private Long id;
    private String mensaje;
    private String fechaAlerta; // String para facilitar recepción desde formulario
    private String tipo;
    private Long actividadId; // ID de la actividad asociada

    // Constructores
    public AlertaDTO() {
    }

    public AlertaDTO(String mensaje, String fechaAlerta, Long actividadId) {
        this.mensaje = mensaje;
        this.fechaAlerta = fechaAlerta;
        this.actividadId = actividadId;
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

    public String getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(String fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getActividadId() {
        return actividadId;
    }

    public void setActividadId(Long actividadId) {
        this.actividadId = actividadId;
    }

    // Método de validación según diagrama de secuencia
    public boolean validar() {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return false;
        }
        if (fechaAlerta == null || fechaAlerta.trim().isEmpty()) {
            return false;
        }
        if (actividadId == null) {
            return false;
        }
        // Validar formato de fecha y que sea futura
        try {
            LocalDateTime fecha = getFechaAlertaAsLocalDateTime();
            return fecha != null && fecha.isAfter(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    // Método para convertir la fecha String a LocalDateTime
    public LocalDateTime getFechaAlertaAsLocalDateTime() {
        try {
            // Formato esperado: "yyyy-MM-dd'T'HH:mm" (input datetime-local de HTML5)
            return LocalDateTime.parse(fechaAlerta);
        } catch (Exception e) {
            // Intento alternativo si viene solo la fecha
            try {
                return LocalDateTime.parse(fechaAlerta + "T00:00");
            } catch (Exception ex) {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "AlertaDTO{" +
                "mensaje='" + mensaje + '\'' +
                ", fechaAlerta='" + fechaAlerta + '\'' +
                ", tipo='" + tipo + '\'' +
                ", actividadId=" + actividadId +
                '}';
    }
}
