package com.gr4.service;

import com.gr4.model.Tarea;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Servicio para gestionar la vista de calendario de tareas.
 * Permite organizar y consultar tareas por fechas específicas.
 */
public class CalendarioService {

    /**
     * Obtiene todas las tareas programadas para un día específico.
     * 
     * @param tareas Lista completa de tareas
     * @param fecha Fecha a consultar
     * @return Lista de tareas para ese día
     */
    public List<Tarea> obtenerTareasPorDia(List<Tarea> tareas, LocalDate fecha) {
        validarParametrosNoNulos(tareas, fecha);
        return filtrarTareasPorFecha(tareas, fecha)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las tareas de una semana específica.
     * 
     * @param tareas Lista completa de tareas
     * @param fechaInicio Primer día de la semana
     * @param fechaFin Último día de la semana
     * @return Lista de tareas en ese rango
     */
    public List<Tarea> obtenerTareasPorSemana(List<Tarea> tareas, LocalDate fechaInicio, LocalDate fechaFin) {
        validarParametrosNoNulos(tareas, fechaInicio, fechaFin);

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha fin");
        }
        
        return tareas.stream()
                .filter(tarea -> tarea.getFechaVencimiento() != null)
                .filter(tarea -> !tarea.getFechaVencimiento().isBefore(fechaInicio))
                .filter(tarea -> !tarea.getFechaVencimiento().isAfter(fechaFin))
                .collect(Collectors.toList());
    }

    /**
     * Agrupa las tareas por fecha de vencimiento.
     * 
     * @param tareas Lista de tareas a agrupar
     * @return Mapa con fecha como clave y lista de tareas como valor
     */
    public Map<LocalDate, List<Tarea>> agruparTareasPorFecha(List<Tarea> tareas) {
        validarParametrosNoNulos(tareas);

        return tareas.stream()
                .filter(tarea -> tarea.getFechaVencimiento() != null)
                .collect(Collectors.groupingBy(Tarea::getFechaVencimiento));
    }

    /**
     * Detecta si hay conflictos de tareas (múltiples tareas de alta prioridad en el mismo día).
     * 
     * @param tareas Lista de tareas
     * @param fecha Fecha a verificar
     * @return true si hay más de una tarea de alta prioridad ese día
     */
    public boolean detectarConflictos(List<Tarea> tareas, LocalDate fecha) {
        validarParametrosNoNulos(tareas, fecha);

        long tareasAltaPrioridad = filtrarTareasPorFecha(tareas, fecha)
                .filter(tarea -> Tarea.PRIORIDAD_ALTA.equals(tarea.getPrioridad()))
                .count();
        return tareasAltaPrioridad > 1;
    }

    /**
     * Cuenta el número de tareas pendientes en un día específico.
     * 
     * @param tareas Lista de tareas
     * @param fecha Fecha a consultar
     * @return Número de tareas pendientes
     */
    public long contarTareasPendientes(List<Tarea> tareas, LocalDate fecha) {
        validarParametrosNoNulos(tareas, fecha);

        return tareas.stream()
                .filter(tarea -> tarea.getFechaVencimiento() != null)
                .filter(tarea -> tarea.getFechaVencimiento().equals(fecha))
                .filter(tarea -> Tarea.ESTADO_PENDIENTE.equals(tarea.getEstado()))
                .count();
    }

    /**
     * Valida que los parámetros proporcionados no sean nulos.
     *
     * @param params Lista de parámetros a validar
     * @throws IllegalArgumentException si algún parámetro es nulo
     */
    private void validarParametrosNoNulos(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
            }
        }
    }


    /**
     * Filtra una lista de tareas por una fecha específica.
     *
     * @param tareas Lista de tareas a filtrar
     * @param fecha Fecha de referencia
     * @return Stream de tareas cuyo vencimiento coincide con la fecha
     */
    private Stream<Tarea> filtrarTareasPorFecha(List<Tarea> tareas, LocalDate fecha) {
        if (tareas == null || fecha == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        return tareas.stream()
                .filter(tarea -> tarea.getFechaVencimiento() != null)
                .filter(tarea -> tarea.getFechaVencimiento().equals(fecha));
    }


}
