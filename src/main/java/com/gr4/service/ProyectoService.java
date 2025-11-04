package com.gr4.service;

import com.gr4.model.Proyecto;
import com.gr4.model.Tarea;
import com.gr4.repository.ProyectoRepository;
import com.gr4.repository.TareaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final TareaRepository tareaRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, TareaRepository tareaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.tareaRepository = tareaRepository;
    }

    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Optional<Proyecto> buscarProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public List<Proyecto> obtenerProyectos() {
        return proyectoRepository.findAll();
    }

    public boolean eliminarProyecto(Long id) {
        return proyectoRepository.deleteById(id);
    }

    public void asociarTarea(Long idProyecto, Long idTarea) {
        Optional<Proyecto> proyectoOpt = buscarProyectoPorId(idProyecto);
        Optional<Tarea> tareaOpt = tareaRepository.findById(idTarea);

        if (proyectoOpt.isPresent() && tareaOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Tarea tarea = tareaOpt.get();
            proyecto.agregarTarea(tarea);
            guardarProyecto(proyecto);
        } else {
            throw new RuntimeException("Proyecto o tarea no encontrados");
        }
    }

    public void crearTareaDentroDeProyecto(Long idProyecto, String titulo, String descripcion, LocalDate fechaVencimiento) {
        Optional<Proyecto> proyectoOpt = buscarProyectoPorId(idProyecto);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Tarea nuevaTarea = new Tarea(titulo, descripcion, fechaVencimiento);
            proyecto.agregarTarea(nuevaTarea);
            guardarProyecto(proyecto);
        } else {
            throw new RuntimeException("Proyecto no encontrado");
        }
    }
}