package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Proyecto;
import com.gr4.repository.ProyectoRepository;
import com.gr4.repository.ProyectoRepositoryImpl;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;
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

    private ProyectoService proyectoService;
    private MateriaService materiaService;

    // ✅ Constructor vacío requerido por Tomcat
    public GestorProyectoServlet() {
        // Constructor vacío - las dependencias se inicializan en init()
    }

    // Constructor con parámetros para testing (opcional)
    public GestorProyectoServlet(ProyectoService proyectoService, MateriaService materiaService) {
        this.proyectoService = proyectoService;
        this.materiaService = materiaService;
    }

    @Override
    public void init() throws ServletException {
        super.init();

        // ✅ Inicializar servicios si no fueron inyectados
        if (this.proyectoService == null) {
            // Crear los repositorios primero
            ProyectoRepository proyectoRepository = new ProyectoRepositoryImpl();
            TareaRepository tareaRepository = new TareaRepositoryImpl();

            // Ahora sí crear el servicio con sus dependencias
            this.proyectoService = new ProyectoService(proyectoRepository, tareaRepository);
        }

        if (this.materiaService == null) {
            this.materiaService = new MateriaService();
        }

        System.out.println("✓ GestorProyectoServlet inicializado correctamente");
        System.out.println("  - ProyectoService: " + (proyectoService != null ? "OK" : "NULL"));
        System.out.println("  - MateriaService: " + (materiaService != null ? "OK" : "NULL"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("📥 GestorProyectoServlet GET - Path: " + path);

        // Si es /seguimiento, mostrar vista de seguimiento visual
        if ("/seguimiento".equals(path)) {
            System.out.println("📊 Mostrando seguimiento visual");
            mostrarSeguimientoVisual(request, response);
            return;
        }

        // Capturar materiaId si viene
        String materiaIdParam = request.getParameter("materiaId");
        System.out.println("🔍 MateriaId recibido: " + materiaIdParam);

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
            System.out.println("📚 Materias cargadas: " + materias.size());

            // Preseleccionar materia si viene el parámetro
            if (materiaIdParam != null && !materiaIdParam.isEmpty()) {
                request.setAttribute("materiaIdPreseleccionada", materiaIdParam);
                System.out.println("✓ Materia preseleccionada: " + materiaIdParam);
            }

        } catch (Exception e) {
            System.err.println("✗ Error al cargar materias: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("materias", List.of());
        }

        // Mostrar formulario para crear un nuevo proyecto
        System.out.println("➡️ Redirigiendo a crear_proyecto.jsp");
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

        System.out.println("📊 Cargando proyectos para seguimiento...");

        try {
            // Obtener todos los proyectos
            List<Proyecto> proyectos = proyectoService.obtenerProyectos();
            System.out.println("✓ Proyectos encontrados: " + proyectos.size());

            // Pasar proyectos a la vista
            request.setAttribute("proyectos", proyectos);

            // Redirigir a la vista de seguimiento
            request.getRequestDispatcher("/jsp/seguimiento_proyectos.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al cargar proyectos: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar proyectos: " + e.getMessage());
            request.setAttribute("proyectos", List.of());
            request.getRequestDispatcher("/jsp/seguimiento_proyectos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("📥 GestorProyectoServlet POST");

        try {
            // CASO 1: Crear un nuevo proyecto
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fechaVencimientoStr = request.getParameter("fechaVencimiento");
            String materiaIdStr = request.getParameter("materiaId");

            System.out.println("📋 Datos recibidos:");
            System.out.println("  - Título: " + titulo);
            System.out.println("  - Descripción: " + descripcion);
            System.out.println("  - Fecha: " + fechaVencimientoStr);
            System.out.println("  - MateriaId: " + materiaIdStr);

            // Verificar que el título no esté vacío (CA1 - Escenario 2)
            if (titulo != null && titulo.isEmpty()) {
                System.err.println("✗ Título vacío");
                request.setAttribute("error", "El nombre del proyecto es obligatorio");
                doGet(request, response);
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
                        System.out.println("✓ Fecha parseada: " + fechaVencimiento);
                    } catch (DateTimeParseException e) {
                        System.err.println("✗ Error al parsear fecha: " + e.getMessage());
                        request.setAttribute("error", "Fecha de vencimiento inválida");
                        doGet(request, response);
                        return;
                    }
                }

                // Obtener materia seleccionada
                if (materiaIdStr != null && !materiaIdStr.isEmpty()) {
                    try {
                        Long materiaId = Long.parseLong(materiaIdStr);
                        System.out.println("🔍 Buscando materia ID: " + materiaId);

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

                        if (materia != null) {
                            System.out.println("✓ Materia encontrada: " + materia.getNombre());
                        } else {
                            System.err.println("⚠️ Materia no encontrada");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("✗ Error al parsear materiaId: " + e.getMessage());
                    }
                }

                // Crear proyecto con los datos disponibles
                Proyecto nuevoProyecto;
                if (materia != null && fechaVencimiento != null) {
                    nuevoProyecto = new Proyecto(titulo, descripcion, fechaVencimiento, materia);
                    System.out.println("✓ Proyecto creado con materia y fecha");
                } else if (fechaVencimiento != null) {
                    nuevoProyecto = new Proyecto(titulo, descripcion, fechaVencimiento);
                    System.out.println("✓ Proyecto creado solo con fecha");
                } else {
                    nuevoProyecto = new Proyecto(titulo, descripcion);
                    System.out.println("✓ Proyecto creado básico");
                }

                // Si hay materia pero no se incluyó en el constructor, asignarla
                if (materia != null && nuevoProyecto.getMateria() == null) {
                    nuevoProyecto.setMateria(materia);
                }

                // Guardar el proyecto
                proyectoService.guardarProyecto(nuevoProyecto);
                System.out.println("✅ Proyecto guardado exitosamente");

                response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Proyecto creado exitosamente");
                return;
            }

            // CASO 2: Asociar tarea existente al proyecto
            String idProyectoStr = request.getParameter("idProyecto");
            String idTareaStr = request.getParameter("idTarea");

            if (idProyectoStr != null && idTareaStr != null) {
                Long idProyecto = Long.parseLong(idProyectoStr);
                Long idTarea = Long.parseLong(idTareaStr);

                proyectoService.asociarTarea(idProyecto, idTarea);
                System.out.println("✓ Tarea asociada al proyecto");
                response.sendRedirect(request.getContextPath() + "/seguimiento");
                return;
            }

            // CASO 3: Crear nueva tarea dentro del proyecto
            String tituloTarea = request.getParameter("tituloTarea");
            String descripcionTarea = request.getParameter("descripcionTarea");
            String fechaVencimientoTareaStr = request.getParameter("fechaVencimientoTarea");

            if (idProyectoStr != null && tituloTarea != null && descripcionTarea != null && fechaVencimientoTareaStr != null) {
                Long idProyecto = Long.parseLong(idProyectoStr);
                LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoTareaStr);

                proyectoService.crearTareaDentroDeProyecto(idProyecto, tituloTarea, descripcionTarea, fechaVencimiento);
                System.out.println("✓ Tarea creada dentro del proyecto");
                response.sendRedirect(request.getContextPath() + "/seguimiento");
                return;
            }

            // Si llegamos aquí, no se proporcionaron parámetros válidos
            System.err.println("✗ Parámetros insuficientes o inválidos");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros insuficientes");

        } catch (Exception e) {
            System.err.println("✗ Error en doPost: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar solicitud: " + e.getMessage());
            doGet(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorProyectoServlet destruido");
    }
}