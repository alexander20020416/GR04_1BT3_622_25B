package com.gr4.repository;

import com.gr4.model.Tarea;
import java.util.List;
import java.util.Optional;

/**
 * Interface Repository para Tarea
 * Patrón Repository - Define las operaciones de persistencia
 * Basado en el diagrama de clases del Incremento 1
 */
public interface TareaRepository {

    /**
     * Guarda una nueva tarea o actualiza una existente
     * @param tarea Tarea a guardar
     * @return Tarea guardada
     */
    Tarea save(Tarea tarea);

    /**
     * Busca una tarea por su ID
     * @param id ID de la tarea
     * @return Optional con la tarea si existe
     */
    Optional<Tarea> findById(Long id);

    /**
     * Obtiene todas las tareas
     * @return Lista de todas las tareas
     */
    List<Tarea> findAll();

    /**
     * Obtiene todas las tareas ordenadas por su ID (orden de inserción)
     * @return Lista de todas las tareas ordenadas por id
     */
    List<Tarea> findAllOrderById();

    /**
     * Busca tareas por estado
     * @param estado Estado de las tareas (Pendiente, En Progreso, Completada)
     * @return Lista de tareas con ese estado
     */
    List<Tarea> findByEstado(String estado);

    /**
     * Busca tareas por prioridad
     * @param prioridad Prioridad de las tareas (Alta, Media, Baja)
     * @return Lista de tareas con esa prioridad
     */
    List<Tarea> findByPrioridad(String prioridad);

    /**
     * Busca tareas por actividad
     * @param actividadId ID de la actividad
     * @return Lista de tareas de esa actividad
     */
    List<Tarea> findByActividadId(Long actividadId);

    /**
     * Elimina una tarea por su ID
     * @param id ID de la tarea a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteById(Long id);

    /**
     * Cuenta el total de tareas
     * @return Número total de tareas
     */
    long count();
}