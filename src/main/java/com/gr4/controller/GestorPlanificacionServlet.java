package com.gr4.controller;

import com.gr4.model.Tarea;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet Controlador para Planificación de Tareas
 * Implementa el caso de uso "Planificar Actividades" (crea Tareas directamente)
 * Basado en el diagrama de secuencia correspondiente
 */
@WebServlet(name = "GestorPlanificacionServlet", urlPatterns = {"/planificar"})
public class GestorPlanificacionServlet extends HttpServlet {

    private TareaRepository tareaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar el repositorio
        tareaRepository = new TareaRepositoryImpl();
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
     * Maneja peticiones POST - Procesa la creación de tarea
     * Implementa el flujo del diagrama de secuencia
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Recibir datos desde el formulario
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fechaLimiteStr = request.getParameter("fechaLimite");
            String estado = request.getParameter("estado");
            String prioridad = request.getParameter("prioridad");

            System.out.println("📥 Datos recibidos - Título: " + titulo + ", Estado: " + estado + ", Prioridad: " + prioridad);

            // PASO 2: Validar los datos
            if (titulo == null || titulo.trim().isEmpty() || 
                descripcion == null || descripcion.trim().isEmpty() ||
                fechaLimiteStr == null || fechaLimiteStr.trim().isEmpty()) {
                request.setAttribute("error", "Datos inválidos. Por favor complete todos los campos correctamente.");
                request.getRequestDispatcher("/jsp/planificar.jsp").forward(request, response);
                return;
            }

            // PASO 3: Crear entidad Tarea
            Tarea tarea = new Tarea();
            tarea.setTitulo(titulo.trim());
            tarea.setDescripcion(descripcion.trim());
            tarea.setFechaVencimiento(LocalDate.parse(fechaLimiteStr));
            tarea.setEstado(estado != null ? estado : "Pendiente");
            tarea.setPrioridad(prioridad != null ? prioridad : "Media");

            // PASO 4: Guardar en el repositorio
            Tarea tareaGuardada = tareaRepository.save(tarea);

            System.out.println("✓ Tarea guardada: " + tareaGuardada);

            // PASO 5: Redirigir con mensaje de éxito
            request.setAttribute("mensaje", "Tarea registrada exitosamente");
            request.setAttribute("tarea", tareaGuardada);
            request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al planificar tarea: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar la tarea: " + e.getMessage());
            request.getRequestDispatcher("/jsp/planificar.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorPlanificacionServlet destruido");
    }
}