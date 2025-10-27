<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Organizar Tareas - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>📊 Organizar Tareas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/" class="btn-link">← Volver al inicio</a>
        </nav>
    </header>

    <main>
        <div class="controls-section">
            <h2>Seleccione el Criterio de Ordenamiento</h2>
            <p class="subtitle">Patrón Strategy aplicado para diferentes estrategias de ordenamiento</p>

            <div class="strategy-buttons">
                <a href="${pageContext.request.contextPath}/organizar?orden=prioridad"
                   class="btn ${criterioActual == 'prioridad' || empty criterioActual ? 'btn-active' : 'btn-outline'}">
                    🎯 Por Prioridad
                </a>
                <a href="${pageContext.request.contextPath}/organizar?orden=fecha"
                   class="btn ${criterioActual == 'fecha' ? 'btn-active' : 'btn-outline'}">
                    📅 Por Fecha
                </a>
                <a href="${pageContext.request.contextPath}/organizar?orden=titulo"
                   class="btn ${criterioActual == 'titulo' ? 'btn-active' : 'btn-outline'}">
                    🔤 Alfabético
                </a>
            </div>

            <c:if test="${not empty nombreEstrategia}">
                <div class="alert alert-info">
                    <strong>Estrategia actual:</strong> ${nombreEstrategia}
                </div>
            </c:if>
        </div>

        <!-- Mostrar errores si existen -->
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Lista de tareas -->
        <div class="tareas-section">
            <h2>Listado de Tareas Ordenadas</h2>

            <c:choose>
                <c:when test="${empty tareas}">
                    <div class="empty-state">
                        <p>📭 No hay tareas registradas en el sistema.</p>
                        <p>Para crear tareas, debe <a href="${pageContext.request.contextPath}/planificar">planificar una tarea</a>.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tareas-count">
                        Total de tareas: <strong>${tareas.size()}</strong>
                    </div>

                    <div class="tareas-grid">
                        <c:forEach var="tarea" items="${tareas}">
                            <div class="tarea-card">
                                <div class="tarea-header">
                                    <h3>${tarea.titulo}</h3>
                                    <span class="badge badge-${tarea.prioridad.toLowerCase()}">${tarea.prioridad}</span>
                                </div>

                                <div class="tarea-body">
                                    <p class="tarea-descripcion">${tarea.descripcion}</p>

                                    <div class="tarea-meta">
                                        <div class="meta-item">
                                            <span class="label">Estado:</span>
                                            <span class="badge badge-estado-${tarea.estado.toLowerCase()}">${tarea.estado}</span>
                                        </div>

                                        <c:if test="${not empty tarea.fechaVencimiento}">
                                            <div class="meta-item">
                                                <span class="label">Vencimiento:</span>
                                                <span class="value">${tarea.fechaVencimiento}</span>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información del Sistema</h3>
            <p>Esta vista implementa el caso de uso <strong>"Organizar Tareas"</strong> del Incremento 1.</p>
            <ul>
                <li><strong>Patrón Strategy:</strong> Permite cambiar el criterio de ordenamiento dinámicamente</li>
                <li><strong>Prioridad:</strong> Ordena de mayor a menor (Alta → Media → Baja)</li>
                <li><strong>Fecha:</strong> Ordena por proximidad de vencimiento</li>
                <li><strong>Alfabético:</strong> Ordena por título de A a Z</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>