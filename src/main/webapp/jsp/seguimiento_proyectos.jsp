<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seguimiento de Proyectos - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }

        .seguimiento-container {
            max-width: 1400px;
            margin: 0 auto;
        }

        .page-header {
            background: white;
            border-radius: 20px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 20px;
        }

        .page-header h1 {
            color: #1f2937;
            font-size: 32px;
            font-weight: 700;
            margin: 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .header-actions {
            display: flex;
            gap: 12px;
        }

        .btn-primary {
            padding: 12px 24px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            border: none;
            cursor: pointer;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            padding: 12px 24px;
            background: white;
            color: #667eea;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            border: 2px solid #667eea;
        }

        .btn-secondary:hover {
            background: #667eea;
            color: white;
        }

        /* Mensaje de éxito */
        .success-message {
            background: #d1fae5;
            color: #065f46;
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            border-left: 4px solid #10b981;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 10px;
            animation: slideDown 0.5s ease-out;
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Grid de proyectos */
        .proyectos-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
            gap: 24px;
        }

        /* Card de proyecto */
        .proyecto-card {
            background: white;
            border-radius: 16px;
            padding: 24px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .proyecto-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
        }

        /* Indicador de urgencia */
        .urgencia-indicator {
            position: absolute;
            top: 0;
            left: 0;
            width: 6px;
            height: 100%;
        }

        .urgencia-indicator.red {
            background: #dc2626;
        }

        .urgencia-indicator.orange {
            background: #f59e0b;
        }

        .urgencia-indicator.green {
            background: #10b981;
        }

        .urgencia-indicator.gray {
            background: #9ca3af;
        }

        .proyecto-header {
            display: flex;
            justify-content: space-between;
            align-items: start;
            margin-bottom: 16px;
            padding-left: 12px;
        }

        .proyecto-header h3 {
            color: #1f2937;
            font-size: 20px;
            font-weight: 700;
            margin: 0 0 8px 0;
        }

        .proyecto-materia {
            display: inline-block;
            background: #e0e7ff;
            color: #4338ca;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }

        .proyecto-descripcion {
            color: #6b7280;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 16px;
            padding-left: 12px;
        }

        /* Barra de progreso */
        .progreso-section {
            margin-bottom: 16px;
            padding-left: 12px;
        }

        .progreso-label {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 8px;
            font-size: 13px;
            font-weight: 600;
            color: #374151;
        }

        .progreso-bar {
            width: 100%;
            height: 10px;
            background: #e5e7eb;
            border-radius: 10px;
            overflow: hidden;
        }

        .progreso-fill {
            height: 100%;
            background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
            transition: width 0.5s ease;
            border-radius: 10px;
        }

        /* Tareas del proyecto */
        .tareas-section {
            margin-top: 16px;
            padding-top: 16px;
            border-top: 2px dashed #e5e7eb;
            padding-left: 12px;
        }

        .tareas-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 12px;
        }

        .tareas-header h4 {
            color: #374151;
            font-size: 14px;
            font-weight: 600;
            margin: 0;
        }

        .tarea-item {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 8px 12px;
            background: #f9fafb;
            border-radius: 8px;
            margin-bottom: 8px;
            font-size: 13px;
        }

        .tarea-status {
            width: 20px;
            height: 20px;
            border-radius: 50%;
            flex-shrink: 0;
        }

        .tarea-status.completada {
            background: #10b981;
        }

        .tarea-status.en-progreso {
            background: #f59e0b;
        }

        .tarea-status.pendiente {
            background: #6b7280;
        }

        .tarea-nombre {
            flex: 1;
            color: #374151;
        }

        /* Botones de acción del proyecto */
        .proyecto-actions {
            display: flex;
            gap: 8px;
            margin-top: 16px;
            padding-left: 12px;
        }

        .btn-small {
            flex: 1;
            padding: 8px 12px;
            background: #f3f4f6;
            color: #4b5563;
            text-decoration: none;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-align: center;
            transition: all 0.2s ease;
            border: none;
            cursor: pointer;
        }

        .btn-small:hover {
            background: #667eea;
            color: white;
        }

        /* Empty state */
        .empty-state {
            background: white;
            border-radius: 20px;
            padding: 60px 20px;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .empty-state h3 {
            color: #374151;
            font-size: 24px;
            margin-bottom: 12px;
        }

        .empty-state p {
            color: #6b7280;
            font-size: 16px;
            margin-bottom: 24px;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .proyectos-grid {
                grid-template-columns: 1fr;
            }

            .page-header {
                flex-direction: column;
                align-items: stretch;
            }

            .header-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<div class="seguimiento-container">
    <!-- Header -->
    <div class="page-header">
        <h1>📊 Seguimiento de Proyectos</h1>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/listarMateria" class="btn-secondary">
                ← Volver a Materias
            </a>
        </div>
    </div>

    <!-- Mensaje de éxito -->
    <c:if test="${not empty param.mensaje}">
        <div class="success-message">
            <span>✅</span>
            <span>${param.mensaje}</span>
        </div>
    </c:if>

    <!-- Grid de proyectos -->
    <c:choose>
        <c:when test="${not empty proyectos}">
            <div class="proyectos-grid">
                <c:forEach var="proyecto" items="${proyectos}">
                    <div class="proyecto-card">
                        <!-- Indicador de urgencia -->
                        <div class="urgencia-indicator ${proyecto.colorIndicador}"></div>

                        <!-- Header del proyecto -->
                        <div class="proyecto-header">
                            <div>
                                <h3>${proyecto.titulo}</h3>
                                <c:if test="${not empty proyecto.materia}">
                                        <span class="proyecto-materia">
                                            📚 ${proyecto.materia.nombre}
                                        </span>
                                </c:if>
                            </div>
                        </div>

                        <!-- Descripción -->
                        <c:if test="${not empty proyecto.descripcion}">
                            <p class="proyecto-descripcion">${proyecto.descripcion}</p>
                        </c:if>

                        <!-- Progreso -->
                        <div class="progreso-section">
                            <div class="progreso-label">
                                <span>Progreso</span>
                                <span>${proyecto.calcularProgreso()}%</span>
                            </div>
                            <div class="progreso-bar">
                                <div class="progreso-fill" style="width: ${proyecto.calcularProgreso()}%"></div>
                            </div>
                        </div>

                        <!-- Fecha de vencimiento -->
                        <c:if test="${not empty proyecto.fechaVencimiento}">
                            <div class="proyecto-meta" style="padding-left: 12px; margin-bottom: 12px;">
                                    <span style="font-size: 13px; color: #6b7280;">
                                        📅 Vence: ${proyecto.fechaVencimiento}
                                    </span>
                            </div>
                        </c:if>

                        <!-- Tareas del proyecto -->
                        <div class="tareas-section">
                            <div class="tareas-header">
                                <h4>📋 Tareas (${proyecto.tareas.size()})</h4>
                            </div>

                            <c:choose>
                                <c:when test="${not empty proyecto.tareas}">
                                    <c:forEach var="tarea" items="${proyecto.tareas}" end="2">
                                        <div class="tarea-item">
                                            <div class="tarea-status ${tarea.estado.toLowerCase().replace(' ', '-')}"></div>
                                            <span class="tarea-nombre">${tarea.titulo}</span>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${proyecto.tareas.size() > 3}">
                                        <p style="font-size: 12px; color: #9ca3af; margin-top: 8px;">
                                            +${proyecto.tareas.size() - 3} tareas más
                                        </p>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <p style="font-size: 12px; color: #9ca3af; text-align: center; padding: 12px;">
                                        Sin tareas asignadas
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <!-- Acciones -->
                        <div class="proyecto-actions">
                            <a href="${pageContext.request.contextPath}/verProyecto?id=${proyecto.id}"
                               class="btn-small">
                                👁️ Ver Detalles
                            </a>
                            <a href="${pageContext.request.contextPath}/agregarTarea?proyectoId=${proyecto.id}"
                               class="btn-small">
                                ➕ Agregar Tarea
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <h3>📭 No hay proyectos creados</h3>
                <p>Comienza creando tu primer proyecto para dar seguimiento a trabajos importantes</p>
                <a href="${pageContext.request.contextPath}/listarMateria" class="btn-primary">
                    ➕ Ir a Materias
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
