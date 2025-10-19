package com.gr4.controller;

import com.gr4.model.Tarea;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para listar todas las tareas
 */
@WebServlet(name = "ListarTareasServlet", urlPatterns = {"/listar"})
public class ListarTareasServlet extends HttpServlet {

    private TareaRepository tareaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        tareaRepository = new TareaRepositoryImpl();
        System.out.println("✓ ListarTareasServlet inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener todas las tareas
            List<Tarea> tareas = tareaRepository.findAll();
            
            System.out.println("✓ Tareas encontradas: " + tareas.size());

            // Enviar datos a la vista
            request.setAttribute("tareas", tareas);
            request.getRequestDispatcher("/jsp/listar.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("✗ Error al listar tareas: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al listar tareas: " + e.getMessage());
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
        System.out.println("✓ ListarTareasServlet destruido");
    }
}
