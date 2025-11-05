package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Tarea;
import com.gr4.repository.MateriaRepositoryImpl;
import com.gr4.repository.TareaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DetalleMateriaServlet", urlPatterns = {"/detalleMateria"})
public class DetalleMateriaServlet extends BaseServlet {

    private MateriaRepositoryImpl materiaRepo;
    private TareaRepositoryImpl tareaRepo;

    @Override
    public void init() {
        materiaRepo = new MateriaRepositoryImpl();
        tareaRepo = new TareaRepositoryImpl();
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

            // Cargar las tareas de esta materia
            List<Tarea> tareas = tareaRepo.findByMateriaId(id);
            request.setAttribute("tareas", tareas);

            request.getRequestDispatcher("/jsp/materia-detalle.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        }
    }
}