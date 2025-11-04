package com.gr4.controller;

import com.gr4.model.Tarea;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;
import com.gr4.strategy.OrdenStrategy;
import com.gr4.strategy.OrdenPorPrioridad;
import com.gr4.strategy.OrdenPorFecha;
import com.gr4.strategy.OrdenPorTitulo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet Controlador para Administración/Organización de Tareas
 * Implementa el caso de uso "Organizar Tareas" del Incremento 1
 * Aplica el Patrón Strategy para diferentes criterios de ordenamiento
 * Basado en el diagrama de secuencia correspondiente
 */
@WebServlet(name = "GestorAdministracionServlet", urlPatterns = {"/organizar"})
public class GestorAdministracionServlet extends HttpServlet {

    private TareaRepository tareaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        tareaRepository = new TareaRepositoryImpl();
        System.out.println("✓ GestorAdministracionServlet inicializado");
    }

    /**
     * Maneja peticiones GET - Muestra la lista de tareas organizadas
     * Implementa el flujo del diagrama de secuencia
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Obtener el criterio de ordenamiento seleccionado
            String criterioOrden = request.getParameter("orden");
            if (criterioOrden == null || criterioOrden.isEmpty()) {
                criterioOrden = "prioridad"; // Por defecto
            }

            System.out.println("📊 Criterio de orden seleccionado: " + criterioOrden);

            // PASO 2: Obtener todas las tareas del repositorio
            List<Tarea> todasLasTareas = tareaRepository.findAll();

            // PASO 3: Separar tareas activas y vencidas
            LocalDate hoy = LocalDate.now();
            System.out.println("📅 Fecha de hoy: " + hoy);
            
            List<Tarea> tareasActivas = new ArrayList<>();
            List<Tarea> tareasVencidas = new ArrayList<>();
            
            for (Tarea tarea : todasLasTareas) {
                // Verificar si la tarea está vencida
                boolean estaVencida = false;
                
                if (tarea.getFechaVencimiento() != null) {
                    System.out.println("🔍 Tarea #" + tarea.getId() + " '" + tarea.getTitulo() + "':");
                    System.out.println("   - Fecha vencimiento: " + tarea.getFechaVencimiento());
                    System.out.println("   - Estado: " + tarea.getEstado());
                    System.out.println("   - isBefore(hoy)? " + tarea.getFechaVencimiento().isBefore(hoy));
                    
                    // Solo está vencida si NO está completada y la fecha ya pasó
                    if (!tarea.getEstado().equals("Completada") && tarea.getFechaVencimiento().isBefore(hoy)) {
                        estaVencida = true;
                        System.out.println("   ⚠️ MARCADA COMO VENCIDA");
                    } else {
                        System.out.println("   ✓ Marcada como activa");
                    }
                }
                
                if (estaVencida) {
                    tareasVencidas.add(tarea);
                } else {
                    tareasActivas.add(tarea);
                }
            }

            // PASO 4: Seleccionar estrategia de ordenamiento (Patrón Strategy)
            OrdenStrategy estrategia = seleccionarEstrategia(criterioOrden);

            // PASO 5: Aplicar la estrategia de ordenamiento a ambas listas
            List<Tarea> tareasActivasOrdenadas = estrategia.ordenar(tareasActivas);
            List<Tarea> tareasVencidasOrdenadas = estrategia.ordenar(tareasVencidas);

            System.out.println("✓ Tareas ordenadas con: " + estrategia.getNombre());
            System.out.println("  Tareas activas: " + tareasActivasOrdenadas.size());
            System.out.println("  Tareas vencidas: " + tareasVencidasOrdenadas.size());

            // PASO 6: Enviar datos a la vista
            request.setAttribute("tareas", tareasActivasOrdenadas);
            request.setAttribute("tareasVencidas", tareasVencidasOrdenadas);
            request.setAttribute("criterioActual", criterioOrden);
            request.setAttribute("nombreEstrategia", estrategia.getNombre());

            // Redirigir a la vista
            request.getRequestDispatcher("/jsp/organizar.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al organizar tareas: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al organizar las tareas: " + e.getMessage());
            request.getRequestDispatcher("/jsp/organizar.jsp").forward(request, response);
        }
    }

    /**
     * Selecciona la estrategia de ordenamiento según el criterio
     * Implementa el Patrón Strategy
     * @param criterio Criterio de ordenamiento ("prioridad", "fecha", "titulo")
     * @return Estrategia de ordenamiento seleccionada
     */
    private OrdenStrategy seleccionarEstrategia(String criterio) {
        switch (criterio.toLowerCase()) {
            case "prioridad":
                return new OrdenPorPrioridad();
            case "fecha":
                return new OrdenPorFecha();
            case "titulo":
                return new OrdenPorTitulo();
            default:
                return new OrdenPorPrioridad(); // Por defecto
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // El POST puede usarse para cambiar el estado de una tarea
        doGet(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorAdministracionServlet destruido");
    }
}