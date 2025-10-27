<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gr4.auth.config.ServiceLocator" %>
<%@ page import="com.gr4.auth.service.AuthService" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🔧 Debug - Usuario Status</title>
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
            max-width: 900px;
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
        .success-section {
            border-left-color: #28a745;
            background: #d4edda;
        }
        .warning-section {
            border-left-color: #ffc107;
            background: #fff3cd;
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
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
            text-decoration: none;
            display: inline-block;
            font-weight: bold;
        }
        .btn-primary { background: #007bff; color: white; }
        .btn-success { background: #28a745; color: white; }
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; color: white; }
        
        .test-form {
            background: #e3f2fd;
            padding: 15px;
            border-radius: 8px;
            margin: 10px 0;
        }
        .form-row {
            display: flex;
            gap: 10px;
            margin: 5px 0;
            align-items: center;
        }
        .form-row input {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="debug-container">
        <div class="debug-header">
            <h1>🔧 Estado del Sistema de Usuarios</h1>
            <p>Diagnóstico completo del sistema de autenticación</p>
        </div>

        <%
            try {
                // Obtener ServiceLocator
                ServiceLocator serviceLocator = ServiceLocator.getInstance();
                AuthService authService = serviceLocator.getAuthService();
                
                // Obtener información del sistema
                int totalUsuarios = authService.obtenerTotalUsuarios();
        %>

        <div class="debug-section <%= totalUsuarios > 0 ? "success-section" : "warning-section" %>">
            <div class="debug-title">📊 Estado de la Base de Datos</div>
            <div class="debug-info">
                <strong>Total de usuarios registrados:</strong> <%= totalUsuarios %>
            </div>
            <div class="debug-info">
                <strong>Estado JPA/Hibernate:</strong> ✅ Funcionando
            </div>
            <div class="debug-info">
                <strong>Base de datos H2:</strong> ✅ Conectada en ./data/gestor_tareas
            </div>
            <div class="debug-info">
                <strong>ServiceLocator:</strong> ✅ Activo con SOLID
            </div>
            <div class="debug-info">
                <strong>Última verificación:</strong> <%= new java.util.Date() %>
            </div>
        </div>

        <div class="debug-section">
            <div class="debug-title">🔍 Verificar Usuario Específico</div>
            <p>Verifica si un correo específico está registrado en el sistema:</p>
            
            <div class="test-form">
                <form method="post" action="<%= request.getRequestURI() %>">
                    <div class="form-row">
                        <label>Correo:</label>
                        <input type="email" name="checkEmail" placeholder="usuario@ejemplo.com" required>
                        <button type="submit" name="action" value="verificar" class="btn btn-primary">
                            🔍 Verificar
                        </button>
                    </div>
                </form>
            </div>
            
            <%
                String action = request.getParameter("action");
                String checkEmail = request.getParameter("checkEmail");
                
                if ("verificar".equals(action) && checkEmail != null && !checkEmail.trim().isEmpty()) {
                    boolean existe = authService.correoEstaRegistrado(checkEmail.trim());
            %>
                <div class="debug-info">
                    <strong>Resultado para "<%= checkEmail %>":</strong> 
                    <%= existe ? "✅ EXISTE en la base de datos" : "❌ NO ENCONTRADO" %>
                </div>
            <%
                }
            %>
        </div>

        <div class="debug-section">
            <div class="debug-title">🚀 Acciones Disponibles</div>
            
            <a href="<%= request.getContextPath() %>/jsp/registro.jsp" class="btn btn-success">
                📝 Registrar Nuevo Usuario
            </a>
            
            <a href="<%= request.getContextPath() %>/jsp/login.jsp" class="btn btn-primary">
                🔐 Ir a Login
            </a>
            
            <a href="<%= request.getContextPath() %>/debug" class="btn btn-warning">
                📊 API Debug (JSON)
            </a>
            
            <% if (totalUsuarios > 0) { %>
            <form method="post" style="display: inline;">
                <button type="submit" name="action" value="limpiar" class="btn btn-danger" 
                        onclick="return confirm('¿Estás seguro de eliminar TODOS los usuarios?')">
                    🧹 Limpiar BD
                </button>
            </form>
            <% } %>
        </div>

        <div class="debug-section">
            <div class="debug-title">📋 Instrucciones de Prueba SOLID</div>
            <ol>
                <li><strong>Registro:</strong> Crear usuario con datos válidos</li>
                <li><strong>Verificación:</strong> Usar el formulario arriba para confirmar que existe</li>
                <li><strong>Login:</strong> Iniciar sesión con las mismas credenciales</li>
                <li><strong>Persistencia:</strong> Los datos se mantienen entre reinicios (H2 file mode)</li>
                <li><strong>Arquitectura:</strong> ServiceLocator + DIP + JPA respetando SOLID</li>
            </ol>
        </div>

        <%
                // Procesar acciones POST
                if ("POST".equals(request.getMethod()) && "limpiar".equals(action)) {
                    authService.limpiarUsuarios();
                    response.sendRedirect(request.getRequestURI() + "?mensaje=usuarios_limpiados");
                    return;
                }
                
                String mensaje = request.getParameter("mensaje");
                if ("usuarios_limpiados".equals(mensaje)) {
        %>
                    <div class="debug-section success-section">
                        <div class="debug-title">✅ Operación Completada</div>
                        <div class="debug-info">Todos los usuarios han sido eliminados del sistema.</div>
                    </div>
        <%
                }
            } catch (Exception e) {
        %>
                <div class="debug-section" style="border-left-color: #dc3545; background: #f8d7da;">
                    <div class="debug-title">❌ Error del Sistema</div>
                    <div class="debug-info">Error: <%= e.getMessage() %></div>
                </div>
        <%
                e.printStackTrace();
            }
        %>
    </div>
</body>
</html>