package com.gr4.controller;

import com.gr4.dto.ActividadDTO;
import com.gr4.model.Actividad;
import com.gr4.repository.ActividadRepository;
import com.gr4.repository.ActividadRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet Controlador para Planificación de Actividades
 * Implementa el caso de uso "Planificar Actividades" del Incremento 1
 * Basado en el diagrama de secuencia correspondiente
 */
@WebServlet(name = "GestorPlanificacionServlet", urlPatterns = {"/planificar"})
public class GestorPlanificacionServlet extends HttpServlet {

    private ActividadRepository actividadRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar el repositorio
        actividadRepository = new ActividadRepositoryImpl();
        System.out.println("✓ GestorPlanificacionServlet inicializado");
    }

    /**
     * Maneja peticiones GET - Muestra el formulario de planificación
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Según diagrama de secuencia: MainPlanificacionTareas muestra el formulario
        request.getRequestDispatcher("/jsp/planificar.jsp").forward(request, response);
    }

    /**
     * Maneja peticiones POST - Procesa la creación de actividad
     * Implementa el flujo del diagrama de secuencia
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Recibir ActividadDTO desde el formulario
            ActividadDTO dto = new ActividadDTO();
            dto.setTitulo(request.getParameter("titulo"));
            dto.setDescripcion(request.getParameter("descripcion"));
            dto.setFechaEntrega(request.getParameter("fechaEntrega"));
            dto.setPrioridad(request.getParameter("prioridad"));
            dto.setEstado("Pendiente"); // Estado inicial

            System.out.println("📥 DTO recibido: " + dto);

            // PASO 2: Validar el DTO (según diagrama de secuencia)
            if (!validar(dto)) {
                request.setAttribute("error", "Datos inválidos. Por favor complete todos los campos correctamente.");
                request.getRequestDispatcher("/jsp/planificar.jsp").forward(request, response);
                return;
            }

            // PASO 3: Crear entidad Actividad desde el DTO
            Actividad actividad = crearActividadDesdeDTO(dto);

            // PASO 4: Guardar en el repositorio (según diagrama de secuencia)
            Actividad actividadGuardada = actividadRepository.save(actividad);

            System.out.println("✓ Actividad guardada: " + actividadGuardada);

            // PASO 5: Redirigir con mensaje de éxito
            request.setAttribute("mensaje", "Actividad registrada exitosamente");
            request.setAttribute("actividad", actividadGuardada);
            request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al planificar actividad: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar la actividad: " + e.getMessage());
            request.getRequestDispatcher("/jsp/planificar.jsp").forward(request, response);
        }
    }

    /**
     * Método de validación según diagrama de secuencia
     * @param dto ActividadDTO a validar
     * @return true si el DTO es válido
     */
    private boolean validar(ActividadDTO dto) {
        return dto.validar();
    }

    /**
     * Crea una entidad Actividad desde un DTO
     * Según diagrama de secuencia: GestorPlanificacionController crea Actividad
     */
    private Actividad crearActividadDesdeDTO(ActividadDTO dto) {
        Actividad actividad = new Actividad();
        actividad.setTitulo(dto.getTitulo());
        actividad.setDescripcion(dto.getDescripcion());
        actividad.setFechaEntrega(dto.getFechaEntregaAsLocalDate());
        actividad.setPrioridad(dto.getPrioridad() != null ? dto.getPrioridad() : "Media");
        actividad.setEstado(dto.getEstado() != null ? dto.getEstado() : "Pendiente");
        return actividad;
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorPlanificacionServlet destruido");
    }
}