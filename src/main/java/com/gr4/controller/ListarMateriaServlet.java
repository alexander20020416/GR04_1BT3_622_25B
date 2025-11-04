package com.gr4.controller;

import com.gr4.dto.MateriaDTO;
import com.gr4.service.MateriaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarTareasServlet", urlPatterns = {"/listarMateria"})
public class ListarMateriaServlet extends BaseServlet {

    private MateriaService materiaService = new MateriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* Obtener todas las materias desde el servicio */
        List<MateriaDTO> materias = materiaService.listarMaterias();

        request.setAttribute("materias", materias);

        request.getRequestDispatcher("/jsp/materia-lista.jsp").forward(request, response);
    }
}