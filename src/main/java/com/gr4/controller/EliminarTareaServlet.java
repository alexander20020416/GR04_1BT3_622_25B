package com.gr4.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Controlador para Eliminar Tareas
 * Implementa operación DELETE del CRUD
 * Cumple con SRP - Solo se encarga de eliminar tareas
 */
@WebServlet(name = "EliminarTareaServlet", urlPatterns = {"/eliminar-tarea"})
public class EliminarTareaServlet extends BaseServlet {

    /**
     * Maneja peticiones POST - Elimina una tarea
     * Se usa POST en lugar de GET por seguridad (operación destructiva)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Obtener el ID de la tarea a eliminar
            String idStr = request.getParameter("id");
            
            if (idStr == null || idStr.trim().isEmpty()) {
                System.err.println("✗ Error: ID de tarea no proporcionado");
                response.sendRedirect(request.getContextPath() + "/consultar?error=id-missing");
                return;
            }

            Long id = Long.parseLong(idStr);

            System.out.println("🗑️ Intentando eliminar tarea con ID: " + id);

            // PASO 2: Eliminar la tarea del repositorio
            boolean eliminada = tareaRepository.deleteById(id);

            if (eliminada) {
                System.out.println("✓ Tarea eliminada exitosamente con ID: " + id);
                
                // PASO 3: Redirigir con mensaje de éxito
                response.sendRedirect(request.getContextPath() + "/consultar?mensaje=Tarea eliminada exitosamente");
            } else {
                System.err.println("✗ No se pudo eliminar la tarea con ID: " + id);
                
                // PASO 4: Redirigir con mensaje de error
                response.sendRedirect(request.getContextPath() + "/consultar?error=No se encontró la tarea");
            }

        } catch (NumberFormatException e) {
            System.err.println("✗ Error: ID inválido - " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/consultar?error=ID inválido");
        } catch (Exception e) {
            System.err.println("✗ Error al eliminar tarea: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/consultar?error=Error al eliminar la tarea");
        }
    }

    /**
     * Maneja peticiones GET - Redirige a POST por seguridad
     * Las operaciones DELETE no deberían hacerse por GET
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.err.println("⚠️ Intento de eliminar tarea por GET - Redirigiendo");
        response.sendRedirect(request.getContextPath() + "/consultar");
    }
}
