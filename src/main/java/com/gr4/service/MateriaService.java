package com.gr4.service;

import com.gr4.dto.MateriaDTO;
import com.gr4.model.Materia;
import com.gr4.repository.MateriaRepository;
import com.gr4.repository.MateriaRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class MateriaService {

    private final MateriaRepository materiaRepository;

    /* Constructor */

    public MateriaService() {
        this.materiaRepository = new MateriaRepositoryImpl();
    }

    /* Constructor para Test (Mock)*/
    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public MateriaDTO crearMateria(MateriaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la materia es obligatorio");
        }
        Materia materia = new Materia(dto.getNombre(), dto.getDescripcion());
        materia = materiaRepository.save(materia);
        dto.setId(materia.getId());
        return dto;
    }

    public List<MateriaDTO> listarMaterias() {
        return materiaRepository.findAll().stream()
                .map(m -> {
                    MateriaDTO dto = new MateriaDTO(m.getNombre(), m.getDescripcion());
                    dto.setId(m.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}


