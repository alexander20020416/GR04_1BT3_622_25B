package com.gr4.repository;

import com.gr4.model.Materia;

import java.util.List;

public interface MateriaRepository {
    /**
     * Guarda una nueva materia actualiza una existente
     * @param materia Materia a guardar
     * @return Materia guardada
     */
    Materia save(Materia materia);

    List<Materia> findAll();

    Materia findById(Long id);

    /**
     * Elimina una materia por su ID
     * @param id ID de la materia a eliminar
     */
    void delete(Long id);
}
