package com.gr4.controller;

import com.gr4.dto.MateriaDTO;
import com.gr4.service.MateriaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet Controlador para Creacion de Materias
 * Implementacion de la Epic Gestión Avanzada por Materias
 */

@WebServlet(name = "CrearMateriaServlet", urlPatterns = {"/materias"})
public class CrearMateriaServlet extends BaseServlet {

    private MateriaService materiaService = new MateriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/jsp/materia-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        try {
            MateriaDTO dto = new MateriaDTO(nombre, descripcion);
            MateriaDTO resultado = materiaService.crearMateria(dto);

            request.setAttribute("materia", resultado);
            request.getRequestDispatcher("/jsp/materia-exito.jsp").forward(request, response);
        }catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/materia-form.jsp").forward(request, response);
        }
    }

}
