<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Gestor de Tareas</title>
    <%@ include file="includes/styles.jsp" %>
    <style>
        .error-container {
            text-align: center;
            padding: 50px 20px;
        }
        .error-container h1 {
            color: #c0392b;
            font-size: 48px;
        }
        .error-container p {
            font-size: 18px;
            color: #555;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="error-container">
        <h1>Error</h1>
        <p>Ha ocurrido un error en la aplicacion.</p>
        <p>Error: <strong><%= exception != null ? exception.getMessage() : "Pagina no encontrada" %></strong></p>
        <p><a href="<%= request.getContextPath() %>/home" class="btn btn-primary">Volver al inicio</a></p>
    </div>
</div>
</body>
</html>
