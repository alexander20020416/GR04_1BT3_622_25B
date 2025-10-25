package com.gr4.controller;

import com.gr4.model.Tarea;
import com.gr4.service.CalendarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GestorCalendarioServlet", urlPatterns = {"/calendario"})
public class GestorCalendarioServlet extends BaseServlet {

    private CalendarioService calendarioService;
    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void init() throws ServletException {
        super.init();
        calendarioService = new CalendarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener fecha seleccionada o usar la actual
            String fechaStr = request.getParameter("fecha");
            LocalDate fechaSeleccionada = fechaStr != null ? 
                LocalDate.parse(fechaStr, FECHA_FORMATTER) : 
                LocalDate.now();

            // Obtener todas las tareas
            List<Tarea> todasLasTareas = tareaRepository.findAll();

            // Obtener tareas del día
            List<Tarea> tareasDia = calendarioService.obtenerTareasPorDia(todasLasTareas, fechaSeleccionada);

            // Obtener inicio y fin de semana
            LocalDate inicioSemana = fechaSeleccionada.minusDays(fechaSeleccionada.getDayOfWeek().getValue() - 1);
            LocalDate finSemana = inicioSemana.plusDays(6);

            // Obtener tareas de la semana
            List<Tarea> tareasSemana = calendarioService.obtenerTareasPorSemana(todasLasTareas, inicioSemana, finSemana);

            // Agrupar tareas por fecha
            Map<LocalDate, List<Tarea>> tareasAgrupadas = calendarioService.agruparTareasPorFecha(tareasSemana);

            // Detectar conflictos
            boolean hayConflictos = calendarioService.detectarConflictos(tareasDia, fechaSeleccionada);

            // Contar tareas pendientes
            long tareasPendientes = calendarioService.contarTareasPendientes(tareasDia, fechaSeleccionada);

            // Enviar datos a la vista
            request.setAttribute("fechaSeleccionada", fechaSeleccionada);
            request.setAttribute("inicioSemana", inicioSemana);
            request.setAttribute("finSemana", finSemana);
            request.setAttribute("tareasDia", tareasDia);
            request.setAttribute("tareasSemana", tareasSemana);
            request.setAttribute("tareasAgrupadas", tareasAgrupadas);
            request.setAttribute("hayConflictos", hayConflictos);
            request.setAttribute("tareasPendientes", tareasPendientes);

            // Mostrar la vista
            request.getRequestDispatcher("/jsp/calendario.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✘ Error en el calendario: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al mostrar el calendario: " + e.getMessage());
            request.getRequestDispatcher("/jsp/calendario.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}