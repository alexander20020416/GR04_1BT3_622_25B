package com.gr4.controller;

import com.gr4.dto.MateriaDTO;
import com.gr4.model.Materia;
import com.gr4.repository.MateriaRepositoryImpl;
import com.gr4.service.MateriaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet para editar materias existentes
 */
@WebServlet(name = "EditarMateriaServlet", urlPatterns = {"/editar-materia"})
public class EditarMateriaServlet extends BaseServlet {

    private MateriaRepositoryImpl materiaRepository;
    private MateriaService materiaService;

    @Override
    public void init() throws ServletException {
        super.init();
        materiaRepository = new MateriaRepositoryImpl();
        materiaService = new MateriaService(materiaRepository);
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
            Materia materia = materiaRepository.findById(id);

            if (materia == null) {
                request.setAttribute("error", "Materia no encontrada");
                response.sendRedirect(request.getContextPath() + "/listarMateria");
                return;
            }

            // Convertir a DTO para enviar al JSP
            MateriaDTO dto = new MateriaDTO(materia.getNombre(), materia.getDescripcion());
            dto.setId(materia.getId());
            dto.setCodigo(materia.getCodigo());
            dto.setColor(materia.getColor());

            request.setAttribute("materia", dto);
            request.setAttribute("isEdit", true);
            request.getRequestDispatcher("/jsp/materia-form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar la materia");
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        }
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

            // Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String codigo = request.getParameter("codigo");
            String color = request.getParameter("color");

            // Validar nombre obligatorio
            if (nombre == null || nombre.trim().isEmpty()) {
                MateriaDTO dto = new MateriaDTO(materia.getNombre(), materia.getDescripcion());
                dto.setId(materia.getId());
                dto.setCodigo(materia.getCodigo());
                dto.setColor(materia.getColor());
                request.setAttribute("materia", dto);
                request.setAttribute("isEdit", true);
                request.setAttribute("error", "El nombre de la materia es obligatorio");
                request.getRequestDispatcher("/jsp/materia-form.jsp").forward(request, response);
                return;
            }

            // Actualizar datos de la materia
            materia.setNombre(nombre.trim());
            materia.setDescripcion(descripcion != null ? descripcion.trim() : "");
            materia.setCodigo(codigo != null ? codigo.trim() : null);
            materia.setColor(color != null ? color.trim() : null);

            // Guardar cambios
            materiaRepository.save(materia);

            System.out.println("✓ Materia actualizada: " + materia.getNombre());

            // Redirigir al detalle de la materia
            response.sendRedirect(request.getContextPath() + "/detalleMateria?id=" + materia.getId());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar la materia: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/listarMateria");
        }
    }
}
