<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Actividades - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>📋 Listado de Actividades</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/" class="btn-link">← Volver al inicio</a>
            <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">➕ Nueva Actividad</a>
        </nav>
    </header>

    <main>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${error}
            </div>
        </c:if>

        <c:if test="${not empty actividades}">
            <div class="activities-list">
                <c:forEach var="actividad" items="${actividades}">
                    <div class="activity-card">
                        <div class="activity-header">
                            <h3>${actividad.titulo}</h3>
                            <span class="badge badge-${actividad.prioridad == 'Alta' ? 'danger' : actividad.prioridad == 'Media' ? 'warning' : 'info'}">
                                ${actividad.prioridad}
                            </span>
                        </div>
                        
                        <div class="activity-body">
                            <p>${actividad.descripcion}</p>
                            <div class="activity-meta">
                                <span>📅 Entrega: ${actividad.fechaEntrega}</span>
                                <span>📊 Estado: ${actividad.estado}</span>
                            </div>
                        </div>

                        <div class="activity-actions">
                            <a href="${pageContext.request.contextPath}/agregar-tarea?actividadId=${actividad.id}" 
                               class="btn btn-sm btn-success">
                                ➕ Agregar Tarea
                            </a>
                            <a href="${pageContext.request.contextPath}/ver-tareas?actividadId=${actividad.id}" 
                               class="btn btn-sm btn-info">
                                👁️ Ver Tareas
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty actividades}">
            <div class="empty-state">
                <h3>📭 No hay actividades registradas</h3>
                <p>Comienza creando tu primera actividad académica</p>
                <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">
                    ➕ Crear Primera Actividad
                </a>
            </div>
        </c:if>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>
