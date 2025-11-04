package com.gr4.controller;

import com.gr4.model.Tarea;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet para cambiar el estado de una tarea
 * Implementa el principio SRP: responsabilidad única de actualizar el estado
 */
@WebServlet(name = "CambiarEstadoTareaServlet", urlPatterns = {"/cambiar-estado"})
public class CambiarEstadoTareaServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener parámetros
            String idParam = request.getParameter("id");
            String nuevoEstado = request.getParameter("estado");

            // Validar parámetros
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/organizar?error=ID no proporcionado");
                return;
            }

            if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/organizar?error=Estado no proporcionado");
                return;
            }

            // Validar que el estado sea válido
            if (!esEstadoValido(nuevoEstado)) {
                response.sendRedirect(request.getContextPath() + "/organizar?error=Estado inválido");
                return;
            }

            try {
                Long id = Long.parseLong(idParam);

                // Buscar la tarea
                Optional<Tarea> optionalTarea = tareaRepository.findById(id);

                if (optionalTarea.isPresent()) {
                    Tarea tarea = optionalTarea.get();
                    String estadoAnterior = tarea.getEstado();

                    // Cambiar el estado
                    tarea.setEstado(nuevoEstado);

                    // Guardar cambios
                    tareaRepository.save(tarea);

                    System.out.println("✔ Estado cambiado - Tarea ID: " + id + 
                                     " | De: " + estadoAnterior + " → " + nuevoEstado);

                    response.sendRedirect(request.getContextPath() + 
                        "/organizar?mensaje=Estado actualizado a: " + nuevoEstado);
                } else {
                    response.sendRedirect(request.getContextPath() + 
                        "/organizar?error=No se encontró la tarea");
                }

            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/organizar?error=ID inválido");
            }

        } catch (Exception e) {
            System.err.println("✘ Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/organizar?error=Error al cambiar el estado");
        }
    }

    /**
     * Valida que el estado sea uno de los permitidos
     */
    private boolean esEstadoValido(String estado) {
        return estado.equals("Pendiente") || 
               estado.equals("En Progreso") || 
               estado.equals("Completada");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir a organizar si se intenta acceder por GET
        response.sendRedirect(request.getContextPath() + "/organizar");
    }
}
