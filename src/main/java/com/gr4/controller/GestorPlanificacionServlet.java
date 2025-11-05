package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Tarea;
import com.gr4.repository.MateriaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.gr4.util.ParametroParser;

/**
 * Servlet Controlador para Planificación de Tareas
 * Implementa el caso de uso "Planificar Actividades" (crea Tareas directamente)
 * Basado en el diagrama de secuencia correspondiente
 */
@WebServlet(name = "GestorPlanificacionServlet", urlPatterns = {"/planificar"})
public class GestorPlanificacionServlet extends BaseServlet {

    private MateriaRepositoryImpl materiaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        materiaRepository = new MateriaRepositoryImpl();
    }

    /**
     * Maneja peticiones GET - Muestra el formulario de planificación
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Pasar materiaId al JSP si viene en el parámetro
        String materiaId = request.getParameter("materiaId");
        if (materiaId != null && !materiaId.trim().isEmpty()) {
            request.setAttribute("materiaId", materiaId);
        }

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
            String materiaIdStr = request.getParameter("materiaId");

            System.out.println("📥 Datos recibidos - Título: " + titulo + ", Estado: " + estado + ", Prioridad: " + prioridad + ", MateriaId: " + materiaIdStr);

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
            tarea.setFechaVencimiento(ParametroParser.parseFecha(fechaLimiteStr));
            tarea.setEstado(estado != null ? estado : Tarea.ESTADO_PENDIENTE);
            tarea.setPrioridad(prioridad != null ? prioridad : Tarea.PRIORIDAD_MEDIA);

            // PASO 3.5: Asignar materia si viene el parámetro
            if (materiaIdStr != null && !materiaIdStr.trim().isEmpty()) {
                try {
                    Long materiaId = Long.parseLong(materiaIdStr);
                    Materia materia = materiaRepository.findById(materiaId);
                    if (materia != null) {
                        tarea.setMateria(materia);
                        System.out.println("✓ Materia asignada: " + materia.getNombre());
                    }
                } catch (NumberFormatException e) {
                    System.err.println("⚠️ MateriaId inválido: " + materiaIdStr);
                }
            }

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

}