package com.gr4.repository;

import com.gr4.model.Proyecto;
import com.gr4.service.ProyectoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProyectoRepositoryTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_ProyectoCuando_GuardarProyectoEntonces_DebeGuardarCorrectamente() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 1", "Descripción del proyecto 1");
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

        // Act
        Proyecto resultado = proyectoService.guardarProyecto(proyecto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Proyecto 1", resultado.getTitulo());
        verify(proyectoRepository, times(1)).save(proyecto);
    }

    @Test
    public void given_IdCuando_BuscarProyectoEntonces_DebeRetornarProyecto() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 2", "Descripción del proyecto 2");
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        // Act
        Optional<Proyecto> resultado = proyectoService.buscarProyectoPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Proyecto 2", resultado.get().getTitulo());
    }

    @Test
    public void given_ListaDeProyectosCuando_ObtenerProyectosEntonces_DebeRetornarLista() {
        // Arrange
        List<Proyecto> listaProyectos = new ArrayList<>();
        listaProyectos.add(new Proyecto("Proyecto 3", "Descripción del proyecto 3"));
        listaProyectos.add(new Proyecto("Proyecto 4", "Descripción del proyecto 4"));
        when(proyectoRepository.findAll()).thenReturn(listaProyectos);

        // Act
        List<Proyecto> resultados = proyectoService.obtenerProyectos();

        // Assert
        assertEquals(2, resultados.size());
        assertEquals("Proyecto 3", resultados.get(0).getTitulo());
        verify(proyectoRepository, times(1)).findAll();
    }

    @Test
    public void given_IdCuando_EliminarProyectoEntonces_DebeEliminarCorrectamente() {
        // Arrange
        Long idProyecto = 1L;
        when(proyectoRepository.deleteById(idProyecto)).thenReturn(true); // Simulando el retorno de true

        // Act
        boolean resultado = proyectoService.eliminarProyecto(idProyecto);

        // Assert
        assertTrue(resultado, "El proyecto debería eliminarse correctamente");
        verify(proyectoRepository, times(1)).deleteById(idProyecto);
    }
}