package com.gr4.auth.controller;

import com.gr4.auth.model.Usuario;
import com.gr4.auth.service.AuthService;
import com.gr4.auth.config.ServiceLocator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet responsable únicamente del registro de usuarios.
 * Cumple con SRP - Solo se encarga de procesar registros.
 * Cumple con DIP - Usa ServiceLocator para obtener dependencias.
 * Usa AuthService para respetar la separación de responsabilidades.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register", "/RegisterServlet", "/registro"})
public class RegisterServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Usar ServiceLocator para obtener la instancia única de AuthService
        this.authService = ServiceLocator.getInstance().getAuthService();
        System.out.println("✓ RegisterServlet inicializado con AuthService compartido");
    }

    /**
     * Maneja peticiones GET - Muestra formulario de registro
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
    }

    /**
     * Maneja peticiones POST - Procesa el registro de usuario
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String contraseña = request.getParameter("password");

            System.out.println("📝 RegisterServlet: Datos recibidos");
            System.out.println("📝 RegisterServlet: Nombre = " + nombre);
            System.out.println("📝 RegisterServlet: Correo = " + correo);
            System.out.println("📝 RegisterServlet: Contraseña recibida = " + (contraseña != null ? "SÍ" : "NO"));
            System.out.println("📝 Intento de registro para: " + correo);

            // PASO 2: Validación básica de campos
            if (nombre == null || correo == null || contraseña == null ||
                nombre.trim().isEmpty() || correo.trim().isEmpty() || contraseña.trim().isEmpty()) {
                
                manejarErrorRegistro(request, response, "Todos los campos son obligatorios");
                return;
            }

            // PASO 3: Verificar si el correo ya está registrado
            if (authService.correoEstaRegistrado(correo.trim())) {
                manejarErrorRegistro(request, response, "El correo electrónico ya está registrado");
                return;
            }

            // PASO 4: Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario(nombre.trim(), correo.trim(), contraseña);

            // PASO 5: Registrar usuario usando el servicio
            boolean registroExitoso = authService.registrarUsuario(nuevoUsuario);

            if (!registroExitoso) {
                System.err.println("✗ Falló el registro para: " + correo);
                manejarErrorRegistro(request, response, "Error en los datos ingresados. Verifique la información.");
                return;
            }

            System.out.println("✓ Usuario registrado exitosamente: " + correo);
            System.out.println("✓ Usuario guardado en repositorio, redirigiendo a login...");

            // PASO 6: Redirigir a login con mensaje de éxito usando redirect
            String redirectUrl = request.getContextPath() + "/jsp/login.jsp?mensaje=" + 
                java.net.URLEncoder.encode("¡Registro exitoso! Ahora puedes iniciar sesión.", "UTF-8");
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            System.err.println("✗ Error durante registro: " + e.getMessage());
            e.printStackTrace();
            manejarErrorRegistro(request, response, "Error interno del servidor");
        }
    }

    /**
     * Maneja errores de registro de forma consistente
     */
    private void manejarErrorRegistro(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ RegisterServlet destruido");
    }
}
