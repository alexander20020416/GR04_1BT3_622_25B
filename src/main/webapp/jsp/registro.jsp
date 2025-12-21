<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Gestor de Tareas</title>
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
            margin-bottom: 25px;
            font-size: 24px;
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
        .btn-submit {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .btn-submit:hover {
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
        .login-link {
            display: inline-block;
            background-color: #28a745;
            color: #fff;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
        }
        .login-link:hover {
            background-color: #218838;
        }
        .message {
            margin-bottom: 20px;
            padding: 12px;
            border-radius: 4px;
            text-align: center;
            font-size: 14px;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .hint {
            font-size: 12px;
            color: #666;
            margin-top: 4px;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Crear Cuenta</h2>
    
    <c:if test="${not empty error}">
        <div class="message error">
            ${error}
        </div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
        <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" placeholder="Ingresa tu nombre" required>
            <div class="hint">Minimo 3 caracteres</div>
        </div>
        <div class="form-group">
            <label for="correo">Correo</label>
            <input type="email" id="correo" name="correo" placeholder="ejemplo@correo.com" required>
        </div>
        <div class="form-group">
            <label for="password">Contrasena</label>
            <input type="password" id="password" name="password" placeholder="********" required>
            <div class="hint">Minimo 8 caracteres</div>
        </div>
        <button type="submit" class="btn-submit">Registrarse</button>
    </form>

    <div class="links-section">
        <p>Tienes Cuenta?</p>
        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="login-link">
            Iniciar Sesion
        </a>
    </div>

</div>
</body>
</html>

