package com.gr4.controller;

import com.gr4.repository.ActividadRepository;
import com.gr4.repository.ActividadRepositoryImpl;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminResetEstadosServlet", urlPatterns = {"/admin/reset-estados"})
public class AdminResetEstadosServlet extends HttpServlet {

    private ActividadRepository actividadRepository;
    private TareaRepository tareaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        actividadRepository = new ActividadRepositoryImpl();
        tareaRepository = new TareaRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Actualizar todas las actividades
            java.util.List<com.gr4.model.Actividad> actividades = actividadRepository.findAll();
            for (com.gr4.model.Actividad a : actividades) {
                a.setEstado("Pendiente");
                actividadRepository.save(a);
            }

            // Actualizar todas las tareas
            java.util.List<com.gr4.model.Tarea> tareas = tareaRepository.findAll();
            for (com.gr4.model.Tarea t : tareas) {
                t.setEstado("Pendiente");
                tareaRepository.save(t);
            }

            response.getWriter().write("Estados reseteados a Pendiente. Actividades: " + actividades.size() + ", Tareas: " + tareas.size());
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("Error al resetear estados: " + e.getMessage());
        }
    }
}
