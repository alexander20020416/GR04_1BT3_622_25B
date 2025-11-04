package com.gr4.service;

import com.gr4.model.Proyecto;
import com.gr4.repository.ProyectoRepository;

import java.util.List;
import java.util.Optional;

public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
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
}