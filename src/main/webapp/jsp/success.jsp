<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Operación Exitosa - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>✓ Operación Exitosa</h1>
    </header>

    <main>
        <div class="success-container">
            <div class="success-icon">✓</div>

            <h2>${not empty mensaje ? mensaje : 'Operación completada exitosamente'}</h2>

            <c:if test="${not empty tarea}">
                <div class="details-card">
                    <h3>Detalles de la Tarea</h3>
                    <div class="detail-row">
                        <span class="label">ID:</span>
                        <span class="value">${tarea.id}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Título:</span>
                        <span class="value">${tarea.titulo}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Descripción:</span>
                        <span class="value">${tarea.descripcion}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Fecha Límite:</span>
                        <span class="value">${tarea.fechaVencimiento}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Prioridad:</span>
                        <span class="badge badge-${tarea.prioridad.toLowerCase()}">${tarea.prioridad}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Estado:</span>
                        <span class="badge badge-estado-${tarea.estado.toLowerCase()}">${tarea.estado}</span>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty alerta}">
                <div class="details-card">
                    <h3>Detalles de la Alerta</h3>
                    <div class="detail-row">
                        <span class="label">ID:</span>
                        <span class="value">${alerta.id}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Mensaje:</span>
                        <span class="value">${alerta.mensaje}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Fecha de Alerta:</span>
                        <span class="value">${alerta.fechaAlerta}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Tipo:</span>
                        <span class="badge badge-${alerta.tipo.toLowerCase()}">${alerta.tipo}</span>
                    </div>
                    <div class="detail-row">
                        <span class="label">Estado:</span>
                        <span class="badge ${alerta.activa ? 'badge-success' : 'badge-secondary'}">
                                ${alerta.activa ? 'Activa' : 'Inactiva'}
                        </span>
                    </div>

                    <c:if test="${not empty alerta.tarea}">
                        <div class="detail-row">
                            <span class="label">Tarea Asociada:</span>
                            <span class="value">${alerta.tarea.titulo}</span>
                        </div>
                    </c:if>
                </div>
            </c:if>

            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    🏠 Volver al Inicio
                </a>

                <c:if test="${not empty tarea}">
                    <a href="${pageContext.request.contextPath}/planificar" class="btn btn-secondary">
                        ➕ Planificar Otra Tarea
                    </a>
                    <a href="${pageContext.request.contextPath}/organizar" class="btn btn-secondary">
                        📊 Organizar Tareas
                    </a>
                    <a href="${pageContext.request.contextPath}/consultar" class="btn btn-secondary">
                        🔍 Consultar Tareas
                    </a>
                </c:if>

                <c:if test="${not empty tarea}">
                    <a href="${pageContext.request.contextPath}/planificar" class="btn btn-secondary">
                        ➕ Planificar Otra Tarea
                    </a>
                    <a href="${pageContext.request.contextPath}/organizar" class="btn btn-secondary">
                        📊 Ver Todas las Tareas
                    </a>
                </c:if>

                <c:if test="${not empty alerta}">
                    <a href="${pageContext.request.contextPath}/alertas" class="btn btn-secondary">
                        📋 Ver Todas las Alertas
                    </a>
                    <a href="${pageContext.request.contextPath}/alertas?action=crear" class="btn btn-secondary">
                        ➕ Crear Otra Alerta
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