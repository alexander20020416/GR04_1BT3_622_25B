package com.gr4.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet que maneja la página principal/inicio de la aplicación.
 * Redirige al menú principal si el usuario está autenticado,
 * o al login si no lo está.
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home", "/menu"})
public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("✓ HomeServlet inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        boolean isAuthenticated = session != null && 
                                Boolean.TRUE.equals(session.getAttribute("autenticado"));

        if (isAuthenticated) {
            // Usuario autenticado - Mostrar menú principal
            System.out.println("✓ Usuario autenticado - Mostrando menú principal");
            request.getRequestDispatcher("/jsp/materia-lista.jsp").forward(request, response);
        } else {
            // Usuario no autenticado - Redirigir a login
            System.out.println("⚠️ Usuario no autenticado - Redirigiendo a login");
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ HomeServlet destruido");
    }
}
