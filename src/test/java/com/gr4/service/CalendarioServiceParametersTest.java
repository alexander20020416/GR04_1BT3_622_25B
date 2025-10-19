package com.gr4.service;

import com.gr4.model.Tarea;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CalendarioServiceParametersTest {

    private CalendarioService calendarioService;
    // Parámetros del test 1
    private LocalDate fecha;
    private int tareasPendientesEsperadas;
    private int tareasEnProgreso;
    private int tareasCompletadas;


    //Constructor que recibe los parámetros para cada ejecución del test
    public CalendarioServiceParametersTest(String fechaStr, int tareasPendientesEsperadas,
                                           int tareasEnProgreso, int tareasCompletadas) {
        this.fecha = LocalDate.parse(fechaStr);
        this.tareasPendientesEsperadas = tareasPendientesEsperadas;
        this.tareasEnProgreso = tareasEnProgreso;
        this.tareasCompletadas = tareasCompletadas;
    }

    /**
     * Proveedor de parámetros para el test
     * Cada array representa: {fecha, tareasPendientes, tareasEnProgreso, tareasCompletadas}
     */
    @Parameters(name = "Escenario {index}: Fecha={0}, Pendientes={1}, EnProgreso={2}, Completadas={3}")
    public static Collection<Object[]> parametros() {
        return Arrays.asList(new Object[][] {
                // Escenario 1: Día con solo tareas pendientes
                {"2025-10-20", 3, 0, 0},
                // Escenario 2: Día con mix de estados
                {"2025-10-21", 2, 1, 1},
                // Escenario 3: Día sin tareas pendientes
                {"2025-10-22", 0, 2, 3},
                // Escenario 4: Día con muchas tareas pendientes
                {"2025-10-23", 5, 1, 0},
                // Escenario 5: Día con una sola tarea pendiente
                {"2025-10-24", 1, 0, 0},
                // Escenario 6: Día con distribución uniforme
                {"2025-10-25", 2, 2, 2}
        });
    }

    @Before
    public void setUp() {
        // Given: Se inicializa el servicio antes de cada test
        calendarioService = new CalendarioService();
    }

    //Test 1:
    @Test
    public void given_TareasConDiferentesEstados_when_ContarTareasPendientes_then_DebeRetornarConteoCorrectoPorFecha() {
        // Given: Se preparan tareas con diferentes estados para una fecha específica
        List<Tarea> tareas = new ArrayList<>();
        // Crear tareas pendientes
        for (int i = 0; i < tareasPendientesEsperadas; i++) {
            Tarea tarea = new Tarea();
            tarea.setTitulo("Tarea Pendiente " + i);
            tarea.setFechaVencimiento(fecha);
            tarea.setEstado(Tarea.ESTADO_PENDIENTE);
            tareas.add(tarea);
        }
        // Crear tareas en progreso
        for (int i = 0; i < tareasEnProgreso; i++) {
            Tarea tarea = new Tarea();
            tarea.setTitulo("Tarea En Progreso " + i);
            tarea.setFechaVencimiento(fecha);
            tarea.setEstado(Tarea.ESTADO_EN_PROGRESO);
            tareas.add(tarea);
        }
        // Crear tareas completadas
        for (int i = 0; i < tareasCompletadas; i++) {
            Tarea tarea = new Tarea();
            tarea.setTitulo("Tarea Completada " + i);
            tarea.setFechaVencimiento(fecha);
            tarea.setEstado(Tarea.ESTADO_COMPLETADA);
            tareas.add(tarea);
        }

        // When: Se cuentan las tareas pendientes para esa fecha
        long conteo = calendarioService.contarTareasPendientes(tareas, fecha);
        // Then: El conteo debe coincidir con el número esperado de tareas pendientes
        String mensajeError = String.format(
                "Para la fecha %s con %d pendientes, %d en progreso y %d completadas, se esperaba contar %d pero se obtuvo %d",
                fecha, tareasPendientesEsperadas, tareasEnProgreso, tareasCompletadas, tareasPendientesEsperadas, conteo);
        assertEquals(mensajeError, tareasPendientesEsperadas, conteo);
        // Verificación adicional: el total de tareas creadas
        int totalTareas = tareasPendientesEsperadas + tareasEnProgreso + tareasCompletadas;
        assertEquals("El total de tareas creadas debe coincidir", totalTareas, tareas.size());
    }

}