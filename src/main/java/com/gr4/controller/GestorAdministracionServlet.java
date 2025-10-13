package com.gr4.controller;

import com.gr4.dto.TareaDTO;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servlet Controlador para Administración de Tareas
 * GestorAdministracionController del diagrama de clases
 * Implementa: actualizarTarea, reordenar, setOrdenStrategy
 * Basado en el diagrama de clases PlantUML proporcionado
 */
@WebServlet(name = "GestorAdministracionServlet", urlPatterns = {"/administracion", "/organizar"})
public class GestorAdministracionServlet extends HttpServlet {

    private TareaRepository tareaRepository;
    private OrdenStrategy ordenStrategy;

    @Override
    public void init() throws ServletException {
        super.init();
        tareaRepository = new TareaRepositoryImpl();
        ordenStrategy = new OrdenPorPrioridad(); // Estrategia por defecto
        System.out.println("✓ GestorAdministracionServlet inicializado");
    }

    /**
     * CASO DE USO 2: ORGANIZAR TAREAS
     * Implementa el diagrama de secuencia del Caso de Uso "Organizar Tareas"
     * 
     * Flujo según diagrama de secuencia:
     * 1. Usuario selecciona criterio de ordenamiento en la interfaz JSP
     * 2. Servlet obtiene el criterio desde el request
     * 3. Servlet recupera todas las tareas mediante TareaRepository.findAll()
     * 4. Servlet selecciona la estrategia de orden apropiada (Patrón Strategy)
     * 5. Servlet aplica ordenar(tareas) usando la estrategia seleccionada
     * 6. Servlet envía la lista ordenada a la vista JSP para mostrarla al usuario
     * 
     * El Patrón Strategy permite una arquitectura extensible: el controlador no 
     * depende de una forma fija de ordenar tareas, sino que puede cambiar la 
     * estrategia en tiempo de ejecución.
     * 
     * Maneja peticiones GET:
     * - /organizar?orden=X -> vista de organización con Strategy (Caso de Uso 2)
     * - /administracion?action=edit&id=X -> formulario de edición
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        String action = request.getParameter("action");

        // Caso 1: Formulario de edición (MainAdministracionTareas.mostrarEdicion)
        if ("/administracion".equals(path) && "edit".equalsIgnoreCase(action)) {
            mostrarEdicion(request, response);
            return;
        }

        // ==================== CASO DE USO 2: ORGANIZAR TAREAS ====================
        // Implementación del diagrama de secuencia
        try {
            // PASO 1 y 2: Obtener el criterio de ordenamiento desde la interfaz JSP
            String criterioOrden = request.getParameter("orden");
            if (criterioOrden == null || criterioOrden.isEmpty()) {
                criterioOrden = "prioridad"; // Valor por defecto
            }

            System.out.println("📊 [Caso de Uso 2] Criterio de orden seleccionado: " + criterioOrden);

            // PASO 3: Recuperar las tareas mediante TareaRepository.findAll()
            List<Tarea> tareas = tareaRepository.findAll();
            System.out.println("📋 [Caso de Uso 2] Tareas recuperadas del repositorio: " + tareas.size());

            // PASO 4: Seleccionar la estrategia de orden (Patrón Strategy)
            OrdenStrategy estrategiaSeleccionada = seleccionarEstrategia(criterioOrden);
            setOrdenStrategy(estrategiaSeleccionada);
            System.out.println("🎯 [Caso de Uso 2] Estrategia seleccionada: " + estrategiaSeleccionada.getNombre());

            // PASO 5: Aplicar ordenar(tareas) usando la estrategia seleccionada
            List<Tarea> tareasOrdenadas = ordenStrategy.ordenar(tareas);
            System.out.println("✓ [Caso de Uso 2] Tareas ordenadas exitosamente");

            // PASO 6: Enviar la lista ordenada a la vista JSP para mostrarla al usuario
            request.setAttribute("tareas", tareasOrdenadas);
            request.setAttribute("criterioActual", criterioOrden);
            request.setAttribute("nombreEstrategia", ordenStrategy.getNombre());

            System.out.println("🌐 [Caso de Uso 2] Redirigiendo a la vista organizar.jsp");
            request.getRequestDispatcher("/jsp/organizar.jsp").forward(request, response);
            // ========================================================================

        } catch (Exception e) {
            System.err.println("✗ [Caso de Uso 2] Error al organizar tareas: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al organizar las tareas: " + e.getMessage());
            request.getRequestDispatcher("/jsp/organizar.jsp").forward(request, response);
        }
    }

    /**
     * MainAdministracionTareas.mostrarEdicion(tareaId)
     * Muestra el formulario de edición de una tarea
     */
    private void mostrarEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                Optional<Tarea> opt = tareaRepository.findById(id);
                if (opt.isPresent()) {
                    request.setAttribute("tarea", opt.get());
                    request.getRequestDispatcher("/jsp/editar_tarea.jsp").forward(request, response);
                    return;
                } else {
                    request.getSession().setAttribute("mensajeAccion", "Tarea no encontrada para edición.");
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("mensajeAccion", "ID inválido.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/consultar");
    }

    /**
     * PATRÓN STRATEGY - Selección de Estrategia de Ordenamiento
     * 
     * Este método implementa el Patrón Strategy, permitiendo seleccionar
     * dinámicamente el algoritmo de ordenamiento en tiempo de ejecución.
     * 
     * Ventajas del Patrón Strategy:
     * - Elimina condicionales complejos (if/else o switch)
     * - Permite agregar nuevas estrategias sin modificar código existente
     * - Cada estrategia encapsula su propio algoritmo
     * - El cliente (servlet) no necesita conocer detalles de implementación
     * 
     * Estrategias disponibles:
     * - OrdenPorPrioridad: Ordena de mayor a menor (Alta → Media → Baja)
     * - OrdenPorFecha: Ordena por proximidad de fecha de vencimiento
     * - OrdenPorTitulo: Ordena alfabéticamente por título
     * 
     * @param criterio Criterio seleccionado por el usuario ("prioridad", "fecha", "titulo")
     * @return Estrategia de ordenamiento correspondiente al criterio
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
                return new OrdenPorPrioridad(); // Estrategia por defecto
        }
    }

    /**
     * Maneja peticiones POST
     * - action=update -> actualizarTarea (MainAdministracionTareas.onGuardarEdicion)
     * - action=delete -> eliminar tarea
     * - action=reordenar -> reordenar(nuevoOrden)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        
        // Detectar de dónde viene la petición para redirigir correctamente
        String referer = request.getHeader("Referer");
        String redirectUrl = request.getContextPath() + "/consultar";
        
        if (referer != null && referer.contains("/organizar")) {
            redirectUrl = request.getContextPath() + "/organizar";
        }

        if (action == null) {
            response.sendRedirect(redirectUrl);
            return;
        }

        try {
            if ("update".equalsIgnoreCase(action)) {
                // GestorAdministracionController.actualizarTarea(id, dto)
                actualizarTarea(request, response);
            } else if ("delete".equalsIgnoreCase(action) && idParam != null) {
                Long id = Long.parseLong(idParam);
                boolean deleted = tareaRepository.deleteById(id);
                request.getSession().setAttribute("mensajeAccion", 
                    deleted ? "Tarea eliminada correctamente." : "No se encontró la tarea para eliminar.");
            } else if ("reordenar".equalsIgnoreCase(action)) {
                // GestorAdministracionController.reordenar(nuevoOrden)
                reordenar(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensajeAccion", "ID inválido.");
        } catch (Exception e) {
            request.getSession().setAttribute("mensajeAccion", "Error: " + e.getMessage());
        }

        response.sendRedirect(redirectUrl);
    }

    /**
     * GestorAdministracionController.actualizarTarea(id, dto) : Tarea
     * Actualiza una tarea existente usando TareaDTO
     */
    private void actualizarTarea(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        
        if (idParam == null) {
            request.getSession().setAttribute("mensajeAccion", "ID no proporcionado.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Optional<Tarea> opt = tareaRepository.findById(id);
            
            if (!opt.isPresent()) {
                request.getSession().setAttribute("mensajeAccion", "Tarea no encontrada.");
                return;
            }

            Tarea tarea = opt.get();
            
            // Crear TareaDTO desde request (MainAdministracionTareas.onGuardarEdicion)
            TareaDTO dto = new TareaDTO();
            dto.setDescripcion(request.getParameter("descripcion"));
            dto.setEstado(request.getParameter("estado"));
            
            String fechaStr = request.getParameter("fechaVencimiento");
            if (fechaStr != null && !fechaStr.isEmpty()) {
                try {
                    LocalDate fecha = LocalDate.parse(fechaStr);
                    dto.setFechaVencimiento(fecha.atStartOfDay());
                } catch (Exception e) {
                    System.err.println("Error al parsear fecha: " + e.getMessage());
                }
            }

            String ordenStr = request.getParameter("orden");
            if (ordenStr != null && !ordenStr.isEmpty()) {
                try {
                    dto.setOrden(Integer.parseInt(ordenStr));
                } catch (NumberFormatException e) {
                    dto.setOrden(0);
                }
            }

            // Aplicar cambios del DTO a la entidad
            if (dto.getDescripcion() != null) tarea.setDescripcion(dto.getDescripcion());
            if (dto.getEstado() != null) tarea.setEstado(dto.getEstado());
            if (dto.getFechaVencimiento() != null) {
                tarea.setFechaVencimiento(dto.getFechaVencimiento().toLocalDate());
            }
            tarea.setOrden(dto.getOrden());

            // Campos adicionales de Tarea (no en TareaDTO del diagrama)
            String titulo = request.getParameter("titulo");
            String prioridad = request.getParameter("prioridad");
            if (titulo != null) tarea.setTitulo(titulo);
            if (prioridad != null) tarea.setPrioridad(prioridad);

            tareaRepository.save(tarea);
            request.getSession().setAttribute("mensajeAccion", "Tarea actualizada correctamente.");
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensajeAccion", "ID inválido.");
        } catch (Exception e) {
            request.getSession().setAttribute("mensajeAccion", "Error al actualizar: " + e.getMessage());
        }
    }

    /**
     * GestorAdministracionController.reordenar(nuevoOrden : List<Long>) : boolean
     * Reordena las tareas según una lista de IDs
     */
    private boolean reordenar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idsParam = request.getParameter("ids");
        
        if (idsParam == null || idsParam.isEmpty()) {
            request.getSession().setAttribute("mensajeAccion", "No se proporcionaron IDs para reordenar.");
            response.sendRedirect(request.getContextPath() + "/consultar");
            return false;
        }

        try {
            String[] idsArray = idsParam.split(",");
            for (int i = 0; i < idsArray.length; i++) {
                Long id = Long.parseLong(idsArray[i].trim());
                tareaRepository.updateOrden(id, i + 1);
            }
            request.getSession().setAttribute("mensajeAccion", "Tareas reordenadas correctamente.");
            response.sendRedirect(request.getContextPath() + "/consultar");
            return true;
        } catch (Exception e) {
            request.getSession().setAttribute("mensajeAccion", "Error al reordenar: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/consultar");
            return false;
        }
    }

    /**
     * GestorAdministracionController.setOrdenStrategy(s : OrdenStrategy)
     * Establece la estrategia de ordenamiento actual (Patrón Strategy)
     * 
     * Este método permite cambiar la estrategia en tiempo de ejecución,
     * demostrando la flexibilidad del Patrón Strategy.
     * 
     * @param estrategia Nueva estrategia de ordenamiento a utilizar
     */
    public void setOrdenStrategy(OrdenStrategy estrategia) {
        this.ordenStrategy = estrategia;
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorAdministracionServlet destruido");
    }
}