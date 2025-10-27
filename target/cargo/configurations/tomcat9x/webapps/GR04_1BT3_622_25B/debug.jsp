<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gr4.auth.config.ServiceLocator" %>
<%@ page import="com.gr4.auth.repository.UsuarioRepository" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🔧 Debug - Estado del Sistema</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
        }
        .debug-container {
            background: white;
            border-radius: 15px;
            padding: 30px;
            max-width: 800px;
            margin: 0 auto;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        .debug-header {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .debug-section {
            background: #f8f9fa;
            border-left: 4px solid #007bff;
            padding: 15px;
            margin: 15px 0;
            border-radius: 5px;
        }
        .debug-title {
            font-weight: bold;
            color: #007bff;
            margin-bottom: 10px;
        }
        .debug-info {
            font-family: 'Courier New', monospace;
            background: #e9ecef;
            padding: 10px;
            border-radius: 5px;
            margin: 5px 0;
        }
        .btn-debug {
            background: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-danger {
            background: #dc3545;
        }
        .btn-primary {
            background: #007bff;
        }
    </style>
</head>
<body>
    <div class="debug-container">
        <div class="debug-header">
            <h1>🔧 Panel de Debug del Sistema</h1>
            <p>Estado actual de usuarios y servicios</p>
        </div>

        <%
            try {
                // Obtener ServiceLocator
                ServiceLocator serviceLocator = ServiceLocator.getInstance();
                UsuarioRepository repository = serviceLocator.getUsuarioRepository();
                
                // Obtener información del sistema
                int totalUsuarios = repository.obtenerTotalUsuarios();
        %>

        <div class="debug-section">
            <div class="debug-title">📊 Estado del Repositorio</div>
            <div class="debug-info">
                Total de usuarios registrados: <strong><%= totalUsuarios %></strong>
            </div>
            <div class="debug-info">
                ServiceLocator status: <strong>✓ Activo</strong>
            </div>
            <div class="debug-info">
                Tiempo actual: <strong><%= new java.util.Date() %></strong>
            </div>
        </div>

        <div class="debug-section">
            <div class="debug-title">🔍 Acciones de Debug</div>
            <a href="<%= request.getContextPath() %>/login" class="btn-debug btn-primary">
                🔐 Ir a Login
            </a>
            <a href="<%= request.getContextPath() %>/register" class="btn-debug">
                📝 Ir a Registro
            </a>
            
            <form method="post" style="display: inline;">
                <input type="hidden" name="action" value="limpiar">
                <button type="submit" class="btn-debug btn-danger" 
                        onclick="return confirm('¿Estás seguro de limpiar todos los usuarios?')">
                    🧹 Limpiar Usuarios
                </button>
            </form>
        </div>

        <%
                // Procesar acciones POST
                if ("POST".equals(request.getMethod())) {
                    String action = request.getParameter("action");
                    if ("limpiar".equals(action)) {
                        serviceLocator.limpiarDatos();
                        out.println("<div class=\"debug-section\">");
                        out.println("<div class=\"debug-title\">✅ Acción Completada</div>");
                        out.println("<div class=\"debug-info\">Todos los usuarios han sido eliminados del sistema.</div>");
                        out.println("</div>");
                    }
                }
            } catch (Exception e) {
                out.println("<div class=\"debug-section\">");
                out.println("<div class=\"debug-title\">❌ Error del Sistema</div>");
                out.println("<div class=\"debug-info\">Error: " + e.getMessage() + "</div>");
                out.println("</div>");
                e.printStackTrace();
            }
        %>

        <div class="debug-section">
            <div class="debug-title">📋 Instrucciones de Prueba</div>
            <ol>
                <li>Usa el botón "Ir a Registro" para crear un nuevo usuario</li>
                <li>Después del registro, serás redirigido al login automáticamente</li>
                <li>Usa las credenciales que acabas de crear para iniciar sesión</li>
                <li>Si hay problemas, revisa la consola del servidor</li>
                <li>Usa "Limpiar Usuarios" para reset el sistema si es necesario</li>
            </ol>
        </div>
    </div>
</body>
</html>