package com.gr4.service;

import com.gr4.model.Tarea;
import com.gr4.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para CalendarioService usando nomenclatura Given-When-Then
 */
public class CalendarioServiceTest {

    private CalendarioService calendarioService;
    private List<Tarea> tareas;

    @BeforeEach
    public void setUp() {
        // Given: Se inicializa el servicio y la lista de tareas
        calendarioService = new CalendarioService();
        tareas = new ArrayList<>();
    }

    @Test
    public void given_TareasConFechaEspecifica_when_ObtenerTareasPorDia_then_DebeRetornarSoloTareasDeEseDia() {
        // Given: Preparamos tareas con diferentes fechas
        LocalDate fechaBuscada = LocalDate.of(2025, 10, 20);
        LocalDate otraFecha = LocalDate.of(2025, 10, 21);

        Tarea tarea1 = new Tarea();
        tarea1.setTitulo("Tarea 1");
        tarea1.setFechaVencimiento(fechaBuscada);

        Tarea tarea2 = new Tarea();
        tarea2.setTitulo("Tarea 2");
        tarea2.setFechaVencimiento(otraFecha);

        Tarea tarea3 = new Tarea();
        tarea3.setTitulo("Tarea 3");
        tarea3.setFechaVencimiento(fechaBuscada);

        tareas.add(tarea1);
        tareas.add(tarea2);
        tareas.add(tarea3);

        // When: Obtenemos las tareas del día específico
        List<Tarea> resultado = calendarioService.obtenerTareasPorDia(tareas, fechaBuscada);

        // Then: Debe retornar solo las 2 tareas de esa fecha
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(tarea1));
        assertTrue(resultado.contains(tarea3));
        assertFalse(resultado.contains(tarea2));
    }

    @Test
    public void given_ParametrosNulos_when_ObtenerTareasPorDia_then_DebeLanzarExcepcion() {
        // Given: Parámetros nulos
        LocalDate fecha = LocalDate.of(2025, 10, 20);

        // When & Then: Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            calendarioService.obtenerTareasPorDia(null, fecha);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            calendarioService.obtenerTareasPorDia(tareas, null);
        });
    }

    @Test
    public void given_TareasEnRangoSemanal_when_ObtenerTareasPorSemana_then_DebeRetornarTareasDentroDelRango() {
        // Given: Tareas en diferentes fechas
        LocalDate inicioSemana = LocalDate.of(2025, 10, 20);
        LocalDate finSemana = LocalDate.of(2025, 10, 26);
        LocalDate fueraDeRango = LocalDate.of(2025, 10, 27);

        Tarea tarea1 = new Tarea();
        tarea1.setTitulo("Tarea Lunes");
        tarea1.setFechaVencimiento(inicioSemana);

        Tarea tarea2 = new Tarea();
        tarea2.setTitulo("Tarea Miércoles");
        tarea2.setFechaVencimiento(LocalDate.of(2025, 10, 22));

        Tarea tarea3 = new Tarea();
        tarea3.setTitulo("Tarea siguiente semana");
        tarea3.setFechaVencimiento(fueraDeRango);

        tareas.add(tarea1);
        tareas.add(tarea2);
        tareas.add(tarea3);

        // When: Obtenemos tareas de la semana
        List<Tarea> resultado = calendarioService.obtenerTareasPorSemana(tareas, inicioSemana, finSemana);

        // Then: Solo debe retornar las 2 tareas dentro del rango
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(tarea1));
        assertTrue(resultado.contains(tarea2));
        assertFalse(resultado.contains(tarea3));
    }

    @Test
    public void given_FechaInicioMayorQueFechaFin_when_ObtenerTareasPorSemana_then_DebeLanzarExcepcion() {
        // Given: Fechas invertidas
        LocalDate fechaInicio = LocalDate.of(2025, 10, 26);
        LocalDate fechaFin = LocalDate.of(2025, 10, 20);

        // When & Then: Debe lanzar IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            calendarioService.obtenerTareasPorSemana(tareas, fechaInicio, fechaFin);
        });
    }

    @Test
    public void given_TareasConDiferentesFechas_when_AgruparTareasPorFecha_then_DebeRetornarMapaAgrupado() {
        // Given: Tareas con fechas repetidas
        LocalDate fecha1 = LocalDate.of(2025, 10, 20);
        LocalDate fecha2 = LocalDate.of(2025, 10, 21);

        Tarea tarea1 = new Tarea();
        tarea1.setTitulo("Tarea A");
        tarea1.setFechaVencimiento(fecha1);

        Tarea tarea2 = new Tarea();
        tarea2.setTitulo("Tarea B");
        tarea2.setFechaVencimiento(fecha1);

        Tarea tarea3 = new Tarea();
        tarea3.setTitulo("Tarea C");
        tarea3.setFechaVencimiento(fecha2);

        tareas.add(tarea1);
        tareas.add(tarea2);
        tareas.add(tarea3);

        // When: Agrupamos tareas por fecha
        Map<LocalDate, List<Tarea>> resultado = calendarioService.agruparTareasPorFecha(tareas);

        // Then: Debe tener 2 grupos con las tareas correspondientes
        assertEquals(2, resultado.size());
        assertEquals(2, resultado.get(fecha1).size());
        assertEquals(1, resultado.get(fecha2).size());
    }

    @Test
    public void given_MultiplesTareasAltaPrioridad_when_DetectarConflictos_then_DebeRetornarTrue() {
        // Given: 2 tareas de alta prioridad el mismo día
        LocalDate fecha = LocalDate.of(2025, 10, 20);

        Tarea tarea1 = new Tarea();
        tarea1.setTitulo("Tarea Importante 1");
        tarea1.setFechaVencimiento(fecha);
        tarea1.setPrioridad(Tarea.PRIORIDAD_ALTA);

        Tarea tarea2 = new Tarea();
        tarea2.setTitulo("Tarea Importante 2");
        tarea2.setFechaVencimiento(fecha);
        tarea2.setPrioridad(Tarea.PRIORIDAD_ALTA);

        tareas.add(tarea1);
        tareas.add(tarea2);

        // When: Detectamos conflictos
        boolean resultado = calendarioService.detectarConflictos(tareas, fecha);

        // Then: Debe detectar conflicto
        assertTrue(resultado);
    }

    @Test
    public void given_UnaSolaTareaAltaPrioridad_when_DetectarConflictos_then_DebeRetornarFalse() {
        // Given: Solo 1 tarea de alta prioridad
        LocalDate fecha = LocalDate.of(2025, 10, 20);

        Tarea tarea = new Tarea();
        tarea.setTitulo("Tarea Importante");
        tarea.setFechaVencimiento(fecha);
        tarea.setPrioridad(Tarea.PRIORIDAD_ALTA);

        tareas.add(tarea);

        // When: Detectamos conflictos
        boolean resultado = calendarioService.detectarConflictos(tareas, fecha);

        // Then: No debe detectar conflicto
        assertFalse(resultado);
    }

    @Test
    public void given_TareasPendientesYCompletadas_when_ContarTareasPendientes_then_DebeContarSoloPendientes() {
        // Given: Tareas con diferentes estados
        LocalDate fecha = LocalDate.of(2025, 10, 20);

        Tarea tareaPendiente1 = new Tarea();
        tareaPendiente1.setTitulo("Pendiente 1");
        tareaPendiente1.setFechaVencimiento(fecha);
        tareaPendiente1.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tareaPendiente2 = new Tarea();
        tareaPendiente2.setTitulo("Pendiente 2");
        tareaPendiente2.setFechaVencimiento(fecha);
        tareaPendiente2.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tareaCompletada = new Tarea();
        tareaCompletada.setTitulo("Completada");
        tareaCompletada.setFechaVencimiento(fecha);
        tareaCompletada.setEstado(Tarea.ESTADO_COMPLETADA);

        Tarea tareaEnProgreso = new Tarea();
        tareaEnProgreso.setTitulo("En Progreso");
        tareaEnProgreso.setFechaVencimiento(fecha);
        tareaEnProgreso.setEstado(Tarea.ESTADO_EN_PROGRESO);

        tareas.add(tareaPendiente1);
        tareas.add(tareaPendiente2);
        tareas.add(tareaCompletada);
        tareas.add(tareaEnProgreso);

        // When: Contamos tareas pendientes
        long resultado = calendarioService.contarTareasPendientes(tareas, fecha);

        // Then: Debe contar solo las 2 pendientes
        assertEquals(2, resultado);
    }

}
