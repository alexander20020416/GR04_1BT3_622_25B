package com.gr4.repository;

import com.gr4.model.Actividad;
import java.util.List;
import java.util.Optional;

/**
 * Interface Repository para Actividad
 * Patrón Repository - Define las operaciones de persistencia
 * Basado en el diagrama de clases del Incremento 1
 */
public interface ActividadRepository {

    /**
     * Guarda una nueva actividad o actualiza una existente
     * @param actividad Actividad a guardar
     * @return Actividad guardada
     */
    Actividad save(Actividad actividad);

    /**
     * Busca una actividad por su ID
     * @param id ID de la actividad
     * @return Optional con la actividad si existe
     */
    Optional<Actividad> findById(Long id);

    /**
     * Obtiene todas las actividades
     * @return Lista de todas las actividades
     */
    List<Actividad> findAll();

    /**
     * Busca actividades por estado
     * @param estado Estado de las actividades (Pendiente, En Progreso, Completada)
     * @return Lista de actividades con ese estado
     */
    List<Actividad> findByEstado(String estado);

    /**
     * Busca actividades por prioridad
     * @param prioridad Prioridad de las actividades (Alta, Media, Baja)
     * @return Lista de actividades con esa prioridad
     */
    List<Actividad> findByPrioridad(String prioridad);

    /**
     * Elimina una actividad por su ID
     * @param id ID de la actividad a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteById(Long id);

    /**
     * Cuenta el total de actividades
     * @return Número total de actividades
     */
    long count();
}