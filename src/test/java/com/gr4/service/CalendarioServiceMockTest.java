package com.gr4.service;

import com.gr4.model.Tarea;
import com.gr4.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Valida la integración con TareaRepository usando Mockito
 */
@ExtendWith(MockitoExtension.class)
public class CalendarioServiceMockTest {

    private CalendarioService calendarioService;

    @Mock
    private TareaRepository tareaRepository;

    @BeforeEach
    public void setUp() {
        // Given: Se inicializa el servicio
        calendarioService = new CalendarioService();
    }

    /**
     * Verifica que el servicio pueda obtener tareas desde el repositorio
     * y filtrarlas correctamente por día
     */
    @Test
    public void given_RepositorioConTareas_when_ObtenerTareasPorDiaDesdeRepositorio_then_DebeRetornarTareasFiltradas() {
        // Given: Preparamos el mock del repositorio con tareas
        LocalDate fechaBuscada = LocalDate.of(2025, 10, 25);
        LocalDate otraFecha = LocalDate.of(2025, 10, 26);

        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        tarea1.setTitulo("Tarea del repositorio 1");
        tarea1.setFechaVencimiento(fechaBuscada);
        tarea1.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tarea2 = new Tarea();
        tarea2.setId(2L);
        tarea2.setTitulo("Tarea del repositorio 2");
        tarea2.setFechaVencimiento(otraFecha);
        tarea2.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tarea3 = new Tarea();
        tarea3.setId(3L);
        tarea3.setTitulo("Tarea del repositorio 3");
        tarea3.setFechaVencimiento(fechaBuscada);
        tarea3.setEstado(Tarea.ESTADO_EN_PROGRESO);

        List<Tarea> tareasDelRepositorio = Arrays.asList(tarea1, tarea2, tarea3);
        // Configuramos el comportamiento del mock
        when(tareaRepository.findAll()).thenReturn(tareasDelRepositorio);

        // When: Obtenemos todas las tareas del repositorio y las filtramos por día
        List<Tarea> todasLasTareas = tareaRepository.findAll();
        List<Tarea> tareasFiltradas = calendarioService.obtenerTareasPorDia(todasLasTareas, fechaBuscada);

        // Then: Verificamos que el repositorio fue llamado y el filtrado es correcto
        verify(tareaRepository, times(1)).findAll();
        assertEquals(2, tareasFiltradas.size());
        assertTrue(tareasFiltradas.contains(tarea1));
        assertTrue(tareasFiltradas.contains(tarea3));
        assertFalse(tareasFiltradas.contains(tarea2));
    }

    /**
     * Verifica que el servicio pueda agrupar tareas obtenidas del repositorio
     * y detectar conflictos de alta prioridad usando datos mockeados
     */
    @Test
    public void given_RepositorioConTareasAltaPrioridad_when_DetectarConflictosDesdeRepositorio_then_DebeIdentificarConflictosCorrectamente() {
        // Given: Preparamos el mock con múltiples tareas de alta prioridad
        LocalDate fechaConflicto = LocalDate.of(2025, 10, 28);
        LocalDate fechaSinConflicto = LocalDate.of(2025, 10, 29);

        Tarea tareaAlta1 = new Tarea();
        tareaAlta1.setId(10L);
        tareaAlta1.setTitulo("Examen de Cálculo");
        tareaAlta1.setFechaVencimiento(fechaConflicto);
        tareaAlta1.setPrioridad(Tarea.PRIORIDAD_ALTA);
        tareaAlta1.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tareaAlta2 = new Tarea();
        tareaAlta2.setId(11L);
        tareaAlta2.setTitulo("Entrega de Proyecto");
        tareaAlta2.setFechaVencimiento(fechaConflicto);
        tareaAlta2.setPrioridad(Tarea.PRIORIDAD_ALTA);
        tareaAlta2.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tareaMedia = new Tarea();
        tareaMedia.setId(12L);
        tareaMedia.setTitulo("Lectura opcional");
        tareaMedia.setFechaVencimiento(fechaConflicto);
        tareaMedia.setPrioridad(Tarea.PRIORIDAD_MEDIA);
        tareaMedia.setEstado(Tarea.ESTADO_PENDIENTE);

        Tarea tareaAltaSola = new Tarea();
        tareaAltaSola.setId(13L);
        tareaAltaSola.setTitulo("Presentación");
        tareaAltaSola.setFechaVencimiento(fechaSinConflicto);
        tareaAltaSola.setPrioridad(Tarea.PRIORIDAD_ALTA);
        tareaAltaSola.setEstado(Tarea.ESTADO_PENDIENTE);

        List<Tarea> tareasDelRepositorio = Arrays.asList(tareaAlta1, tareaAlta2, tareaMedia, tareaAltaSola);

        // Configuramos el comportamiento del mock
        when(tareaRepository.findAll()).thenReturn(tareasDelRepositorio);

        // When: Obtenemos las tareas y detectamos conflictos
        List<Tarea> todasLasTareas = tareaRepository.findAll();
        boolean hayConflictoEnFecha1 = calendarioService.detectarConflictos(todasLasTareas, fechaConflicto);
        boolean hayConflictoEnFecha2 = calendarioService.detectarConflictos(todasLasTareas, fechaSinConflicto);

        // Then: Verificamos las interacciones y los resultados
        verify(tareaRepository, times(1)).findAll();
        assertTrue(hayConflictoEnFecha1, "Debe detectar conflicto con 2 tareas de alta prioridad");
        assertFalse(hayConflictoEnFecha2, "No debe detectar conflicto con solo 1 tarea de alta prioridad");

        // Verificación adicional: agrupamos las tareas para validar la estructura
        Map<LocalDate, List<Tarea>> tareasAgrupadas = calendarioService.agruparTareasPorFecha(todasLasTareas);
        assertEquals(2, tareasAgrupadas.size(), "Debe haber 2 fechas diferentes");
        assertEquals(3, tareasAgrupadas.get(fechaConflicto).size(), "La fecha con conflicto debe tener 3 tareas");
        assertEquals(1, tareasAgrupadas.get(fechaSinConflicto).size(), "La fecha sin conflicto debe tener 1 tarea");
    }
}