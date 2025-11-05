<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle del Proyecto - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }

        .detalle-container {
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Header del Proyecto */
        .proyecto-hero {
            background: white;
            border-radius: 20px;
            padding: 40px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            border-left: 8px solid #667eea;
            position: relative;
        }

        .proyecto-hero h1 {
            color: #1f2937;
            font-size: 36px;
            font-weight: 700;
            margin: 0 0 15px 0;
        }

        .proyecto-meta {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
            margin-bottom: 20px;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px 16px;
            background: #f3f4f6;
            border-radius: 10px;
            font-size: 14px;
            color: #374151;
        }

        .proyecto-descripcion {
            color: #6b7280;
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 25px;
        }

        /* Barra de progreso grande */
        .progreso-grande {
            margin-bottom: 25px;
        }

        .progreso-label {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .progreso-label span {
            font-size: 18px;
            font-weight: 700;
            color: #374151;
        }

        .progreso-bar-grande {
            width: 100%;
            height: 30px;
            background: #e5e7eb;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .progreso-fill-grande {
            height: 100%;
            background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
            transition: width 0.5s ease;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            padding-right: 15px;
            color: white;
            font-weight: 700;
            font-size: 14px;
        }

        /* Botones de acción */
        .action-buttons {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .btn-action {
            padding: 12px 24px;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            background: #f3f4f6;
            color: #374151;
            border: 2px solid #e5e7eb;
        }

        .btn-secondary:hover {
            background: #e5e7eb;
        }

        .btn-danger {
            background: #fee2e2;
            color: #991b1b;
            border: 2px solid #fca5a5;
        }

        .btn-danger:hover {
            background: #fca5a5;
            color: white;
        }

        /* Sección de Tareas */
        .tareas-section {
            background: white;
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .tareas-section h2 {
            color: #1f2937;
            font-size: 24px;
            font-weight: 700;
            margin: 0 0 20px 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .tarea-card {
            background: #f9fafb;
            border: 2px solid #e5e7eb;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }

        .tarea-card:hover {
            border-color: #667eea;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.1);
        }

        .tarea-header {
            display: flex;
            justify-content: space-between;
            align-items: start;
            margin-bottom: 12px;
        }

        .tarea-header h3 {
            color: #1f2937;
            font-size: 18px;
            font-weight: 600;
            margin: 0;
            flex: 1;
        }

        .tarea-estado {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
            text-transform: uppercase;
        }

        .estado-pendiente {
            background: #f3f4f6;
            color: #6b7280;
        }

        .estado-en-progreso {
            background: #fef3c7;
            color: #92400e;
        }

        .estado-completada {
            background: #d1fae5;
            color: #065f46;
        }

        .tarea-descripcion {
            color: #6b7280;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 12px;
        }

        .tarea-meta {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            margin-bottom: 12px;
        }

        .tarea-meta span {
            font-size: 13px;
            color: #6b7280;
        }

        .tarea-actions {
            display: flex;
            gap: 8px;
            margin-top: 12px;
            padding-top: 12px;
            border-top: 1px solid #e5e7eb;
        }

        .btn-small {
            padding: 8px 16px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            border: none;
            cursor: pointer;
            transition: all 0.2s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .btn-completar {
            background: #d1fae5;
            color: #065f46;
        }

        .btn-completar:hover {
            background: #10b981;
            color: white;
        }

        .btn-completar:disabled {
            background: #e5e7eb;
            color: #9ca3af;
            cursor: not-allowed;
        }

        .btn-editar {
            background: #dbeafe;
            color: #1e40af;
        }

        .btn-editar:hover {
            background: #3b82f6;
            color: white;
        }

        .empty-tareas {
            text-align: center;
            padding: 60px 20px;
            color: #9ca3af;
        }

        .empty-tareas h3 {
            font-size: 20px;
            margin-bottom: 10px;
        }

        .back-button {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s ease;
            margin-bottom: 20px;
            border: 2px solid rgba(255, 255, 255, 0.3);
        }

        .back-button:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateX(-4px);
        }

        .alert {
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-weight: 500;
        }

        .alert-success {
            background: #d1fae5;
            color: #065f46;
            border-left: 4px solid #10b981;
        }

        .alert-error {
            background: #fee2e2;
            color: #991b1b;
            border-left: 4px solid #dc2626;
        }
    </style>
</head>
<body>
<div class="detalle-container">
    <a href="${pageContext.request.contextPath}/seguimiento" class="back-button">
        ← Volver al Seguimiento
    </a>

    <!-- Mensajes -->
    <c:if test="${not empty param.mensaje}">
        <div class="alert alert-success">
            ✅ ${param.mensaje}
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-error">
            ❌ ${param.error}
        </div>
    </c:if>

    <!-- Hero del Proyecto -->
    <div class="proyecto-hero">
        <h1>📋 ${proyecto.titulo}</h1>

        <div class="proyecto-meta">
            <c:if test="${not empty proyecto.materia}">
                <div class="meta-item">
                    📚 <strong>${proyecto.materia.nombre}</strong>
                </div>
            </c:if>

            <c:if test="${not empty proyecto.fechaVencimiento}">
                <div class="meta-item">
                    📅 Vence: <strong>${proyecto.fechaVencimiento}</strong>
                </div>
            </c:if>

            <div class="meta-item">
                📊 Estado: <strong style="color: ${proyecto.colorIndicador};">${proyecto.determinarEstadoUrgencia()}</strong>
            </div>
        </div>

        <c:if test="${not empty proyecto.descripcion}">
            <p class="proyecto-descripcion">${proyecto.descripcion}</p>
        </c:if>

        <!-- Progreso Grande -->
        <div class="progreso-grande">
            <div class="progreso-label">
                <span>Progreso del Proyecto</span>
                <span style="color: #667eea;">${proyecto.calcularProgreso()}%</span>
            </div>
            <div class="progreso-bar-grande">
                <div class="progreso-fill-grande" style="width: ${proyecto.calcularProgreso()}%">
                    <c:if test="${proyecto.calcularProgreso() > 10}">
                        ${proyecto.calcularProgreso()}%
                    </c:if>
                </div>
            </div>
        </div>

        <!-- Botones de Acción -->
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/agregarTarea?proyectoId=${proyecto.id}" class="btn-action btn-primary">
                ➕ Agregar Tarea
            </a>
            <a href="${pageContext.request.contextPath}/editarProyecto?id=${proyecto.id}" class="btn-action btn-secondary">
                ✏️ Editar Proyecto
            </a>
            <button onclick="confirmarEliminar(${proyecto.id}, '${proyecto.titulo}', ${proyecto.materia != null ? proyecto.materia.id : 'null'})" 
                    class="btn-action btn-danger">
                🗑️ Eliminar Proyecto
            </button>
        </div>
    </div>

    <!-- Sección de Tareas -->
    <div class="tareas-section">
        <h2>
            📝 Tareas del Proyecto 
            <span style="color: #667eea; font-size: 20px;">(${proyecto.tareas.size()})</span>
        </h2>

        <c:choose>
            <c:when test="${not empty proyecto.tareas}">
                <c:forEach var="tarea" items="${proyecto.tareas}">
                    <div class="tarea-card">
                        <div class="tarea-header">
                            <h3>${tarea.titulo}</h3>
                            <span class="tarea-estado estado-${tarea.estado.toLowerCase().replace(' ', '-')}">
                                ${tarea.estado}
                            </span>
                        </div>

                        <c:if test="${not empty tarea.descripcion}">
                            <p class="tarea-descripcion">${tarea.descripcion}</p>
                        </c:if>

                        <div class="tarea-meta">
                            <span>📅 Vence: <strong>${tarea.fechaVencimiento}</strong></span>
                            <span>⚡ Prioridad: <strong>${tarea.prioridad}</strong></span>
                            <c:if test="${not empty tarea.materia}">
                                <span>📚 Materia: <strong>${tarea.materia.nombre}</strong></span>
                            </c:if>
                        </div>

                        <div class="tarea-actions">
                            <c:if test="${tarea.estado != 'Completada'}">
                                <form action="${pageContext.request.contextPath}/cambiarEstadoTarea" method="post" style="display: inline;">
                                    <input type="hidden" name="tareaId" value="${tarea.id}">
                                    <input type="hidden" name="nuevoEstado" value="Completada">
                                    <input type="hidden" name="proyectoId" value="${proyecto.id}">
                                    <button type="submit" class="btn-small btn-completar">
                                        ✓ Marcar como Completada
                                    </button>
                                </form>
                            </c:if>

                            <c:if test="${tarea.estado == 'Completada'}">
                                <button class="btn-small btn-completar" disabled>
                                    ✓ Completada
                                </button>
                            </c:if>

                            <a href="${pageContext.request.contextPath}/editar-tarea?id=${tarea.id}&proyectoId=${proyecto.id}" 
                               class="btn-small btn-editar">
                                ✏️ Editar
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty-tareas">
                    <h3>📭 No hay tareas en este proyecto</h3>
                    <p>Agrega tareas para dar seguimiento a tu proyecto</p>
                    <a href="${pageContext.request.contextPath}/agregarTarea?proyectoId=${proyecto.id}" class="btn-action btn-primary">
                        ➕ Agregar Primera Tarea
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
    function confirmarEliminar(id, nombre, materiaId) {
        if (confirm('¿Estás seguro de eliminar el proyecto "' + nombre + '"?\n\nEsta acción no se puede deshacer y eliminará todas las tareas asociadas.')) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/eliminarProyecto';

            const inputId = document.createElement('input');
            inputId.type = 'hidden';
            inputId.name = 'id';
            inputId.value = id;
            form.appendChild(inputId);

            if (materiaId && materiaId !== 'null') {
                const inputMateriaId = document.createElement('input');
                inputMateriaId.type = 'hidden';
                inputMateriaId.name = 'materiaId';
                inputMateriaId.value = materiaId;
                form.appendChild(inputMateriaId);
            }

            document.body.appendChild(form);
            form.submit();
        }
    }
</script>
</body>
</html>
