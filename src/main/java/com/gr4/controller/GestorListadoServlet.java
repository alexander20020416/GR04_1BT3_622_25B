package com.gr4.controller;

import com.gr4.model.Tarea;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.gr4.service.TareaFilterService;

/**
 * Servlet Controlador para Consultar Tareas
 * Implementa el caso de uso "Consultar Tareas" del Incremento 2
 * Basado en el diagrama de secuencia correspondiente
 */
@WebServlet(name = "GestorListadoServlet", urlPatterns = {"/consultar"})
public class GestorListadoServlet extends BaseServlet {

    private TareaFilterService filterService;

    /**
     * Maneja peticiones GET - Consulta y muestra las tareas
     * Implementa el flujo del diagrama de secuencia
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Obtener filtro (si existe)
            String filtro = request.getParameter("filtro");

            System.out.println("🔍 Consultando tareas con filtro: " + (filtro != null ? filtro : "ninguno"));

            // PASO 2: Inicializar el servicio de filtrado
            filterService = new TareaFilterService(tareaRepository);

            // PASO 3: Obtener tareas según el filtro
            List<Tarea> tareasFiltradas = filterService.obtenerTareasFiltradas(filtro);

            // PASO 4: Separar tareas activas de vencidas
            LocalDate hoy = LocalDate.now();
            System.out.println("📅 Fecha de hoy (Consultar): " + hoy);
            
            List<Tarea> tareasActivas = new ArrayList<>();
            List<Tarea> tareasVencidas = new ArrayList<>();
            
            for (Tarea tarea : tareasFiltradas) {
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

            System.out.println("✔ Tareas encontradas - Activas: " + tareasActivas.size() + ", Vencidas: " + tareasVencidas.size());

            // PASO 5: Validar integridad de datos (casos de prueba CP14)
            boolean integridadValida = validarIntegridadDatos(tareasFiltradas);

            // PASO 6: Preparar mensaje si no hay tareas
            if (tareasActivas.isEmpty() && tareasVencidas.isEmpty()) {
                request.setAttribute("mensaje", filterService.obtenerMensajeVacio(filtro));
            }

            // PASO 7: Enviar datos a la vista
            request.setAttribute("tareas", tareasActivas);
            request.setAttribute("tareasVencidas", tareasVencidas);
            request.setAttribute("filtroActual", filtro);
            request.setAttribute("integridadValida", integridadValida);

            request.getRequestDispatcher("/jsp/consultar.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✘ Error al consultar tareas: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar tareas: " + e.getMessage());
            request.getRequestDispatcher("/jsp/consultar.jsp").forward(request, response);
        }
    }

    /**
     * Valida la integridad de los datos de las tareas
     * Caso de prueba CP14: Todas las tareas deben tener id, descripcion, estado y fechaVencimiento
     */
    private boolean validarIntegridadDatos(List<Tarea> tareas) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == null ||
                    tarea.getDescripcion() == null ||
                    tarea.getEstado() == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}