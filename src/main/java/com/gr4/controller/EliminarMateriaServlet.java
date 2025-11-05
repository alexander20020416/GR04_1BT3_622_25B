package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.repository.MateriaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet para eliminar materias
 */
@WebServlet(name = "EliminarMateriaServlet", urlPatterns = {"/eliminar-materia"})
public class EliminarMateriaServlet extends BaseServlet {

    private MateriaRepositoryImpl materiaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        materiaRepository = new MateriaRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/listarMateria");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Materia materia = materiaRepository.findById(id);

            if (materia == null) {
                request.setAttribute("error", "Materia no encontrada");
                response.sendRedirect(request.getContextPath() + "/listarMateria");
                return;
            }

            // Eliminar la materia
            materiaRepository.delete(id);

            System.out.println("✓ Materia eliminada: " + materia.getNombre());

            // Redirigir a la lista
            response.sendRedirect(request.getContextPath() + "/listarMateria");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al eliminar la materia: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        }
    }
}
