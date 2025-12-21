<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestionar Proyecto - Gestor de Tareas</title>
    <%@ include file="includes/styles.jsp" %>
</head>
<body>
<div class="container">
    <h1>Gestionar Proyecto</h1>
    <form action="${pageContext.request.contextPath}/gestionarProyecto" method="post" class="form-container">
        <div class="form-group">
            <label for="idProyecto">ID Proyecto:</label>
            <input type="text" id="idProyecto" name="idProyecto" class="form-control">
        </div>
        <div class="form-group">
            <label for="tituloTarea">Titulo Tarea:</label>
            <input type="text" id="tituloTarea" name="tituloTarea" class="form-control">
        </div>
        <div class="form-group">
            <label for="descripcionTarea">Descripcion Tarea:</label>
            <input type="text" id="descripcionTarea" name="descripcionTarea" class="form-control">
        </div>
        <div class="form-group">
            <label for="fechaVencimiento">Fecha de Vencimiento:</label>
            <input type="date" id="fechaVencimiento" name="fechaVencimiento" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">Enviar</button>
    </form>
</div>
</body>
</html>
