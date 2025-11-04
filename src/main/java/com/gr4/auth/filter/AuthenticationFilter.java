package com.gr4.auth.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filtro de autenticación que protege las páginas que requieren login.
 * Cumple con SRP - Solo se encarga de verificar autenticación.
 * Implementa el patrón Chain of Responsibility.
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    // URLs que NO requieren autenticación
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/",
        "/home",
        "/menu",
        "/index.jsp",
        "/login",
        "/register", 
        "/RegisterServlet",
        "/registro",
        "/jsp/login.jsp",
        "/jsp/registro.jsp",
        "/css/",
        "/js/",
        "/images/"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("✓ AuthenticationFilter inicializado");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getServletPath();
        String contextPath = httpRequest.getContextPath();

        System.out.println("🔍 Filtro Auth - Verificando: " + requestPath);

        // Verificar si es una ruta pública
        if (isPublicPath(requestPath)) {
            System.out.println("✓ Ruta pública permitida: " + requestPath);
            chain.doFilter(request, response);
            return;
        }

        // Verificar autenticación para rutas protegidas
        HttpSession session = httpRequest.getSession(false);
        boolean isAuthenticated = session != null && 
                                Boolean.TRUE.equals(session.getAttribute("autenticado"));

        if (isAuthenticated) {
            System.out.println("✓ Usuario autenticado accediendo a: " + requestPath);
            chain.doFilter(request, response);
        } else {
            System.out.println("❌ Acceso denegado a: " + requestPath + " - Redirigiendo a login");
            
            // Guardar la URL destino para redirigir después del login
            String targetUrl = requestPath;
            if (httpRequest.getQueryString() != null) {
                targetUrl += "?" + httpRequest.getQueryString();
            }
            httpRequest.getSession(true).setAttribute("targetUrl", targetUrl);
            
            // Redirigir a login
            httpResponse.sendRedirect(contextPath + "/jsp/login.jsp");
        }
    }

    /**
     * Verifica si la ruta es pública (no requiere autenticación)
     */
    private boolean isPublicPath(String path) {
        if (path == null) return false;
        
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> 
            path.equals(publicPath) || path.startsWith(publicPath)
        );
    }

    @Override
    public void destroy() {
        System.out.println("✓ AuthenticationFilter destruido");
    }
}