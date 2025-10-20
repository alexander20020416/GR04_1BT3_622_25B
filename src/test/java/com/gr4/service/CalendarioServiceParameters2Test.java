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

/**
 * Pruebas unitarias parametrizadas para CalendarioService usando Parameterized Runner
 * Test #2: Valida el filtrado de tareas por día con múltiples combinaciones
 */
@RunWith(Parameterized.class)
public class CalendarioServiceParameters2Test {

    private CalendarioService calendarioService;

    // Parámetros del test
    private LocalDate fechaBuscada;
    private int tareasEnEsaFecha;
    private int tareasOtraFecha1;
    private int tareasOtraFecha2;
    private int tamanoEsperado;

    private static final String MENSAJE_TOTAL_TAREAS = "El total de tareas creadas debe coincidir";
    private static final String MENSAJE_FECHA_TAREAS = "Para fecha %s con %d tareas en esa fecha, %d en otra1 y %d en otra2, se esperaba obtener %d pero se obtuvo %d";


    /**
     * Constructor que recibe los parámetros para cada ejecución del test
     */
    public CalendarioServiceParameters2Test(String fechaStr, int tareasEnEsaFecha,
                                            int tareasOtraFecha1, int tareasOtraFecha2,
                                            int tamanoEsperado) {
        this.fechaBuscada = LocalDate.parse(fechaStr);
        this.tareasEnEsaFecha = tareasEnEsaFecha;
        this.tareasOtraFecha1 = tareasOtraFecha1;
        this.tareasOtraFecha2 = tareasOtraFecha2;
        this.tamanoEsperado = tamanoEsperado;
    }

    /**
     * Proveedor de parámetros para el test
     * Cada array representa: {fecha, tareasEnEsaFecha, tareasOtraFecha1, tareasOtraFecha2, tamañoEsperado}
     */
    @Parameters(name = "Escenario {index}: Fecha={0}, EnFecha={1}, OtraF1={2}, OtraF2={3}, Esperado={4}")
    public static Collection<Object[]> parametros() {
        return Arrays.asList(new Object[][] {
                // Escenario 1: Fecha sin tareas, otras fechas con tareas
                {"2025-10-25", 0, 2, 1, 0},
                // Escenario 2: Fecha con todas las tareas
                {"2025-10-26", 3, 0, 0, 3},
                // Escenario 3: Distribución uniforme
                {"2025-10-27", 1, 1, 1, 1},
                // Escenario 4: Fecha con múltiples tareas
                {"2025-10-28", 2, 2, 2, 2},
                // Escenario 5: Fecha con alta concentración de tareas
                {"2025-10-29", 5, 3, 1, 5},
                // Escenario 6: Caso extremo - muchas tareas en otras fechas, pocas en la buscada
                {"2025-10-30", 1, 10, 8, 1}
        });
    }
    @Before
    public void setUp() {
        // Given: Se inicializa el servicio antes de cada test
        calendarioService = new CalendarioService();
    }
    @Test
    public void given_TareasEnMultiplesFechas_when_ObtenerTareasPorDia_then_DebeRetornarTamañoCorrectoDeLista() {

        // Given: Se preparan tareas distribuidas en diferentes fechas
        LocalDate otraFecha1 = fechaBuscada.plusDays(1);
        LocalDate otraFecha2 = fechaBuscada.plusDays(2);
        List<Tarea> tareas = new ArrayList<>();
        // Crear tareas para la fecha buscada
        for (int i = 0; i < tareasEnEsaFecha; i++) {
            Tarea tarea = new Tarea();
            tarea.setTitulo("Tarea Fecha Buscada " + i);
            tarea.setDescripcion("Descripción tarea " + i);
            tarea.setFechaVencimiento(fechaBuscada);
            tarea.setPrioridad(Tarea.PRIORIDAD_MEDIA);
            tarea.setEstado(Tarea.ESTADO_PENDIENTE);
            tareas.add(tarea);
        }
        // Crear tareas para otra fecha 1
        for (int i = 0; i < tareasOtraFecha1; i++) {
            Tarea tarea = new Tarea();
            tarea.setTitulo("Tarea Otra Fecha 1 " + i);
            tarea.setDescripcion("Descripción otra fecha 1 " + i);
            tarea.setFechaVencimiento(otraFecha1);
            tarea.setPrioridad(Tarea.PRIORIDAD_BAJA);
            tarea.setEstado(Tarea.ESTADO_PENDIENTE);
            tareas.add(tarea);
        }
        // Crear tareas para otra fecha 2
        for (int i = 0; i < tareasOtraFecha2; i++) {
            Tarea tarea = new Tarea();
            tarea.setTitulo("Tarea Otra Fecha 2 " + i);
            tarea.setDescripcion("Descripción otra fecha 2 " + i);
            tarea.setFechaVencimiento(otraFecha2);
            tarea.setPrioridad(Tarea.PRIORIDAD_ALTA);
            tarea.setEstado(Tarea.ESTADO_EN_PROGRESO);
            tareas.add(tarea);
        }

        // When: Se obtienen las tareas del día específico
        List<Tarea> resultado = calendarioService.obtenerTareasPorDia(tareas, fechaBuscada);
        // Then: El tamaño de la lista debe ser el esperado
        assertEquals(String.format(MENSAJE_FECHA_TAREAS, fechaBuscada, tareasEnEsaFecha, tareasOtraFecha1, tareasOtraFecha2, tamanoEsperado, resultado.size()),
                tamanoEsperado, resultado.size());
        // Verificación adicional 1: todas las tareas deben ser de la fecha correcta
        for (Tarea tarea : resultado) {
            assertNotNull("La tarea no debe ser nula", tarea);
            assertEquals("Todas las tareas retornadas deben ser de la fecha buscada",
                    fechaBuscada, tarea.getFechaVencimiento());
        }
        // Verificación adicional 2: el total de tareas creadas
        int totalTareasCreadas = tareasEnEsaFecha + tareasOtraFecha1 + tareasOtraFecha2;
        assertEquals(MENSAJE_TOTAL_TAREAS, totalTareasCreadas, tareas.size());
        // Verificación adicional 3: si no hay tareas para esa fecha, la lista debe estar vacía
        if (tareasEnEsaFecha == 0) {
            assertTrue("Si no hay tareas para la fecha, la lista debe estar vacía", resultado.isEmpty());
        }
    }
}