<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Actividades - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        /* ========== Grid de Actividades ========== */
        .activities-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
            gap: 24px;
            padding: 5px;
        }

        /* ========== Tarjeta de Actividad Moderna ========== */
        .activity-card {
            background: white;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border: 2px solid transparent;
            position: relative;
        }

        .activity-card:hover {
            transform: translateY(-6px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
            border-color: #667eea;
        }

        .activity-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 16px;
        }

        .activity-header h3 {
            color: white;
            margin: 0;
            font-size: 18px;
            font-weight: 700;
            flex: 1;
        }

        .activity-body {
            padding: 20px;
        }

        .activity-body p {
            color: #4a5568;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 16px;
        }

        .activity-meta {
            display: flex;
            flex-direction: column;
            gap: 10px;
            background: #f9fafb;
            padding: 16px;
            border-radius: 12px;
            margin-bottom: 16px;
        }

        .activity-meta span {
            font-size: 13px;
            color: #374151;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        /* ========== Badges para Actividades ========== */
        .badge {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.3px;
        }

        .badge-danger {
            background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
            color: #991b1b;
            border: 1px solid #fca5a5;
        }

        .badge-warning {
            background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
            color: #92400e;
            border: 1px solid #fbbf24;
        }

        .badge-info {
            background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
            color: #065f46;
            border: 1px solid #6ee7b7;
        }

        /* ========== Botones de Acción en Actividades ========== */
        .activity-actions {
            display: flex;
            gap: 10px;
            padding: 0 20px 20px 20px;
        }

        .btn-sm {
            flex: 1;
            padding: 10px 16px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
            transition: all 0.3s ease;
        }

        .btn-success {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
            box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
        }

        .btn-success:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(16, 185, 129, 0.4);
        }

        .btn-info {
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            color: white;
            box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
        }

        .btn-info:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
        }

        /* ========== Empty State Mejorado ========== */
        .empty-state {
            text-align: center;
            padding: 80px 20px;
            background: linear-gradient(135deg, #f9fafb 0%, #f3f4f6 100%);
            border-radius: 20px;
            border: 3px dashed #d1d5db;
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

        /* ========== Header Mejorado ========== */
        header nav {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        /* ========== Responsive ========== */
        @media (max-width: 768px) {
            .activities-list {
                grid-template-columns: 1fr;
            }

            .activity-header {
                flex-direction: column;
                text-align: center;
            }

            .activity-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📋 Listado de Actividades</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/home" class="btn-link">← Volver al inicio</a>
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
                                <span>📅 <strong>Entrega:</strong> ${actividad.fechaEntrega}</span>
                                <span>📊 <strong>Estado:</strong> ${actividad.estado}</span>
                            </div>
                        </div>

                        <div class="activity-actions">
                            <a href="${pageContext.request.contextPath}/agregar-tarea?actividadId=${actividad.id}" 
                               class="btn-sm btn-success">
                                ➕ Agregar Tarea
                            </a>
                            <a href="${pageContext.request.contextPath}/ver-tareas?actividadId=${actividad.id}" 
                               class="btn-sm btn-info">
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
