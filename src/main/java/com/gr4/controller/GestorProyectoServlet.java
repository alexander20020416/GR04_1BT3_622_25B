package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Proyecto;
import com.gr4.service.MateriaService;
import com.gr4.service.ProyectoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


/**
 * Servlet responsable de la gestión de proyectos.
 * Maneja creación de proyectos, asociación de tareas y creación de tareas dentro de proyectos.
 * Cumple con SRP - Solo maneja operaciones relacionadas con proyectos.
 */
@WebServlet(name = "GestorProyectoServlet", urlPatterns = {"/proyectos", "/gestionarProyecto", "/seguimiento"})
public class GestorProyectoServlet extends HttpServlet {

    private final ProyectoService proyectoService;
    private final MateriaService materiaService;

    public GestorProyectoServlet(ProyectoService proyectoService, MateriaService materiaService) {
        this.proyectoService = proyectoService;
        this.materiaService = materiaService;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // Instanciación directa para compatibilidad
        System.out.println("✓ GestorProyectoServlet inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        
        // Si es /seguimiento, mostrar vista de seguimiento visual
        if ("/seguimiento".equals(path)) {
            mostrarSeguimientoVisual(request, response);
            return;
        }
        
        // Si es /proyectos, mostrar formulario de creación
        // Cargar lista de materias para el formulario
        try {
            List<Materia> materias = materiaService.listarMaterias()
                    .stream()
                    .map(dto -> {
                        Materia m = new Materia(dto.getNombre(), dto.getDescripcion());
                        m.setId(dto.getId());
                        return m;
                    })
                    .toList();
            request.setAttribute("materias", materias);
        } catch (Exception e) {
            // Si falla, continuar sin materias
            request.setAttribute("materias", List.of());
        }

        // Mostrar formulario para crear un nuevo proyecto
        request.getRequestDispatcher("/jsp/crear_proyecto.jsp").forward(request, response);
    }
    
    /**
     * Muestra la ventana de seguimiento visual de proyectos (HU-2)
     * CA1: Muestra nombre, progreso y barra visual
     * CA2: Lista de tareas con estados
     * CA3: Alertas visuales para proyectos próximos a vencer
     */
    private void mostrarSeguimientoVisual(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Obtener todos los proyectos
        List<Proyecto> proyectos = proyectoService.obtenerProyectos();
        
        // Pasar proyectos a la vista
        request.setAttribute("proyectos", proyectos);
        
        // Redirigir a la vista de seguimiento
        request.getRequestDispatcher("/jsp/seguimiento_proyectos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CASO 1: Crear un nuevo proyecto
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String fechaVencimientoStr = request.getParameter("fechaVencimiento");
        String materiaIdStr = request.getParameter("materiaId");

        //Verificar que el título no esté vacío (CA1 - Escenario 2)
        if (titulo != null && titulo.isEmpty()) {
            request.setAttribute("error", "El nombre del proyecto es obligatorio");
            doGet(request, response); // Recargar formulario con materias
            return;
        }

        // Si viene título, crear proyecto
        if (titulo != null) {
            LocalDate fechaVencimiento = null;
            Materia materia = null;

            // Parsear fecha de vencimiento si viene
            if (fechaVencimientoStr != null && !fechaVencimientoStr.isEmpty()) {
                try {
                    fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
                } catch (DateTimeParseException e) {
                    request.setAttribute("error", "Fecha de vencimiento inválida");
                    doGet(request, response);
                    return;
                }
            }

            // Obtener materia seleccionada
            if (materiaIdStr != null && !materiaIdStr.isEmpty()) {
                try {
                    Long materiaId = Long.parseLong(materiaIdStr);
                    // Buscar la materia por ID
                    materia = materiaService.listarMaterias()
                            .stream()
                            .filter(dto -> dto.getId().equals(materiaId))
                            .map(dto -> {
                                Materia m = new Materia(dto.getNombre(), dto.getDescripcion());
                                m.setId(dto.getId());
                                return m;
                            })
                            .findFirst()
                            .orElse(null);
                } catch (NumberFormatException e) {
                    // Si falla el parseo, continuar sin materia
                }
            }

            // Crear proyecto con los datos disponibles
            Proyecto nuevoProyecto;
            if (materia != null && fechaVencimiento != null) {
                // Constructor completo (CA1 completo)
                nuevoProyecto = new Proyecto(titulo, descripcion, fechaVencimiento, materia);
            } else if (fechaVencimiento != null) {
                // Solo con fecha
                nuevoProyecto = new Proyecto(titulo, descripcion, fechaVencimiento);
            } else {
                // Básico
                nuevoProyecto = new Proyecto(titulo, descripcion);
            }

            // Si hay materia pero no se incluyó en el constructor, asignarla
            if (materia != null && nuevoProyecto.getMateria() == null) {
                nuevoProyecto.setMateria(materia);
            }

            proyectoService.guardarProyecto(nuevoProyecto);
            response.sendRedirect(request.getContextPath() + "/proyectos");
            return;
        }

        // CASO 2: Asociar tarea existente al proyecto
        String idProyectoStr = request.getParameter("idProyecto");
        String idTareaStr = request.getParameter("idTarea");
        String tituloTarea = request.getParameter("tituloTarea");
        String descripcionTarea = request.getParameter("descripcionTarea");
        String fechaVencimientoTareaStr = request.getParameter("fechaVencimiento");

        if (idProyectoStr != null && idTareaStr != null) {
            Long idProyecto = Long.parseLong(idProyectoStr);
            Long idTarea = Long.parseLong(idTareaStr);

            proyectoService.asociarTarea(idProyecto, idTarea);
            response.sendRedirect(request.getContextPath() + "/proyectos");
            return;
        }

        // CASO 3: Crear nueva tarea dentro del proyecto
        if (idProyectoStr != null && tituloTarea != null && descripcionTarea != null && fechaVencimientoTareaStr != null) {
            Long idProyecto = Long.parseLong(idProyectoStr);
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoTareaStr);

            proyectoService.crearTareaDentroDeProyecto(idProyecto, tituloTarea, descripcionTarea, fechaVencimiento);
            response.sendRedirect(request.getContextPath() + "/proyectos");
            return;
        }

        // Manejar el caso en que no se proporcionan los parámetros necesarios
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros insuficientes");
    }

    protected boolean validarParametros(HttpServletRequest request) {
        String idProyectoStr = request.getParameter("idProyecto");
        String idTareaStr = request.getParameter("idTarea");
        return idProyectoStr != null && !idProyectoStr.isEmpty() &&
                idTareaStr != null && !idTareaStr.isEmpty();
    }
}