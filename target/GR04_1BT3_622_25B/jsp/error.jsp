<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Error - Gestor de Tareas Universitarias</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f8f8f8;
      text-align: center;
      padding-top: 50px;
    }
    h1 {
      color: #c0392b;
      font-size: 48px;
    }
    p {
      font-size: 18px;
      color: #555;
    }
    a {
      text-decoration: none;
      color: #2980b9;
    }
    a:hover {
      text-decoration: underline;
    }
    .container {
      display: inline-block;
      background-color: #fff;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
  </style>
</head>
<body>
<div class="container">
  <h1>¡Ups!</h1>
  <p>Ha ocurrido un error en la aplicación.</p>
  <p>Error: <strong><%= exception != null ? exception.getMessage() : "Página no encontrada" %></strong></p>
  <p><a href="<%= request.getContextPath() %>/">Volver al inicio</a></p>
</div>
</body>
</html>
