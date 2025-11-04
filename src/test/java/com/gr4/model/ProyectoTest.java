package com.gr4.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProyectoTest {

    @Test
    public void given_ProyectoVacio_when_CrearProyecto_then_NoDeberiaTenerDatos() {
        // Arrange
        Proyecto proyecto = new Proyecto();

        // Act & Assert
        assertNull(proyecto.getId(), "El ID del proyecto debería ser null");
        assertNull(proyecto.getTitulo(), "El título del proyecto debería ser null");
        assertNull(proyecto.getDescripcion(), "La descripción del proyecto debería ser null");
        assertEquals(0, proyecto.getTareas().size(), "El proyecto no debería tener tareas");
        assertEquals(0, proyecto.calcularProgreso(), "El progreso debería ser 0");
    }

    @Test
    public void given_DatosDeProyectoValidos_when_CrearProyecto_then_DebeCrearseCorrectamente() {
        // Arrange
        String titulo = "Proyecto 1";
        String descripcion = "Descripción del proyecto 1";
        Proyecto proyecto = new Proyecto(titulo, descripcion);

        // Act & Assert
        assertNotNull(proyecto, "El proyecto no debería ser null");
        assertEquals(titulo, proyecto.getTitulo(), "El título debería coincidir");
        assertEquals(descripcion, proyecto.getDescripcion(), "La descripción debería coincidir");
        assertEquals(0, proyecto.getTareas().size(), "El proyecto no debería tener tareas");
    }

    @Test
    public void given_ProyectoSinTareas_when_CalcularProgreso_then_DebeSerCero() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 2", "Descripción del proyecto 2");

        // Act
        int progreso = proyecto.calcularProgreso();

        // Assert
        assertEquals(0, progreso, "El progreso del proyecto sin tareas debería ser 0");
    }

}