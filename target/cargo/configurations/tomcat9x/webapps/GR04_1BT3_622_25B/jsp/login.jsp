<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registro.css">
</head>
<body>
<div class="register-container">
    <h2>🔐 Iniciar Sesión</h2>
    <p class="subtitle">Accede a tu cuenta del Gestor de Tareas</p>
    
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="correo">Correo Electrónico</label>
            <input type="email" 
                   id="correo" 
                   name="correo" 
                   placeholder="ejemplo@correo.com" 
                   value="${param.correo}"
                   required>
        </div>
        
        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" 
                   id="password" 
                   name="password" 
                   placeholder="********" 
                   required>
        </div>
        
        <button type="submit" class="btn-primary">Iniciar Sesión</button>
    </form>
    
    <div class="links-section">
        <p>¿No tienes cuenta?</p>
        <a href="${pageContext.request.contextPath}/jsp/registro.jsp" class="register-link">
            📝 Crear cuenta nueva
        </a>
    </div>

    <!-- Mensajes de estado -->
    <c:if test="${not empty error}">
        <div class="message error">
            <span>❌ ${error}</span>
        </div>
    </c:if>
    
    <c:if test="${not empty mensaje}">
        <div class="message success">
            <span>✅ ${mensaje}</span>
        </div>
    </c:if>
    
    <!-- Mensaje desde URL parameter -->
    <c:if test="${not empty param.mensaje}">
        <div class="message success">
            <span>✅ ${param.mensaje}</span>
        </div>
    </c:if>
</div>

<style>
    .subtitle {
        color: #666;
        text-align: center;
        margin-bottom: 2rem;
        font-size: 0.9rem;
    }
    
    .links-section {
        text-align: center;
        margin-top: 1.5rem;
        padding-top: 1rem;
        border-top: 1px solid #eee;
    }
    
    .links-section p {
        margin-bottom: 0.75rem;
        color: #666;
        font-size: 0.9rem;
    }
    
    .register-link {
        display: inline-block;
        background-color: #28a745;
        color: white !important;
        padding: 0.6rem 1.5rem;
        border-radius: 4px;
        text-decoration: none !important;
        font-weight: 500;
        font-size: 0.9rem;
        transition: background-color 0.2s;
        margin-top: 0.5rem;
    }
    
    .register-link:hover {
        background-color: #218838;
        text-decoration: none !important;
        color: white !important;
    }
    
    .message {
        margin-top: 1rem;
        padding: 0.75rem;
        border-radius: 4px;
        text-align: center;
        font-size: 0.9rem;
    }
    
    .message.error {
        background-color: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
    }
    
    .message.success {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
    
    .btn-primary {
        width: 100%;
        padding: 0.75rem;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 1rem;
        cursor: pointer;
        transition: background-color 0.2s;
    }
    
    .btn-primary:hover {
        background-color: #0056b3;
    }
</style>
</body>
</html>