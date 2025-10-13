package com.gr4.controller;

import com.gr4.model.Actividad;
import com.gr4.model.Tarea;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import com.gr4.util.ParametroParser;

/**
 * Servlet para agregar tareas a actividades existentes
 * Mantiene la arquitectura: Actividad 1:N Tarea
 */
@WebServlet(name = "AgregarTareaServlet", urlPatterns = {"/agregar-tarea"})
public class AgregarTareaServlet extends BaseServlet{

    /**
     * GET: Muestra el formulario para agregar tarea
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String actividadIdStr = request.getParameter("actividadId");
        
        if (actividadIdStr == null || actividadIdStr.isEmpty()) {
            request.setAttribute("error", "ID de actividad no especificado");
            request.getRequestDispatcher("/listar").forward(request, response);
            return;
        }

        try {
            Long actividadId = ParametroParser.parseNumero(actividadIdStr, "ID de actividad");
            Optional<Actividad> actividadOpt = actividadRepository.findById(actividadId);

            if (actividadOpt.isPresent()) {
                request.setAttribute("actividad", actividadOpt.get());
                request.getRequestDispatcher("/jsp/agregar_tarea.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Actividad no encontrada");
                request.getRequestDispatcher("/listar").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de actividad inválido");
            request.getRequestDispatcher("/listar").forward(request, response);
        }
    }

    /**
     * POST: Procesa la creación de la tarea
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener parámetros
            Long actividadId = Long.parseLong(request.getParameter("actividadId"));
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fechaVencimiento = request.getParameter("fechaVencimiento");
            String prioridad = request.getParameter("prioridad");

            // Validar
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new IllegalArgumentException("El título es obligatorio");
            }

            // Buscar la actividad
            Optional<Actividad> actividadOpt = actividadRepository.findById(actividadId);
            if (!actividadOpt.isPresent()) {
                throw new IllegalArgumentException("Actividad no encontrada");
            }

            Actividad actividad = actividadOpt.get();

            // Crear la tarea
            Tarea tarea = crearTareaDesdeParametros(titulo, descripcion, fechaVencimiento, prioridad, actividad);

            // Guardar la tarea
            Tarea tareaGuardada = tareaRepository.save(tarea);

            System.out.println("✓ Tarea guardada: " + tareaGuardada);

            // Redirigir con mensaje de éxito
            request.setAttribute("mensaje", "Tarea agregada exitosamente");
            request.setAttribute("tarea", tareaGuardada);
            request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al agregar tarea: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar la tarea: " + e.getMessage());
            request.getRequestDispatcher("/jsp/agregar_tarea.jsp").forward(request, response);
        }
    }

    /**
     * Crea y configura una nueva instancia de Tarea con los parámetros proporcionados
     */
    private Tarea crearTareaDesdeParametros(String titulo, String descripcion, 
                                            String fechaVencimiento, String prioridad, 
                                            Actividad actividad) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setFechaVencimiento(ParametroParser.parseFecha(fechaVencimiento));
        tarea.setPrioridad(prioridad != null ? prioridad : Tarea.PRIORIDAD_MEDIA);
        tarea.setEstado(Tarea.ESTADO_PENDIENTE);
        tarea.setActividad(actividad);
        return tarea;
    }

}
