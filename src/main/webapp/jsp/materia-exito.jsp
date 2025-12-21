<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Operacion Exitosa - Gestor de Tareas</title>
    <%@ include file="includes/styles.jsp" %>
</head>
<body>
<div class="container">
    <header>
        <h1>Operacion Exitosa</h1>
    </header>

    <main>
        <div class="success-container">
            <div class="success-icon">OK</div>

            <h2>${not empty mensaje ? mensaje : 'Operación completada exitosamente'}</h2>

            <c:if test="${not empty materia}">
                <div class="details-card">
                    <h3>Detalles de la Materia</h3>
                    <div class="detail-row">
                        <span class="label">ID:</span>
                        <span class="value">${materia.id}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Nombre:</span>
                        <span class="value">${materia.nombre}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Descripción:</span>
                        <span class="value">${materia.descripcion}</span>
                    </div>
                </div>
            </c:if>

            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/listarMateria" class="btn btn-primary">
                    Volver al Inicio
                </a>

                <c:if test="${not empty materia}">
                    <a href="${pageContext.request.contextPath}/materias" class="btn btn-secondary">
                        Crear Otra Materia
                    </a>
                </c:if>
            </div>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>
