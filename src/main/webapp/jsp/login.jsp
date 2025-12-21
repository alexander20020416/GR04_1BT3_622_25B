<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesion - Gestor de Tareas</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
        }
        .register-container {
            background: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        .register-container h2 {
            text-align: center;
            color: #333;
            margin-bottom: 10px;
            font-size: 24px;
        }
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 25px;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 6px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-group input:focus {
            outline: none;
            border-color: #007bff;
        }
        .btn-primary {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .links-section {
            text-align: center;
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        .links-section p {
            color: #666;
            margin-bottom: 10px;
            font-size: 14px;
        }
        .register-link {
            display: inline-block;
            background-color: #28a745;
            color: #fff;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
        }
        .register-link:hover {
            background-color: #218838;
        }
        .message {
            margin-top: 15px;
            padding: 12px;
            border-radius: 4px;
            text-align: center;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Iniciar Sesion</h2>
    <p class="subtitle">Accede a tu cuenta del Gestor de Tareas</p>
    
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="correo">Correo Electronico</label>
            <input type="email" 
                   id="correo" 
                   name="correo" 
                   placeholder="ejemplo@correo.com" 
                   value="${param.correo}"
                   required>
        </div>
        
        <div class="form-group">
            <label for="password">Contrasena</label>
            <input type="password" 
                   id="password" 
                   name="password" 
                   placeholder="********" 
                   required>
        </div>
        
        <button type="submit" class="btn-primary">Iniciar Sesion</button>
    </form>
    
    <div class="links-section">
        <p>No tienes cuenta?</p>
        <a href="${pageContext.request.contextPath}/jsp/registro.jsp" class="register-link">
            Crear cuenta nueva
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="message error">
            <span>${error}</span>
        </div>
    </c:if>
    
    <c:if test="${not empty mensaje}">
        <div class="message success">
            <span>${mensaje}</span>
        </div>
    </c:if>
    
    <c:if test="${not empty param.mensaje}">
        <div class="message success">
            <span>${param.mensaje}</span>
        </div>
    </c:if>
</div>
</body>
</html>