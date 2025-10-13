package com.gr4.repository;

import com.gr4.model.Alerta;
import java.util.List;
import java.util.Optional;

/**
 * Interface Repository para Alerta
 * Patrón Repository - Define las operaciones de persistencia
 * Basado en el diagrama de clases del Incremento 2
 */
public interface AlertaRepository {

    /**
     * Guarda una nueva alerta o actualiza una existente
     * @param alerta Alerta a guardar
     * @return Alerta guardada
     */
    Alerta save(Alerta alerta);

    /**
     * Busca una alerta por su ID
     * @param id ID de la alerta
     * @return Optional con la alerta si existe
     */
    Optional<Alerta> findById(Long id);

    /**
     * Obtiene todas las alertas
     * @return Lista de todas las alertas
     */
    List<Alerta> findAll();

    /**
     * Busca alertas por actividad
     * @param actividadId ID de la actividad
     * @return Lista de alertas de esa actividad
     */
    List<Alerta> findByActividadId(Long actividadId);

    /**
     * Busca alertas activas
     * @return Lista de alertas activas
     */
    List<Alerta> findActivas();

    /**
     * Busca alertas por tipo
     * @param tipo Tipo de alerta (Recordatorio, Urgente, Informativa)
     * @return Lista de alertas de ese tipo
     */
    List<Alerta> findByTipo(String tipo);

    /**
     * Elimina una alerta por su ID
     * @param id ID de la alerta a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteById(Long id);

    /**
     * Cuenta el total de alertas
     * @return Número total de alertas
     */
    long count();
}