package com.gr4.auth.controller;

import com.gr4.auth.config.ServiceLocator;
import com.gr4.auth.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet para debugging y verificación del estado del sistema.
 * Cumple con SRP - Solo se encarga de mostrar información de debug.
 */
@WebServlet(name = "DebugServlet", urlPatterns = {"/debug", "/debug-usuarios"})
public class DebugServlet extends HttpServlet {
    
    private AuthService authService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.authService = ServiceLocator.getInstance().getAuthService();
        System.out.println("✓ DebugServlet inicializado");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            int totalUsuarios = authService.obtenerTotalUsuarios();
            
            // Respuesta JSON
            out.println("{");
            out.println("  \"status\": \"success\",");
            out.println("  \"timestamp\": \"" + new java.util.Date() + "\",");
            out.println("  \"totalUsuarios\": " + totalUsuarios + ",");
            out.println("  \"serviceLocator\": \"activo\",");
            out.println("  \"authService\": \"funcionando\"");
            out.println("}");
            
            System.out.println("🔍 DebugServlet: Información solicitada - Total usuarios: " + totalUsuarios);
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{");
            out.println("  \"status\": \"error\",");
            out.println("  \"message\": \"" + e.getMessage() + "\"");
            out.println("}");
            
            System.err.println("❌ DebugServlet: Error al obtener información: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("limpiar".equals(action)) {
            try {
                authService.limpiarUsuarios();
                response.sendRedirect(request.getContextPath() + "/debug.jsp?message=Usuarios+limpiados+exitosamente");
                System.out.println("🧹 DebugServlet: Usuarios limpiados por solicitud");
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + "/debug.jsp?error=Error+al+limpiar+usuarios");
                System.err.println("❌ DebugServlet: Error al limpiar usuarios: " + e.getMessage());
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/debug.jsp");
        }
    }
}