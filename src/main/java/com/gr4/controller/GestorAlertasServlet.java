package com.gr4.controller;

import com.gr4.dto.AlertaDTO;
import com.gr4.model.Tarea;
import com.gr4.model.Alerta;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;
import com.gr4.repository.AlertaRepository;
import com.gr4.repository.AlertaRepositoryImpl;
import com.gr4.observer.AlertaListener;
import com.gr4.observer.AlertaListenerImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet Controlador para Configurar Alertas
 * Implementa el caso de uso "Configurar Alertas" del Incremento 2
 * Aplica el Patrón Observer para notificar cuando se crean alertas
 * Basado en el diagrama de secuencia correspondiente
 */
@WebServlet(name = "GestorAlertasServlet", urlPatterns = {"/alertas"})
public class GestorAlertasServlet extends HttpServlet {

    private AlertaRepository alertaRepository;
    private TareaRepository tareaRepository;
    private AlertaListener alertaListener;

    @Override
    public void init() throws ServletException {
        super.init();
        alertaRepository = new AlertaRepositoryImpl();
        tareaRepository = new TareaRepositoryImpl();
        alertaListener = new AlertaListenerImpl(); // Patrón Observer
        System.out.println("✓ GestorAlertasServlet inicializado con Observer");
    }

    /**
     * Maneja peticiones GET - Muestra el listado de alertas o el formulario según el parámetro
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            
            // Si action=crear, mostrar formulario de creación
            if ("crear".equals(action)) {
                List<Tarea> tareas = tareaRepository.findAll();
                request.setAttribute("tareas", tareas);
                request.getRequestDispatcher("/jsp/configurar_alerta.jsp").forward(request, response);
                return;
            }
            
            // Si action=editar, mostrar formulario de edición
            if ("editar".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Optional<Alerta> alertaOpt = alertaRepository.findById(id);
                    if (alertaOpt.isPresent()) {
                        request.setAttribute("alerta", alertaOpt.get());
                        List<Tarea> tareas = tareaRepository.findAll();
                        request.setAttribute("tareas", tareas);
                        request.getRequestDispatcher("/jsp/editar_alerta.jsp").forward(request, response);
                        return;
                    }
                }
                request.setAttribute("error", "Alerta no encontrada");
            }
            
            // Si action=activas, devolver JSON con alertas activas (para la campana)
            if ("activas".equals(action)) {
                handleGetAlertasActivas(request, response);
                return;
            }
            
            // Si action=count, devolver JSON con el conteo (para el badge)
            if ("count".equals(action)) {
                handleGetAlertasCount(request, response);
                return;
            }
            
            // Por defecto, mostrar listado de alertas
            List<Alerta> alertas = alertaRepository.findAll();
            request.setAttribute("alertas", alertas);
            
            System.out.println("📋 Alertas encontradas: " + alertas.size());
            for (Alerta a : alertas) {
                System.out.println("  - ID: " + a.getId() + " | " + a.getMensaje() + " | " + a.getTipo());
            }
            
            request.getRequestDispatcher("/jsp/listar_alertas.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al cargar alertas: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar alertas");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }

    /**
     * Maneja peticiones POST - Procesa la creación/edición/toggle/eliminación de alertas
     * Implementa el flujo del diagrama de secuencia con Patrón Observer
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        
        // Toggle activar/desactivar alerta (AJAX)
        if ("toggle".equals(action)) {
            handleToggleAlerta(request, response);
            return;
        }
        
        // Eliminar alerta (AJAX)
        if ("eliminar".equals(action)) {
            handleEliminarAlerta(request, response);
            return;
        }
        
        // Actualizar alerta existente
        if ("actualizar".equals(action)) {
            handleActualizarAlerta(request, response);
            return;
        }

        // Crear nueva alerta (lógica original)
        handleCrearAlerta(request, response);
    }

    /**
     * Maneja la creación de una nueva alerta
     */
    private void handleCrearAlerta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // PASO 1: Recibir AlertaDTO desde el formulario
            AlertaDTO dto = new AlertaDTO();
            dto.setMensaje(request.getParameter("mensaje"));
            dto.setFechaAlerta(request.getParameter("fechaAlerta"));
            dto.setTipo(request.getParameter("tipo"));

            String tareaIdStr = request.getParameter("tareaId");
            if (tareaIdStr != null && !tareaIdStr.isEmpty()) {
                dto.setTareaId(Long.parseLong(tareaIdStr));
            }

            System.out.println("📥 AlertaDTO recibido: " + dto);

            // PASO 2: Validar el DTO (según diagrama de secuencia - CP03, CP08)
            if (!dto.validar()) {
                String mensajeError = "Datos inválidos. ";
                if (dto.getFechaAlertaAsLocalDateTime() != null &&
                        !dto.getFechaAlertaAsLocalDateTime().isAfter(java.time.LocalDateTime.now())) {
                    mensajeError += "La fecha debe ser futura.";
                } else {
                    mensajeError += "Complete todos los campos correctamente.";
                }

                request.setAttribute("error", mensajeError);
                List<Tarea> tareas = tareaRepository.findAll();
                request.setAttribute("tareas", tareas);
                request.getRequestDispatcher("/jsp/configurar_alerta.jsp").forward(request, response);
                return;
            }

            // PASO 3: Buscar la tarea asociada
            Optional<Tarea> tareaOpt = tareaRepository.findById(dto.getTareaId());
            if (!tareaOpt.isPresent()) {
                request.setAttribute("error", "Tarea no encontrada");
                List<Tarea> tareas = tareaRepository.findAll();
                request.setAttribute("tareas", tareas);
                request.getRequestDispatcher("/jsp/configurar_alerta.jsp").forward(request, response);
                return;
            }

            Tarea tarea = tareaOpt.get();

            // PASO 4: Crear entidad Alerta desde el DTO
            Alerta alerta = new Alerta();
            alerta.setMensaje(dto.getMensaje());
            alerta.setFechaAlerta(dto.getFechaAlertaAsLocalDateTime());
            alerta.setTipo(dto.getTipo() != null ? dto.getTipo() : "Recordatorio");
            alerta.setTarea(tarea);

            // PASO 5: Guardar en el repositorio (según diagrama - CP05)
            Alerta alertaGuardada = alertaRepository.save(alerta);

            System.out.println("✓ Alerta guardada: " + alertaGuardada);

            // PASO 6: Notificar a los listeners (Patrón Observer - CP06)
            alertaListener.onAlertaCreada(alertaGuardada);

            // PASO 7: Redirigir con mensaje de éxito
            request.setAttribute("mensaje", "Alerta configurada exitosamente");
            request.setAttribute("alerta", alertaGuardada);
            request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("✗ Error en formato de número: " + e.getMessage());
            request.setAttribute("error", "ID de tarea inválido");
            List<Tarea> tareas = tareaRepository.findAll();
            request.setAttribute("tareas", tareas);
            request.getRequestDispatcher("/jsp/configurar_alerta.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al guardar alerta: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar alerta: " + e.getMessage());
            List<Tarea> tareas = tareaRepository.findAll();
            request.setAttribute("tareas", tareas);
            request.getRequestDispatcher("/jsp/configurar_alerta.jsp").forward(request, response);
        }
    }

    /**
     * Maneja la actualización de una alerta existente
     */
    private void handleActualizarAlerta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long alertaId = Long.parseLong(request.getParameter("alertaId"));
            Optional<Alerta> alertaOpt = alertaRepository.findById(alertaId);
            
            if (!alertaOpt.isPresent()) {
                request.setAttribute("error", "Alerta no encontrada");
                response.sendRedirect(request.getContextPath() + "/alertas");
                return;
            }
            
            Alerta alerta = alertaOpt.get();
            
            // Actualizar campos
            alerta.setMensaje(request.getParameter("mensaje"));
            alerta.setFechaAlerta(java.time.LocalDateTime.parse(request.getParameter("fechaAlerta")));
            alerta.setTipo(request.getParameter("tipo"));
            
            String tareaIdStr = request.getParameter("tareaId");
            if (tareaIdStr != null && !tareaIdStr.isEmpty()) {
                Optional<Tarea> tareaOpt = tareaRepository.findById(Long.parseLong(tareaIdStr));
                if (tareaOpt.isPresent()) {
                    alerta.setTarea(tareaOpt.get());
                }
            }
            
            alertaRepository.save(alerta);
            
            System.out.println("✓ Alerta actualizada: " + alerta.getId());
            
            request.getSession().setAttribute("mensaje", "Alerta actualizada exitosamente");
            response.sendRedirect(request.getContextPath() + "/alertas");
            
        } catch (Exception e) {
            System.err.println("✗ Error al actualizar alerta: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar alerta");
            response.sendRedirect(request.getContextPath() + "/alertas");
        }
    }

    /**
     * Maneja el toggle de activación/desactivación de alertas (AJAX)
     */
    private void handleToggleAlerta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            Long alertaId = Long.parseLong(request.getParameter("alertaId"));
            boolean activa = Boolean.parseBoolean(request.getParameter("activa"));
            
            Optional<Alerta> alertaOpt = alertaRepository.findById(alertaId);
            
            if (alertaOpt.isPresent()) {
                Alerta alerta = alertaOpt.get();
                alerta.setActiva(activa);
                alertaRepository.save(alerta);
                
                // Notificar al observer
                if (activa) {
                    alertaListener.onAlertaActivada(alerta);
                } else {
                    alertaListener.onAlertaDesactivada(alerta);
                }
                
                System.out.println("✓ Alerta " + (activa ? "activada" : "desactivada") + ": " + alertaId);
                
                response.getWriter().write("{\"success\": true}");
            } else {
                response.getWriter().write("{\"success\": false, \"error\": \"Alerta no encontrada\"}");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error al toggle alerta: " + e.getMessage());
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Maneja la eliminación de alertas (AJAX)
     */
    private void handleEliminarAlerta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            Long alertaId = Long.parseLong(request.getParameter("id"));
            
            boolean eliminada = alertaRepository.deleteById(alertaId);
            
            if (eliminada) {
                System.out.println("✓ Alerta eliminada: " + alertaId);
                response.getWriter().write("{\"success\": true}");
            } else {
                response.getWriter().write("{\"success\": false, \"error\": \"No se pudo eliminar\"}");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error al eliminar alerta: " + e.getMessage());
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Obtiene alertas activas para el dropdown de notificaciones (AJAX)
     */
    private void handleGetAlertasActivas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<Alerta> alertas = alertaRepository.findActivas();
            
            StringBuilder json = new StringBuilder();
            json.append("{\"alertas\": [");
            
            for (int i = 0; i < alertas.size(); i++) {
                Alerta a = alertas.get(i);
                if (i > 0) json.append(",");
                
                json.append("{")
                    .append("\"id\": ").append(a.getId()).append(",")
                    .append("\"mensaje\": \"").append(escaparJson(a.getMensaje())).append("\",")
                    .append("\"tipo\": \"").append(a.getTipo()).append("\",")
                    .append("\"fechaAlerta\": \"").append(a.getFechaAlerta().toString()).append("\"")
                    .append("}");
            }
            
            json.append("]}");
            
            response.getWriter().write(json.toString());
            
        } catch (Exception e) {
            System.err.println("✗ Error al obtener alertas activas: " + e.getMessage());
            response.getWriter().write("{\"alertas\": []}");
        }
    }

    /**
     * Obtiene el conteo de alertas activas para el badge (AJAX)
     */
    private void handleGetAlertasCount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            List<Alerta> alertas = alertaRepository.findActivas();
            response.getWriter().write("{\"count\": " + alertas.size() + "}");
            
        } catch (Exception e) {
            System.err.println("✗ Error al contar alertas: " + e.getMessage());
            response.getWriter().write("{\"count\": 0}");
        }
    }

    /**
     * Escapa caracteres especiales para JSON
     */
    private String escaparJson(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorAlertasServlet destruido");
    }
}