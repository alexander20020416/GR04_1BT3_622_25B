package com.gr4.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;

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

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5})
    public void given_FechaProximaAVencer_when_CalcularProgresoConFechasDiferentes_then_DebeValidarCorrectamente(int dias) {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 4", "Descripción del proyecto 4");
        LocalDate fechaVencimiento = LocalDate.now().plusDays(dias);
        Tarea tarea = new Tarea("Tarea 1", "Descripción de tarea 1", fechaVencimiento);

        // Act
        proyecto.agregarTarea(tarea);

        // Assert
        if (dias == 0) {
            assertTrue(tarea.estaVencida(), "La tarea debería estar vencida hoy");
        } else {
            assertFalse(tarea.estaVencida(), "La tarea no debería estar vencida");
        }
    }

    @Test
    public void given_ProyectoConTareas_when_AgregarYRemoverTareas_then_DebeActualizarCorrectamente() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 5", "Descripción del proyecto 5");
        Tarea tarea1 = new Tarea("Tarea 1", "Descripción de tarea 1", LocalDate.now());
        Tarea tarea2 = new Tarea("Tarea 2", "Descripción de tarea 2", LocalDate.now());

        // Act
        proyecto.agregarTarea(tarea1);
        proyecto.agregarTarea(tarea2);

        // Assert
        assertEquals(2, proyecto.getTareas().size(), "El proyecto debería tener 2 tareas");

        proyecto.getTareas().remove(tarea1);
        assertEquals(1, proyecto.getTareas().size(), "El proyecto debería tener 1 tarea después de eliminar una");
    }

    @Test
    public void given_ProyectoConTareasCuando_CambiarEstadoTarea_then_DebeActualizarProgresoProyecto() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 6", "Descripción del proyecto 6");
        Tarea tarea1 = new Tarea("Tarea 1", "Descripción de tarea 1", LocalDate.now());
        Tarea tarea2 = new Tarea("Tarea 2", "Descripción de tarea 2", LocalDate.now());

        // Act
        proyecto.agregarTarea(tarea1);
        proyecto.agregarTarea(tarea2);

        // Inicialmente, el progreso debe ser 0%
        assertEquals(0, proyecto.calcularProgreso(), "El progreso debe ser 0%");

        // Cambiar estado de la tarea 1 a completada
        tarea1.setEstado(Tarea.ESTADO_COMPLETADA);

        // Act
        int progreso = proyecto.calcularProgreso();

        // Assert
        assertEquals(50, progreso, "El progreso debe ser 50% después de completar una tarea");

        // Cambiar estado de la tarea 2 a completada
        tarea2.setEstado(Tarea.ESTADO_COMPLETADA);

        // Act
        progreso = proyecto.calcularProgreso();

        // Assert
        assertEquals(100, progreso, "El progreso debe ser 100% después de completar ambas tareas");
    }

    @ParameterizedTest
    @CsvSource({
        "4, 0, 0",      // 4 tareas, 0 completadas = 0%
        "4, 1, 25",     // 4 tareas, 1 completada = 25%
        "4, 2, 50",     // 4 tareas, 2 completadas = 50%
        "4, 3, 75",     // 4 tareas, 3 completadas = 75%
        "4, 4, 100",    // 4 tareas, 4 completadas = 100%
        "10, 3, 30",    // 10 tareas, 3 completadas = 30%
        "1, 1, 100"     // 1 tarea, 1 completada = 100%
    })
    public void given_ProyectoConDiferentesCantidadesDeTareas_when_CalcularProgreso_then_RetornaPorcentajeCorrecto(
        int totalTareas, int tareasCompletadas, int progresoEsperado) {
        
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto Test", "Descripción Test");
        
        // Agregar tareas al proyecto
        for (int i = 0; i < totalTareas; i++) {
            Tarea tarea = new Tarea("Tarea " + (i + 1), "Descripción " + (i + 1), LocalDate.now());
            
            // Marcar como completada según el parámetro
            if (i < tareasCompletadas) {
                tarea.setEstado(Tarea.ESTADO_COMPLETADA);
            }
            
            proyecto.agregarTarea(tarea);
        }
        
        // Act
        int progreso = proyecto.calcularProgreso();
        
        // Assert
        assertEquals(progresoEsperado, progreso, 
            "El progreso debería ser " + progresoEsperado + "% con " + 
            tareasCompletadas + " de " + totalTareas + " tareas completadas");
    }

}