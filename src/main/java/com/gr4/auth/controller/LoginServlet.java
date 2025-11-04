package com.gr4.auth.controller;

import com.gr4.auth.model.Usuario;
import com.gr4.auth.service.AuthService;
import com.gr4.auth.config.ServiceLocator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet responsable únicamente del manejo de autenticación de usuarios.
 * Cumple con SRP - Solo se encarga de procesar login y logout.
 * Cumple con OCP - Extensible para diferentes tipos de autenticación.
 * Cumple con DIP - Usa ServiceLocator para obtener dependencias.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {
    
    private AuthService authService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Usar ServiceLocator para obtener la instancia única de AuthService
        this.authService = ServiceLocator.getInstance().getAuthService();
        System.out.println("✓ LoginServlet inicializado con AuthService compartido");
    }

    /**
     * Maneja peticiones GET - Muestra formulario de login o procesa logout
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = getAction(request);
        
        switch (action) {
            case "logout":
                procesarLogout(request, response);
                break;
            case "login":
            default:
                mostrarFormularioLogin(request, response);
                break;
        }
    }

    /**
     * Maneja peticiones POST - Procesa autenticación
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        procesarAutenticacion(request, response);
    }

    /**
     * Procesa la autenticación del usuario
     * Responsabilidad única: validar credenciales y crear sesión
     */
    private void procesarAutenticacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // PASO 1: Obtener credenciales del formulario
            String correo = request.getParameter("correo");
            String contraseña = request.getParameter("password");
            
            System.out.println("🔐 LoginServlet: Datos recibidos");
            System.out.println("🔐 LoginServlet: Correo = " + correo);
            System.out.println("🔐 LoginServlet: Contraseña recibida = " + (contraseña != null ? "SÍ" : "NO"));
            System.out.println("🔐 Intento de login para: " + correo);
            
            // PASO 2: Validar datos básicos
            if (correo == null || contraseña == null || 
                correo.trim().isEmpty() || contraseña.trim().isEmpty()) {
                
                manejarErrorLogin(request, response, "Por favor complete todos los campos");
                return;
            }
            
            // PASO 3: Autenticar usando el servicio
            Usuario usuarioAutenticado = authService.autenticar(correo.trim(), contraseña);
            
            if (usuarioAutenticado == null) {
                System.out.println("✗ Login fallido para: " + correo);
                manejarErrorLogin(request, response, "Credenciales incorrectas");
                return;
            }
            
            // PASO 4: Crear sesión exitosa
            crearSesionUsuario(request, usuarioAutenticado);
            
            System.out.println("✓ Login exitoso para: " + correo);
            
            // PASO 5: Redirigir a la aplicación principal
            response.sendRedirect(request.getContextPath() + "/jsp/materia-lista.jsp");
            
        } catch (Exception e) {
            System.err.println("✗ Error durante autenticación: " + e.getMessage());
            e.printStackTrace();
            manejarErrorLogin(request, response, "Error interno del servidor");
        }
    }

    /**
     * Crea la sesión del usuario autenticado
     * Responsabilidad única: gestión de sesión
     */
    private void crearSesionUsuario(HttpServletRequest request, Usuario usuario) {
        HttpSession session = request.getSession(true);
        
        // Datos esenciales en sesión
        session.setAttribute("usuario", usuario);
        session.setAttribute("usuarioNombre", usuario.getNombre());
        session.setAttribute("usuarioCorreo", usuario.getCorreo());
        session.setAttribute("autenticado", true);
        
        // Configurar tiempo de sesión (30 minutos)
        session.setMaxInactiveInterval(30 * 60);
        
        System.out.println("✓ Sesión creada para usuario: " + usuario.getNombre());
    }

    /**
     * Procesa el cierre de sesión
     * Responsabilidad única: limpiar sesión
     */
    private void procesarLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String usuarioNombre = (String) session.getAttribute("usuarioNombre");
            System.out.println("🚪 Cerrando sesión para: " + usuarioNombre);
            
            // Invalidar sesión
            session.invalidate();
        }
        
        // Redirigir a página de login con mensaje
        request.setAttribute("mensaje", "Sesión cerrada exitosamente");
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    /**
     * Muestra el formulario de login
     */
    private void mostrarFormularioLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Si ya está autenticado, redirigir al dashboard
        HttpSession session = request.getSession(false);
        if (session != null && Boolean.TRUE.equals(session.getAttribute("autenticado"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    /**
     * Maneja errores de login de forma consistente
     */
    private void manejarErrorLogin(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    /**
     * Extrae la acción de la URL
     */
    private String getAction(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path.endsWith("/logout")) {
            return "logout";
        }
        return "login";
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✓ LoginServlet destruido");
    }
}