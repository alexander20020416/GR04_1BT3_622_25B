<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registro.css">
</head>
<body>
<div class="register-container">
    <h2>Crear Cuenta</h2>
    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
        <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" placeholder="Ingresa tu nombre" required>
        </div>
        <div class="form-group">
            <label for="correo">Correo</label>
            <input type="email" id="correo" name="correo" placeholder="ejemplo@correo.com" required>
        </div>
        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" placeholder="********" required>
        </div>
        <button type="submit" class="btn-submit">Registrarse</button>
    </form>

    <div class="links-section">
        <p>¿Tienes Cuenta?</p>
        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="login-link">
            📝 Iniciar Sesión
        </a>
    </div>

</div>
</body>
</html>

