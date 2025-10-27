<%@ page import="com.gr4.auth.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

<div class="container">
    <h2>Login</h2>

    <%
        String mensaje = request.getParameter("mensaje");
        if(mensaje != null){
    %>
    <p class="mensaje"><%= mensaje %></p>
    <%
        }
    %>

    <form method="post" action="login.jsp">
        Correo: <input type="email" name="correo" required><br>
        Contraseña: <input type="password" name="contraseña" required><br>
        <input type="submit" value="Ingresar">
    </form>

    <p>¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></p>

    <%
        // Lógica de login
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contraseña");

        if(correo != null && contrasena != null){
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if(usuario != null && usuario.autenticar(correo, contrasena)){
    %>
    <p class="mensaje exito">Bienvenido, <%= usuario.getNombre() %>!</p>
    <p><a href="logout.jsp">Cerrar sesión</a></p>
    <%
    } else {
    %>
    <p class="mensaje">Correo o contraseña incorrectos</p>
    <%
            }
        }
    %>

</div>
</body>
</html>
