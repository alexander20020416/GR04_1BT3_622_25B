package com.gr4.repository;

import com.gr4.model.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository {
    Proyecto save(Proyecto proyecto);
    Optional<Proyecto> findById(Long id);
    List<Proyecto> findAll();
    boolean deleteById(Long id);
}