package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.repository.MateriaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detalleMateria")  // USAR ESTE PATRÓN COMO listarMateria
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

        // Validar que se recibió el ID
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/listarMateria");
            return;
        }

        try {
            // Convertir el ID a Long
            Long id = Long.parseLong(idParam);

            // Buscar la materia en la base de datos
            Materia materia = materiaRepo.findById(id);

            // Validar que la materia existe
            if (materia == null) {
                request.setAttribute("error", "La materia no fue encontrada");
                response.sendRedirect(request.getContextPath() + "/listarMateria");
                return;
            }

            // Pasar la materia al JSP
            request.setAttribute("materia", materia);

            // Forward al JSP de detalle
            request.getRequestDispatcher("/jsp/materia-detalle.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Si el ID no es un número válido
            request.setAttribute("error", "ID de materia inválido");
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        } catch (Exception e) {
            // Otros errores
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar la materia: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        }
    }
}