<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Alertas - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>⏰ Mis Alertas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/" class="btn-link">← Volver al inicio</a>
            <a href="${pageContext.request.contextPath}/alertas?action=crear" class="btn btn-primary">
                ➕ Crear Nueva Alerta
            </a>
        </nav>
    </header>

    <main>
        <div class="list-container">
            <h2>📋 Alertas Configuradas</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <c:choose>
                <c:when test="${empty alertas}">
                    <div class="empty-state">
                        <div class="empty-icon">🔔</div>
                        <h3>No hay alertas configuradas</h3>
                        <p>Aún no has creado ninguna alerta. Comienza configurando tu primera alerta.</p>
                        <a href="${pageContext.request.contextPath}/alertas?action=crear" class="btn btn-primary">
                            ➕ Crear Mi Primera Alerta
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="stats-summary">
                        <div class="stat-card">
                            <div class="stat-number">${alertas.size()}</div>
                            <div class="stat-label">Total de Alertas</div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-number">
                                <c:set var="activas" value="0"/>
                                <c:forEach var="alerta" items="${alertas}">
                                    <c:if test="${alerta.activa}">
                                        <c:set var="activas" value="${activas + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${activas}
                            </div>
                            <div class="stat-label">Alertas Activas</div>
                        </div>
                    </div>

                    <div class="table-container">
                        <table class="data-table">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Mensaje</th>
                                <th>Fecha y Hora</th>
                                <th>Tipo</th>
                                <th>Estado</th>
                                <th>Tarea Asociada</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="alerta" items="${alertas}">
                                <tr class="${alerta.activa ? 'row-active' : 'row-inactive'}">
                                    <td><strong>#${alerta.id}</strong></td>
                                    <td class="text-column">
                                        <div class="text-preview">${alerta.mensaje}</div>
                                    </td>
                                    <td>
                                        <div class="date-display">
                                            📅 ${alerta.fechaAlerta}
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${alerta.tipo == 'Urgente'}">
                                                <span class="badge badge-urgente">🚨 ${alerta.tipo}</span>
                                            </c:when>
                                            <c:when test="${alerta.tipo == 'Informativa'}">
                                                <span class="badge badge-informativa">ℹ️ ${alerta.tipo}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-recordatorio">🔔 ${alerta.tipo}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${alerta.activa}">
                                                <span class="badge badge-success">✓ Activa</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-secondary">○ Inactiva</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${not empty alerta.tarea}">
                                            <div class="task-info">
                                                <strong>${alerta.tarea.titulo}</strong>
                                                <br>
                                                <small>Vence: ${alerta.tarea.fechaVencimiento}</small>
                                            </div>
                                        </c:if>
                                        <c:if test="${empty alerta.tarea}">
                                            <span class="text-muted">Sin tarea</span>
                                        </c:if>
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
            <h3>ℹ️ Acerca de las Alertas</h3>
            <ul>
                <li><strong>Patrón Observer:</strong> Cada alerta creada notifica automáticamente al sistema</li>
                <li><strong>Tipos de Alertas:</strong>
                    <ul>
                        <li>🚨 <strong>Urgente:</strong> Para tareas de máxima prioridad</li>
                        <li>🔔 <strong>Recordatorio:</strong> Para recordatorios generales</li>
                        <li>ℹ️ <strong>Informativa:</strong> Para información adicional</li>
                    </ul>
                </li>
                <li><strong>Estado Activo:</strong> Indica si la alerta está actualmente vigente</li>
                <li><strong>Asociación:</strong> Cada alerta está vinculada a una tarea específica</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>

<style>
    .stats-summary {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 1rem;
        margin-bottom: 2rem;
    }

    .stat-card {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 1.5rem;
        border-radius: 10px;
        text-align: center;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .stat-number {
        font-size: 2.5rem;
        font-weight: bold;
        margin-bottom: 0.5rem;
    }

    .stat-label {
        font-size: 0.9rem;
        opacity: 0.9;
    }

    .table-container {
        overflow-x: auto;
        background: white;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .data-table {
        width: 100%;
        border-collapse: collapse;
    }

    .data-table thead {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
    }

    .data-table th {
        padding: 1rem;
        text-align: left;
        font-weight: 600;
    }

    .data-table td {
        padding: 1rem;
        border-bottom: 1px solid #e2e8f0;
    }

    .data-table tbody tr:hover {
        background-color: #f7fafc;
    }

    .row-active {
        background-color: #f0fff4;
    }

    .row-inactive {
        background-color: #f7fafc;
        opacity: 0.7;
    }

    .text-column {
        max-width: 300px;
    }

    .text-preview {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .date-display {
        white-space: nowrap;
        font-size: 0.9rem;
    }

    .badge {
        display: inline-block;
        padding: 0.4rem 0.8rem;
        border-radius: 20px;
        font-size: 0.85rem;
        font-weight: 600;
        white-space: nowrap;
    }

    .badge-urgente {
        background-color: #fee;
        color: #c00;
        border: 1px solid #fcc;
    }

    .badge-recordatorio {
        background-color: #e3f2fd;
        color: #1565c0;
        border: 1px solid #90caf9;
    }

    .badge-informativa {
        background-color: #f3e5f5;
        color: #6a1b9a;
        border: 1px solid #ce93d8;
    }

    .badge-success {
        background-color: #e8f5e9;
        color: #2e7d32;
        border: 1px solid #81c784;
    }

    .badge-secondary {
        background-color: #f5f5f5;
        color: #757575;
        border: 1px solid #e0e0e0;
    }

    .task-info {
        font-size: 0.9rem;
    }

    .task-info strong {
        color: #2d3748;
    }

    .text-muted {
        color: #a0aec0;
        font-style: italic;
    }

    .empty-state {
        text-align: center;
        padding: 3rem 1rem;
    }

    .empty-icon {
        font-size: 5rem;
        margin-bottom: 1rem;
        opacity: 0.5;
    }

    .empty-state h3 {
        color: #2d3748;
        margin-bottom: 0.5rem;
    }

    .empty-state p {
        color: #718096;
        margin-bottom: 2rem;
    }
</style>
</body>
</html>
