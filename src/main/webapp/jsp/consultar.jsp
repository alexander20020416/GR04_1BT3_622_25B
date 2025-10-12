<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultar Tareas - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>🔍 Consultar Tareas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/" class="btn-link">← Volver al inicio</a>
        </nav>
    </header>

    <main>
        <div class="controls-section">
            <h2>Filtrar Tareas por Estado</h2>
            <p class="subtitle">Incremento 2 - Caso de Uso: Consultar Tareas</p>

            <div class="filter-buttons">
                <a href="${pageContext.request.contextPath}/consultar"
                   class="btn ${empty filtroActual || filtroActual == 'todos' ? 'btn-active' : 'btn-outline'}">
                    📋 Todas
                </a>
                <a href="${pageContext.request.contextPath}/consultar?filtro=Pendiente"
                   class="btn ${filtroActual == 'Pendiente' ? 'btn-active' : 'btn-outline'}">
                    ⏳ Pendientes
                </a>
                <a href="${pageContext.request.contextPath}/consultar?filtro=En Progreso"
                   class="btn ${filtroActual == 'En Progreso' ? 'btn-active' : 'btn-outline'}">
                    🔄 En Progreso
                </a>
                <a href="${pageContext.request.contextPath}/consultar?filtro=Completada"
                   class="btn ${filtroActual == 'Completada' ? 'btn-active' : 'btn-outline'}">
                    ✅ Completadas
                </a>
            </div>
        </div>

        <!-- Mostrar mensajes -->
        <c:if test="${not empty mensaje}">
            <div class="alert alert-warning">
                    ${mensaje}
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Validación de integridad (CP14) -->
        <c:if test="${not empty integridadValida && !integridadValida}">
            <div class="alert alert-error">
                <strong>⚠️ Advertencia:</strong> Se detectaron inconsistencias en la integridad de los datos.
            </div>
        </c:if>

        <!-- Lista de tareas -->
        <div class="tareas-section">
            <c:choose>
                <c:when test="${empty tareas}">
                    <div class="empty-state">
                        <p>📭 ${not empty mensaje ? mensaje : 'No hay tareas para mostrar.'}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tareas-stats">
                        <h2>Resultados de la Consulta</h2>
                        <p>Se encontraron <strong>${tareas.size()}</strong> tarea(s)</p>
                    </div>

                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Título</th>
                                <th>Descripción</th>
                                <th>Estado</th>
                                <th>Prioridad</th>
                                <th>Fecha Vencimiento</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="tarea" items="${tareas}">
                                <tr>
                                    <td>${tarea.id}</td>
                                    <td><strong>${tarea.titulo}</strong></td>
                                    <td class="descripcion-cell">${tarea.descripcion}</td>
                                    <td>
                                                <span class="badge badge-estado-${tarea.estado.toLowerCase().replace(' ', '-')}">
                                                        ${tarea.estado}
                                                </span>
                                    </td>
                                    <td>
                                                <span class="badge badge-${tarea.prioridad.toLowerCase()}">
                                                        ${tarea.prioridad}
                                                </span>
                                    </td>
                                    <td>
                                            ${not empty tarea.fechaVencimiento ? tarea.fechaVencimiento : 'N/A'}
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información del Sistema</h3>
            <p>Esta vista implementa el caso de uso <strong>"Consultar Tareas"</strong> del Incremento 2.</p>
            <ul>
                <li><strong>Filtrado:</strong> Permite consultar tareas por estado</li>
                <li><strong>Validación de integridad:</strong> Verifica que todas las tareas tengan datos completos (CP14)</li>
                <li><strong>Visualización completa:</strong> Muestra todos los campos de cada tarea</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>