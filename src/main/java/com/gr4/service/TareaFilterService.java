package com.gr4.service;

import com.gr4.model.Tarea;
import com.gr4.repository.TareaRepository;
import java.util.List;

/**
 * Servicio para centralizar la lógica de filtrado de tareas
 */
public class TareaFilterService {

    private TareaRepository tareaRepository;

    public TareaFilterService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    /**
     * Obtiene tareas según el filtro especificado
     * @param filtro tipo de filtro (null, "todos", o un estado específico)
     * @return Lista de tareas filtradas
     */
    public List<Tarea> obtenerTareasFiltradas(String filtro) {
        if (filtro == null || filtro.isEmpty() || filtro.equals("todos")) {
            return tareaRepository.findAll();
        } else {
            return tareaRepository.findByEstado(filtro);
        }
    }

    /**
     * Obtiene el mensaje a mostrar cuando no hay tareas
     * @param filtro el filtro aplicado
     * @return mensaje descriptivo
     */
    public String obtenerMensajeVacio(String filtro) {
        String mensaje = "No existen tareas";

        if (filtro != null && !filtro.isEmpty() && !filtro.equals("todos")) {
            mensaje += " con estado: " + filtro;
        }

        return mensaje;
    }
}








