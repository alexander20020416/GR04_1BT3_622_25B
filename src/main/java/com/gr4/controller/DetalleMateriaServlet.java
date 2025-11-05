package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.repository.MateriaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DetalleMateriaServlet", urlPatterns = {"/detalleMateria"})
public class DetalleMateriaServlet extends BaseServlet {

    private MateriaRepositoryImpl materiaRepo;

    @Override
    public void init() {
        materiaRepo = new MateriaRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/listarMateria");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Materia materia = materiaRepo.findById(id);

            if (materia == null) {
                response.sendRedirect(request.getContextPath() + "/listarMateria");
                return;
            }

            // Pasar la materia al JSP
            request.setAttribute("materia", materia);

            // TODO: Cargar las tareas de esta materia
            // List<Tarea> tareas = tareaRepo.findByMateriaId(id);
            // request.setAttribute("tareas", tareas);

            request.getRequestDispatcher("/jsp/materia-detalle.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        }
    }
}