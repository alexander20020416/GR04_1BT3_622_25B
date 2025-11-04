<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestionar Proyecto</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/proyectos.css">
</head>
<body>
<h1>Gestionar Proyecto</h1>
<form action="${pageContext.request.contextPath}/gestionarProyecto" method="post">
    <label for="idProyecto">ID Proyecto:</label>
    <input type="text" id="idProyecto" name="idProyecto"><br>
    <label for="tituloTarea">Título Tarea:</label>
    <input type="text" id="tituloTarea" name="tituloTarea"><br>
    <label for="descripcionTarea">Descripción Tarea:</label>
    <input type="text" id="descripcionTarea" name="descripcionTarea"><br>
    <label for="fechaVencimiento">Fecha de Vencimiento:</label>
    <input type="date" id="fechaVencimiento" name="fechaVencimiento"><br>
    <button type="submit">Enviar</button>
</form>
</body>
</html>
