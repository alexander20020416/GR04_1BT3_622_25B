package com.gr4.controller;

import com.gr4.dto.MateriaDTO;
import com.gr4.model.Alerta;
import com.gr4.repository.AlertaRepositoryImpl;
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
    private AlertaRepositoryImpl alertaRepository = new AlertaRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* Obtener todas las materias desde el servicio */
        List<MateriaDTO> materias = materiaService.listarMaterias();
        request.setAttribute("materias", materias);

        /* Obtener el número de alertas activas */
        List<Alerta> todasAlertas = alertaRepository.findAll();
        long alertasActivas = todasAlertas.stream().filter(a -> Boolean.TRUE.equals(a.getActiva())).count();
        request.setAttribute("numAlertas", alertasActivas);

        request.getRequestDispatcher("/jsp/materia-lista.jsp").forward(request, response);
    }
}