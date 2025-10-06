package com.gr4.controller;

import com.gr4.dto.AlertaDTO;
import com.gr4.model.Actividad;
import com.gr4.model.Alerta;
import com.gr4.repository.ActividadRepository;
import com.gr4.repository.ActividadRepositoryImpl;
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
    private ActividadRepository actividadRepository;
    private AlertaListener alertaListener;

    @Override
    public void init() throws ServletException {
        super.init();
        alertaRepository = new AlertaRepositoryImpl();
        actividadRepository = new ActividadRepositoryImpl();
        alertaListener = new AlertaListenerImpl(); // Patrón Observer
        System.out.println("✓ GestorAlertasServlet inicializado con Observer");
    }

    /**
     * Maneja peticiones GET - Muestra el formulario de configuración de alertas
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Cargar actividades disponibles para asociar la alerta
            List<Actividad> actividades = actividadRepository.findAll();
            request.setAttribute("actividades", actividades);

            request.getRequestDispatcher("/jsp/configurar-alerta.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al cargar formulario de alertas: " + e.getMessage());
            request.setAttribute("error", "Error al cargar el formulario");
            request.getRequestDispatcher("/jsp/configurar-alerta.jsp").forward(request, response);
        }
    }

    /**
     * Maneja peticiones POST - Procesa la creación de alerta
     * Implementa el flujo del diagrama de secuencia con Patrón Observer
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Recibir AlertaDTO desde el formulario
            AlertaDTO dto = new AlertaDTO();
            dto.setMensaje(request.getParameter("mensaje"));
            dto.setFechaAlerta(request.getParameter("fechaAlerta"));
            dto.setTipo(request.getParameter("tipo"));

            String actividadIdStr = request.getParameter("actividadId");
            if (actividadIdStr != null && !actividadIdStr.isEmpty()) {
                dto.setActividadId(Long.parseLong(actividadIdStr));
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
                List<Actividad> actividades = actividadRepository.findAll();
                request.setAttribute("actividades", actividades);
                request.getRequestDispatcher("/jsp/configurar-alerta.jsp").forward(request, response);
                return;
            }

            // PASO 3: Buscar la actividad asociada
            Optional<Actividad> actividadOpt = actividadRepository.findById(dto.getActividadId());
            if (!actividadOpt.isPresent()) {
                request.setAttribute("error", "Actividad no encontrada");
                List<Actividad> actividades = actividadRepository.findAll();
                request.setAttribute("actividades", actividades);
                request.getRequestDispatcher("/jsp/configurar-alerta.jsp").forward(request, response);
                return;
            }

            Actividad actividad = actividadOpt.get();

            // PASO 4: Crear entidad Alerta desde el DTO
            Alerta alerta = new Alerta();
            alerta.setMensaje(dto.getMensaje());
            alerta.setFechaAlerta(dto.getFechaAlertaAsLocalDateTime());
            alerta.setTipo(dto.getTipo() != null ? dto.getTipo() : "Recordatorio");
            alerta.setActividad(actividad);

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
            request.setAttribute("error", "ID de actividad inválido");
            List<Actividad> actividades = actividadRepository.findAll();
            request.setAttribute("actividades", actividades);
            request.getRequestDispatcher("/jsp/configurar-alerta.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al guardar alerta: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar alerta: " + e.getMessage());
            List<Actividad> actividades = actividadRepository.findAll();
            request.setAttribute("actividades", actividades);
            request.getRequestDispatcher("/jsp/configurar-alerta.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ GestorAlertasServlet destruido");
    }
}