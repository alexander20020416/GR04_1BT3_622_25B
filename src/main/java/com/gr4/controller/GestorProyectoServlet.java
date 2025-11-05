package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Proyecto;
import com.gr4.model.Tarea;
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
import java.util.Optional;

/**
 * Servlet responsable de la gestión de proyectos.
 * Maneja creación de proyectos, asociación de tareas y creación de tareas dentro de proyectos.
 * Cumple con SRP - Solo maneja operaciones relacionadas con proyectos.
 */
@WebServlet(name = "GestorProyectoServlet", urlPatterns = {
    "/proyectos",           // GET: Formulario crear | POST: Crear proyecto
    "/gestionarProyecto",   // POST: Acciones generales
    "/seguimiento",         // GET: Ver todos los proyectos
    "/agregarTarea",        // GET: Vista agregar tarea
    "/editarProyecto",      // GET: Formulario editar | POST: Actualizar
    "/eliminarProyecto",    // POST: Eliminar proyecto
    "/verProyecto",         // GET: Ver detalle del proyecto
    "/cambiarEstadoTarea"   // POST: Cambiar estado de tarea en proyecto
})
public class GestorProyectoServlet extends HttpServlet {

    private ProyectoService proyectoService;
    private MateriaService materiaService;
    private TareaRepository tareaRepository;

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
            this.tareaRepository = new TareaRepositoryImpl();

            // Ahora sí crear el servicio con sus dependencias
            this.proyectoService = new ProyectoService(proyectoRepository, tareaRepository);
        }

        if (this.materiaService == null) {
            this.materiaService = new MateriaService();
        }

        if (this.tareaRepository == null) {
            this.tareaRepository = new TareaRepositoryImpl();
        }

        System.out.println("✓ GestorProyectoServlet inicializado correctamente");
        System.out.println("  - ProyectoService: " + (proyectoService != null ? "OK" : "NULL"));
        System.out.println("  - MateriaService: " + (materiaService != null ? "OK" : "NULL"));
        System.out.println("  - TareaRepository: " + (tareaRepository != null ? "OK" : "NULL"));
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

        // Si es /agregarTarea, mostrar vista para agregar tareas al proyecto (CA2)
        if ("/agregarTarea".equals(path)) {
            System.out.println("➕ Mostrando vista para agregar tarea");
            mostrarVistaAgregarTarea(request, response);
            return;
        }

        // Si es /verProyecto, mostrar detalle del proyecto
        if ("/verProyecto".equals(path)) {
            System.out.println("👁️ Mostrando detalle del proyecto");
            mostrarDetalleProyecto(request, response);
            return;
        }

        // Si es /editarProyecto, mostrar formulario de edición
        if ("/editarProyecto".equals(path)) {
            System.out.println("✏️ Mostrando formulario de edición");
            mostrarFormularioEdicion(request, response);
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
     * Muestra la vista para agregar tareas a un proyecto (CA2)
     * Permite asociar tareas existentes o crear nuevas tareas
     */
    private void mostrarVistaAgregarTarea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("➕ Cargando vista para agregar tarea al proyecto...");

        try {
            // Obtener ID del proyecto
            String proyectoIdStr = request.getParameter("proyectoId");
            if (proyectoIdStr == null || proyectoIdStr.isEmpty()) {
                System.err.println("✗ ID de proyecto no proporcionado");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=ID de proyecto requerido");
                return;
            }

            Long proyectoId = Long.parseLong(proyectoIdStr);
            System.out.println("🔍 Buscando proyecto ID: " + proyectoId);

            // Buscar el proyecto
            Optional<Proyecto> proyectoOpt = proyectoService.buscarProyectoPorId(proyectoId);
            if (!proyectoOpt.isPresent()) {
                System.err.println("✗ Proyecto no encontrado");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Proyecto no encontrado");
                return;
            }

            Proyecto proyecto = proyectoOpt.get();
            System.out.println("✓ Proyecto encontrado: " + proyecto.getTitulo());

            // Obtener tareas disponibles que no estén ya en un proyecto
            List<Tarea> todasLasTareas = tareaRepository.findAll();
            List<Tarea> tareasDisponibles = todasLasTareas.stream()
                    .filter(tarea -> tarea.getProyecto() == null) // Que no estén ya en otro proyecto
                    .toList();

            System.out.println("✓ Tareas disponibles para asociar: " + tareasDisponibles.size());

            // Pasar datos a la vista
            request.setAttribute("proyecto", proyecto);
            request.setAttribute("tareasDisponibles", tareasDisponibles);

            // Redirigir a la vista
            request.getRequestDispatcher("/jsp/agregar_tarea_proyecto.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("✗ Error: ID de proyecto inválido");
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=ID de proyecto inválido");
        } catch (Exception e) {
            System.err.println("✗ Error al cargar vista: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al cargar vista");
        }
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

        String path = request.getServletPath();
        System.out.println("🔍 Path: " + path);

        try {
            // Cambiar estado de tarea
            if ("/cambiarEstadoTarea".equals(path)) {
                cambiarEstadoTarea(request, response);
                return;
            }
            
            // Eliminar proyecto
            if ("/eliminarProyecto".equals(path)) {
                eliminarProyecto(request, response);
                return;
            }

            // Editar proyecto
            if ("/editarProyecto".equals(path)) {
                actualizarProyecto(request, response);
                return;
            }

            // Obtener la acción si viene
            String accion = request.getParameter("accion");
            System.out.println("🔍 Acción recibida: " + accion);

            // CASO 1: Asociar tarea existente al proyecto (CA2 - Escenario 1)
            if ("asociarTarea".equals(accion)) {
                asociarTareaExistente(request, response);
                return;
            }

            // CASO 2: Crear nueva tarea dentro del proyecto (CA2)
            if ("crearTarea".equals(accion)) {
                crearNuevaTareaEnProyecto(request, response);
                return;
            }

            // CASO 3: Crear un nuevo proyecto (CA1)
            crearNuevoProyecto(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error en doPost: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar solicitud: " + e.getMessage());
            doGet(request, response);
        }
    }

    /**
     * Crea un nuevo proyecto (CA1)
     */
    private void crearNuevoProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            Long materiaIdLong = null;

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
                    materiaIdLong = Long.parseLong(materiaIdStr);
                    final Long materiaIdFinal = materiaIdLong;
                    System.out.println("🔍 Buscando materia ID: " + materiaIdFinal);

                    // Buscar la materia por ID
                    materia = materiaService.listarMaterias()
                            .stream()
                            .filter(dto -> dto.getId().equals(materiaIdFinal))
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

            // Redirigir a la materia si viene materiaId, sino al seguimiento
            if (materiaIdLong != null) {
                response.sendRedirect(request.getContextPath() + "/detalleMateria?id=" + materiaIdLong + "&mensaje=Proyecto creado exitosamente");
            } else {
                response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Proyecto creado exitosamente");
            }
            return;
        }

        // Si llegamos aquí, no se proporcionaron parámetros válidos
        System.err.println("✗ Parámetros insuficientes o inválidos");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros insuficientes");
    }

    /**
     * Asocia una tarea existente al proyecto (CA2 - Escenario 1)
     */
    private void asociarTareaExistente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔗 Asociando tarea existente al proyecto...");

        try {
            String idProyectoStr = request.getParameter("idProyecto");
            String idTareaStr = request.getParameter("idTarea");

            if (idProyectoStr == null || idTareaStr == null) {
                System.err.println("✗ Parámetros insuficientes");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Datos incompletos");
                return;
            }

            Long idProyecto = Long.parseLong(idProyectoStr);
            Long idTarea = Long.parseLong(idTareaStr);

            proyectoService.asociarTarea(idProyecto, idTarea);
            System.out.println("✅ Tarea asociada exitosamente");

            response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Tarea asociada al proyecto");

        } catch (NumberFormatException e) {
            System.err.println("✗ Error: IDs inválidos");
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=IDs inválidos");
        } catch (Exception e) {
            System.err.println("✗ Error al asociar tarea: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al asociar tarea");
        }
    }

    /**
     * Crea una nueva tarea dentro del proyecto (CA2)
     */
    private void crearNuevaTareaEnProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("➕ Creando nueva tarea en proyecto...");

        try {
            String idProyectoStr = request.getParameter("idProyecto");
            String tituloTarea = request.getParameter("tituloTarea");
            String descripcionTarea = request.getParameter("descripcionTarea");
            String fechaVencimientoTareaStr = request.getParameter("fechaVencimientoTarea");
            String prioridad = request.getParameter("prioridad");

            if (idProyectoStr == null || tituloTarea == null || fechaVencimientoTareaStr == null) {
                System.err.println("✗ Parámetros insuficientes");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Datos incompletos");
                return;
            }

            Long idProyecto = Long.parseLong(idProyectoStr);
            LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoTareaStr);

            // Obtener el proyecto para heredar su materia
            Optional<Proyecto> proyectoOpt = proyectoService.buscarProyectoPorId(idProyecto);
            if (!proyectoOpt.isPresent()) {
                System.err.println("✗ Proyecto no encontrado");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Proyecto no encontrado");
                return;
            }

            Proyecto proyecto = proyectoOpt.get();

            // Crear la nueva tarea y asociarla directamente al proyecto
            Tarea nuevaTarea = new Tarea(tituloTarea, descripcionTarea, fechaVencimiento);
            if (prioridad != null && !prioridad.isEmpty()) {
                nuevaTarea.setPrioridad(prioridad);
            }

            // Asociar al proyecto
            proyecto.agregarTarea(nuevaTarea);
            proyectoService.guardarProyecto(proyecto);

            System.out.println("✅ Tarea creada y agregada al proyecto exitosamente");

            response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Tarea creada en el proyecto");

        } catch (NumberFormatException | DateTimeParseException e) {
            System.err.println("✗ Error: Datos inválidos");
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Datos inválidos");
        } catch (Exception e) {
            System.err.println("✗ Error al crear tarea: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al crear tarea");
        }
    }

    /**
     * Muestra el formulario de edición de proyecto
     */
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("✏️ Mostrando formulario de edición...");

        try {
            String proyectoIdStr = request.getParameter("id");
            if (proyectoIdStr == null || proyectoIdStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=ID de proyecto requerido");
                return;
            }

            Long proyectoId = Long.parseLong(proyectoIdStr);
            Optional<Proyecto> proyectoOpt = proyectoService.buscarProyectoPorId(proyectoId);

            if (!proyectoOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Proyecto no encontrado");
                return;
            }

            // Cargar materias
            List<Materia> materias = materiaService.listarMaterias()
                    .stream()
                    .map(dto -> {
                        Materia m = new Materia(dto.getNombre(), dto.getDescripcion());
                        m.setId(dto.getId());
                        return m;
                    })
                    .toList();

            request.setAttribute("proyecto", proyectoOpt.get());
            request.setAttribute("materias", materias);
            request.getRequestDispatcher("/jsp/editar_proyecto.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al cargar formulario");
        }
    }

    /**
     * Actualiza un proyecto existente
     */
    private void actualizarProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔄 Actualizando proyecto...");

        try {
            String proyectoIdStr = request.getParameter("id");
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fechaVencimientoStr = request.getParameter("fechaVencimiento");
            String materiaIdStr = request.getParameter("materiaId");

            if (proyectoIdStr == null || titulo == null || titulo.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Datos incompletos");
                return;
            }

            Long proyectoId = Long.parseLong(proyectoIdStr);
            Optional<Proyecto> proyectoOpt = proyectoService.buscarProyectoPorId(proyectoId);

            if (!proyectoOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Proyecto no encontrado");
                return;
            }

            Proyecto proyecto = proyectoOpt.get();
            Long materiaIdAnterior = proyecto.getMateria() != null ? proyecto.getMateria().getId() : null;

            // Actualizar campos
            proyecto.setTitulo(titulo);
            proyecto.setDescripcion(descripcion);

            // Actualizar fecha
            if (fechaVencimientoStr != null && !fechaVencimientoStr.isEmpty()) {
                proyecto.setFechaVencimiento(LocalDate.parse(fechaVencimientoStr));
            }

            // Actualizar materia
            if (materiaIdStr != null && !materiaIdStr.isEmpty()) {
                Long materiaId = Long.parseLong(materiaIdStr);
                final Long materiaIdFinal = materiaId;

                Materia materia = materiaService.listarMaterias()
                        .stream()
                        .filter(dto -> dto.getId().equals(materiaIdFinal))
                        .map(dto -> {
                            Materia m = new Materia(dto.getNombre(), dto.getDescripcion());
                            m.setId(dto.getId());
                            return m;
                        })
                        .findFirst()
                        .orElse(null);

                proyecto.setMateria(materia);
            }

            proyectoService.guardarProyecto(proyecto);
            System.out.println("✅ Proyecto actualizado exitosamente");

            // Redirigir a la materia si tenía una
            if (materiaIdAnterior != null) {
                response.sendRedirect(request.getContextPath() + "/detalleMateria?id=" + materiaIdAnterior + "&mensaje=Proyecto actualizado");
            } else {
                response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Proyecto actualizado");
            }

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al actualizar proyecto");
        }
    }

    /**
     * Elimina un proyecto
     */
    private void eliminarProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🗑️ Eliminando proyecto...");

        try {
            String proyectoIdStr = request.getParameter("id");
            String materiaIdStr = request.getParameter("materiaId");

            if (proyectoIdStr == null || proyectoIdStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=ID de proyecto requerido");
                return;
            }

            Long proyectoId = Long.parseLong(proyectoIdStr);
            boolean eliminado = proyectoService.eliminarProyecto(proyectoId);

            if (eliminado) {
                System.out.println("✅ Proyecto eliminado exitosamente");

                // Redirigir a la materia si viene materiaId
                if (materiaIdStr != null && !materiaIdStr.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/detalleMateria?id=" + materiaIdStr + "&mensaje=Proyecto eliminado");
                } else {
                    response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Proyecto eliminado");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=No se pudo eliminar el proyecto");
            }

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al eliminar proyecto");
        }
    }

    /**
     * Muestra el detalle completo de un proyecto con sus tareas
     */
    private void mostrarDetalleProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("👁️ Mostrando detalle del proyecto...");

        try {
            String proyectoIdStr = request.getParameter("id");
            if (proyectoIdStr == null || proyectoIdStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=ID de proyecto requerido");
                return;
            }

            Long proyectoId = Long.parseLong(proyectoIdStr);
            Optional<Proyecto> proyectoOpt = proyectoService.buscarProyectoPorId(proyectoId);

            if (!proyectoOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Proyecto no encontrado");
                return;
            }

            Proyecto proyecto = proyectoOpt.get();
            System.out.println("✓ Proyecto encontrado: " + proyecto.getTitulo());
            System.out.println("✓ Tareas en el proyecto: " + proyecto.getTareas().size());

            request.setAttribute("proyecto", proyecto);
            request.getRequestDispatcher("/jsp/proyecto_detalle.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al cargar detalle");
        }
    }

    /**
     * Cambia el estado de una tarea dentro de un proyecto
     */
    private void cambiarEstadoTarea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔄 Cambiando estado de tarea...");

        try {
            String tareaIdStr = request.getParameter("tareaId");
            String nuevoEstado = request.getParameter("nuevoEstado");
            String proyectoIdStr = request.getParameter("proyectoId");

            if (tareaIdStr == null || nuevoEstado == null) {
                System.err.println("✗ Parámetros incompletos");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Parámetros incompletos");
                return;
            }

            Long tareaId = Long.parseLong(tareaIdStr);
            
            // Buscar la tarea
            Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
            if (!tareaOpt.isPresent()) {
                System.err.println("✗ Tarea no encontrada");
                response.sendRedirect(request.getContextPath() + "/seguimiento?error=Tarea no encontrada");
                return;
            }

            Tarea tarea = tareaOpt.get();
            System.out.println("✓ Tarea encontrada: " + tarea.getTitulo());
            System.out.println("  Estado anterior: " + tarea.getEstado());
            System.out.println("  Estado nuevo: " + nuevoEstado);

            // Cambiar el estado
            tarea.setEstado(nuevoEstado);
            tareaRepository.save(tarea);

            System.out.println("✅ Estado de tarea actualizado exitosamente");

            // Redirigir al detalle del proyecto
            if (proyectoIdStr != null && !proyectoIdStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/verProyecto?id=" + proyectoIdStr + "&mensaje=Tarea actualizada");
            } else {
                response.sendRedirect(request.getContextPath() + "/seguimiento?mensaje=Tarea actualizada");
            }

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/seguimiento?error=Error al cambiar estado de tarea");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorProyectoServlet destruido");
    }
}