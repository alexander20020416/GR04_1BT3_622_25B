package com.gr4.strategy;

import com.gr4.model.Tarea;
import java.util.List;

/**
 * Interface del Patrón Strategy para ordenamiento de tareas
 * Basado en el diagrama de clases del Incremento 1
 * Permite diferentes estrategias de ordenamiento sin modificar el controlador
 */
public interface OrdenStrategy {

    /**
     * Ordena una lista de tareas según la estrategia implementada
     * @param tareas Lista de tareas a ordenar
     * @return Lista de tareas ordenada
     */
    List<Tarea> ordenar(List<Tarea> tareas);

    /**
     * Devuelve el nombre de la estrategia
     * @return Nombre descriptivo de la estrategia
     */
    String getNombre();
}