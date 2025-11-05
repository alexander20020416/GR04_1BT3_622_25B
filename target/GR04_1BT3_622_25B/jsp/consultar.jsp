<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultar Tareas - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        /* ========== Tabla Moderna ========== */
        .table-responsive {
            overflow-x: auto;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            border-radius: 16px;
            background: white;
        }

        .table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin: 0;
        }

        .table thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .table thead th {
            padding: 18px 16px;
            text-align: left;
            font-weight: 600;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            border: none;
        }

        .table thead th:first-child {
            border-top-left-radius: 16px;
        }

        .table thead th:last-child {
            border-top-right-radius: 16px;
        }

        .table tbody tr {
            border-bottom: 1px solid #f0f0f0;
            transition: all 0.3s ease;
        }

        .table tbody tr:hover {
            background: #f8f9ff;
            transform: scale(1.01);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.1);
        }

        .table tbody tr:last-child {
            border-bottom: none;
        }

        .table tbody td {
            padding: 16px;
            font-size: 14px;
            color: #4a5568;
        }

        .table tbody td:first-child {
            font-weight: 600;
            color: #667eea;
            font-size: 13px;
        }

        .descripcion-cell {
            max-width: 300px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        /* ========== Badges Mejorados ========== */
        .badge {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            display: inline-block;
            text-transform: uppercase;
            letter-spacing: 0.3px;
        }

        /* Badges de Prioridad */
        .badge-alta {
            background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
            color: #991b1b;
            border: 1px solid #fca5a5;
        }

        .badge-media {
            background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
            color: #92400e;
            border: 1px solid #fbbf24;
        }

        .badge-baja {
            background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
            color: #065f46;
            border: 1px solid #6ee7b7;
        }

        /* Badges de Estado */
        .badge-estado-pendiente {
            background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
            color: #92400e;
            border: 1px solid #fbbf24;
        }

        .badge-estado-en-progreso {
            background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
            color: #1e40af;
            border: 1px solid #93c5fd;
        }

        .badge-estado-completada {
            background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
            color: #065f46;
            border: 1px solid #6ee7b7;
        }

        /* ========== Botones de Filtro Mejorados ========== */
        .filter-buttons {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .btn-active {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: 2px solid #667eea;
            padding: 12px 24px;
            border-radius: 12px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
        }

        .btn-outline {
            background: white;
            color: #667eea;
            border: 2px solid #e5e7eb;
            padding: 12px 24px;
            border-radius: 12px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-outline:hover {
            border-color: #667eea;
            background: #f8f9ff;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
        }

        /* ========== Stats Mejoradas ========== */
        .tareas-stats {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 24px;
            border-radius: 16px;
            margin-bottom: 24px;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
        }

        .tareas-stats h2 {
            margin: 0 0 8px 0;
            font-size: 20px;
        }

        .tareas-stats p {
            margin: 0;
            font-size: 14px;
            opacity: 0.95;
        }

        .tareas-stats strong {
            font-weight: 700;
            font-size: 18px;
        }

        /* ========== Empty State Mejorado ========== */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: linear-gradient(135deg, #f9fafb 0%, #f3f4f6 100%);
            border-radius: 16px;
            border: 2px dashed #d1d5db;
        }

        .empty-state p {
            font-size: 16px;
            color: #6b7280;
            margin: 0;
        }

        /* ========== Responsive ========== */
        @media (max-width: 768px) {
            .table thead {
                display: none;
            }

            .table tbody tr {
                display: block;
                margin-bottom: 16px;
                border-radius: 12px;
                border: 1px solid #e5e7eb;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            }

            .table tbody td {
                display: block;
                text-align: right;
                padding: 12px 16px;
                position: relative;
            }

            .table tbody td::before {
                content: attr(data-label);
                float: left;
                font-weight: 600;
                color: #667eea;
            }

            .filter-buttons {
                flex-direction: column;
            }
        }

        /* ========== Sección Tareas Vencidas ========== */
        .tareas-vencidas-section {
            margin-top: 40px;
            padding-top: 30px;
            border-top: 3px dashed #f59e0b;
        }

        .tareas-stats-vencidas {
            background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
            color: #92400e;
            border: 2px solid #fbbf24;
        }

        .table-vencidas {
            border: 2px solid #f59e0b;
        }

        .fila-vencida {
            background: #fffbeb !important;
            border-left: 4px solid #f59e0b !important;
        }

        .fila-vencida:hover {
            background: #fef3c7 !important;
        }

        .fecha-vencida-cell {
            color: #dc2626;
            font-weight: 700;
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>🔍 Consultar Tareas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/home" class="btn-link">← Volver al inicio</a>
        </nav>
    </header>

    <main>
        <div class="controls-section">
            <h2>Filtrar Tareas por Estado</h2>

            <div class="filter-buttons">
                <a href="${pageContext.request.contextPath}/consultar"
                   class="btn ${empty filtroActual ? 'btn-active' : 'btn-outline'}">
                    🔍 Activas
                </a>
                <a href="${pageContext.request.contextPath}/consultar?filtro=todos"
                   class="btn ${filtroActual == 'todos' ? 'btn-active' : 'btn-outline'}">
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
        <c:if test="${not empty param.mensaje}">
            <div class="alert alert-success">
                ✓ ${param.mensaje}
            </div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-error">
                ✗ ${param.error}
            </div>
        </c:if>

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

        <!-- Lista de tareas ACTIVAS -->
        <div class="tareas-section">
            <c:choose>
                <c:when test="${empty tareas && empty tareasVencidas}">
                    <div class="empty-state">
                        <p>📭 ${not empty mensaje ? mensaje : 'No hay tareas para mostrar.'}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty tareas}">
                        <div class="tareas-stats">
                            <h2>📌 Tareas Activas</h2>
                            <p>Se encontraron <strong>${tareas.size()}</strong> tarea(s) activa(s)</p>
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
                    </c:if>

                    <!-- Lista de tareas VENCIDAS -->
                    <c:if test="${not empty tareasVencidas}">
                        <div class="tareas-vencidas-section">
                            <div class="tareas-stats tareas-stats-vencidas">
                                <h2>⚠️ Tareas Vencidas</h2>
                                <p>Se encontraron <strong>${tareasVencidas.size()}</strong> tarea(s) vencida(s)</p>
                            </div>

                            <div class="table-responsive table-vencidas">
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
                                    <c:forEach var="tarea" items="${tareasVencidas}">
                                        <tr class="fila-vencida">
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
                                            <td class="fecha-vencida-cell">
                                                    ⚠️ ${tarea.fechaVencimiento}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información del Sistema</h3>
            <p>Esta vista implementa el caso de uso <strong>"Consultar Tareas"</strong>.</p>
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