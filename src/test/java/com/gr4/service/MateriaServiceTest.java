package com.gr4.service;

import com.gr4.dto.MateriaDTO;
import com.gr4.model.Materia;
import com.gr4.repository.MateriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MateriaServiceTest {

    private MateriaRepository materiaRepository;
    private MateriaService materiaService;

    @BeforeEach
    void setUp() {

        materiaRepository = mock(MateriaRepository.class);
        materiaService = new MateriaService(materiaRepository);
    }

    @Test
    void givenNombreValido_whenCrearMateria_thenMateriaSeCreaCorrectamente() {
        MateriaDTO input = new MateriaDTO("Matemáticas", "Álgebra y geometría");
        Materia persisted = new Materia("Matemáticas", "Álgebra y geometría");
        setId(persisted, 1L);

        when(materiaRepository.save(any(Materia.class))).thenReturn(persisted);

        MateriaDTO resultado = materiaService.crearMateria(input);

        assertNotNull(resultado.getId());
        assertEquals("Matemáticas", resultado.getNombre());

        ArgumentCaptor<Materia> captor = ArgumentCaptor.forClass(Materia.class);
        verify(materiaRepository).save(captor.capture());
        assertEquals("Matemáticas", captor.getValue().getNombre());
    }

    @Test
    void givenNombreVacio_whenCrearMateria_thenNoDeberiaCrearseMateria() {
        MateriaDTO input = new MateriaDTO("", "algo");
        assertThrows(IllegalArgumentException.class, () -> materiaService.crearMateria(input));
        verifyNoInteractions(materiaRepository);
    }

    @Test
    void givenMateriasExistentes_whenListarMaterias_thenDeberiaRetornarDTOs() {
        Materia m1 = new Materia("Matemáticas", "Álgebra");
        setId(m1, 1L);
        Materia m2 = new Materia("Física", "Mecánica");
        setId(m2, 2L);

        when(materiaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<MateriaDTO> lista = materiaService.listarMaterias();

        assertEquals(2, lista.size());
        assertEquals("Matemáticas", lista.get(0).getNombre());
        assertEquals("Física", lista.get(1).getNombre());
    }

    /* Utilidad para setear ID en test */
    private void setId(Materia materia, Long id) {
        try {
            var field = Materia.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(materia, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
