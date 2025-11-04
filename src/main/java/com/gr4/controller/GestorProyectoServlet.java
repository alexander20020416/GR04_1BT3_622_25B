package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Proyecto;
import com.gr4.service.MateriaService;
import com.gr4.service.ProyectoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GestorProyectoServlet extends HttpServlet {

    private final ProyectoService proyectoService;
    private final MateriaService materiaService;

    public GestorProyectoServlet(ProyectoService proyectoService, MateriaService materiaService) {
        this.proyectoService = proyectoService;
        this.materiaService = materiaService;
    }

    // Constructor para compatibilidad (sin MateriaService)
    public GestorProyectoServlet(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
        this.materiaService = new MateriaService();
    }

    public boolean validarProyectoDTO(HttpServletRequest request) {
        String nombre = request.getParameter("nombre");
        String fechaInicio = request.getParameter("fechaInicio");

        if (nombre == null || nombre.isEmpty()) {
            return false; // Nombre vacío
        }

        if (fechaInicio == null || !isValidDate(fechaInicio)) {
            return false; // Fecha inválida
        }

        return true; // Datos válidos
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cargar lista de materias para el formulario (CA1 - Escenario 1)
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
        request.getRequestDispatcher("/WEB-INF/views/crear_proyecto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CASO 1: Crear un nuevo proyecto
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String fechaVencimientoStr = request.getParameter("fechaVencimiento");
        String materiaIdStr = request.getParameter("materiaId");

        // Verificar que el título no esté vacío (CA1 - Escenario 2)
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

}