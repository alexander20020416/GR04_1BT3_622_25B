package com.gr4.controller;

import com.gr4.model.Actividad;
import com.gr4.repository.ActividadRepository;
import com.gr4.repository.ActividadRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para listar todas las actividades
 */
@WebServlet(name = "ListarActividadesServlet", urlPatterns = {"/listar"})
public class ListarActividadesServlet extends HttpServlet {

    private ActividadRepository actividadRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        actividadRepository = new ActividadRepositoryImpl();
        System.out.println("✓ ListarActividadesServlet inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener todas las actividades
            List<Actividad> actividades = actividadRepository.findAll();
            
            System.out.println("✓ Actividades encontradas: " + actividades.size());

            // Enviar datos a la vista
            request.setAttribute("actividades", actividades);
            request.getRequestDispatcher("/jsp/listar.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al listar actividades: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al listar actividades: " + e.getMessage());
            request.getRequestDispatcher("/jsp/listar.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ ListarActividadesServlet destruido");
    }
}
