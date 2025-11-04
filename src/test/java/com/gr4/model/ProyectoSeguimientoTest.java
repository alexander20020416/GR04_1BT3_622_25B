package com.gr4.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;

/**
 * Pruebas TDD para la funcionalidad de seguimiento visual de proyectos.
 * Historia de Usuario 6: Ver ventana de seguimiento visual del proyecto.
 * 
 * Estas pruebas validan:
 * - CA3: Sistema muestra alertas visuales para proyectos próximos a vencer
 * 
 * @author Tu Nombre
 */
public class ProyectoSeguimientoTest {

    @Test
    public void given_ProyectoNuevo_when_SetFechaVencimiento_then_DebeGuardarCorrectamente() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto Final", "Proyecto de Construcción de Software");
        LocalDate fechaEsperada = LocalDate.now().plusDays(10);
        
        // Act
        proyecto.setFechaVencimiento(fechaEsperada);
        
        // Assert
        assertEquals(fechaEsperada, proyecto.getFechaVencimiento(), 
            "La fecha de vencimiento debe guardarse correctamente");
    }
    
    @Test
    public void given_ProyectoUrgente_when_GetColorIndicador_then_RetornaRojo() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto Urgente", "Vence mañana");
        proyecto.setFechaVencimiento(LocalDate.now().plusDays(1));
        
        // Act
        String color = proyecto.getColorIndicador();
        
        // Assert
        assertEquals("red", color, "Un proyecto urgente debe tener indicador rojo");
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, URGENTE",       // Vence en 1 día
        "2, URGENTE",       // Vence en 2 días
        "3, URGENTE",       // Vence en 3 días (límite urgente)
        "5, PROXIMO",       // Vence en 5 días
        "7, PROXIMO",       // Vence en 7 días (límite próximo)
        "10, A_TIEMPO",     // Vence en 10 días
        "30, A_TIEMPO",     // Vence en 30 días
        "-1, VENCIDO"       // Venció ayer
    })
    public void given_ProyectoConDiferentesFechas_when_DeterminarEstadoUrgencia_then_RetornaEstadoCorrecto(
        int diasHastaVencimiento, String estadoEsperado) {
        
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto", "Descripción");
        LocalDate fechaVencimiento = LocalDate.now().plusDays(diasHastaVencimiento);
        proyecto.setFechaVencimiento(fechaVencimiento);
        
        // Act
        String estadoUrgencia = proyecto.determinarEstadoUrgencia();
        
        // Assert
        assertEquals(estadoEsperado, estadoUrgencia, 
            "Con " + diasHastaVencimiento + " días hasta vencer, el estado debe ser " + estadoEsperado);
    }

}
