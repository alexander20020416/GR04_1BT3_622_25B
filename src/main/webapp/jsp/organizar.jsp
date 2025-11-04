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
        <a href="${pageContext.request.contextPath}/home" class="btn-link">← Volver al inicio</a>
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
        <c:if test="${not empty param.mensaje}">
            <div class="alert alert-success">
                ✓ ${param.mensaje}
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${error}
            </div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${param.error}
            </div>
        </c:if>

        <!-- Lista de tareas -->
        <div class="tareas-section">
            <h2>📌 Tareas Activas</h2>

            <c:choose>
                <c:when test="${empty tareas}">
                    <div class="empty-state">
                        <p>✅ ¡Excelente! No tienes tareas activas pendientes.</p>
                        <p>Para crear tareas, debe <a href="${pageContext.request.contextPath}/planificar">planificar una tarea</a>.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tareas-count">
                        Tareas activas: <strong>${tareas.size()}</strong>
                    </div>

                    <div class="tareas-grid-modern">
                        <c:forEach var="tarea" items="${tareas}">
                            <div class="tarea-card-modern prioridad-${tarea.prioridad.toLowerCase()}">
                                <div class="card-ribbon">
                                    <span class="badge-prioridad badge-${tarea.prioridad.toLowerCase()}">
                                        ${tarea.prioridad == 'Alta' ? '🔴' : tarea.prioridad == 'Media' ? '🟡' : '🟢'} ${tarea.prioridad}
                                    </span>
                                </div>
                                
                                <div class="card-content">
                                    <div class="card-header-modern">
                                        <h3 class="tarea-titulo-modern">${tarea.titulo}</h3>
                                        <span class="tarea-id">#${tarea.id}</span>
                                    </div>

                                    <p class="tarea-descripcion-modern">${tarea.descripcion}</p>

                                    <div class="tarea-info-grid">
                                        <div class="info-item">
                                            <span class="info-icon">📊</span>
                                            <div class="info-content">
                                                <span class="info-label">Estado</span>
                                                <form action="${pageContext.request.contextPath}/cambiar-estado" 
                                                      method="post" 
                                                      class="estado-form"
                                                      onchange="this.submit()">
                                                    <input type="hidden" name="id" value="${tarea.id}">
                                                    <select name="estado" class="estado-select estado-${tarea.estado.toLowerCase().replace(' ', '-')}">
                                                        <option value="Pendiente" ${tarea.estado == 'Pendiente' ? 'selected' : ''}>⏳ Pendiente</option>
                                                        <option value="En Progreso" ${tarea.estado == 'En Progreso' ? 'selected' : ''}>🔄 En Progreso</option>
                                                        <option value="Completada" ${tarea.estado == 'Completada' ? 'selected' : ''}>✅ Completada</option>
                                                    </select>
                                                </form>
                                            </div>
                                        </div>

                                        <div class="info-item">
                                            <span class="info-icon">📅</span>
                                            <div class="info-content">
                                                <span class="info-label">Vencimiento</span>
                                                <span class="info-value">${not empty tarea.fechaVencimiento ? tarea.fechaVencimiento : 'Sin fecha'}</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-actions">
                                        <a href="${pageContext.request.contextPath}/editar-tarea?id=${tarea.id}" 
                                           class="btn-modern btn-edit-modern">
                                            <span class="btn-icon">📝</span>
                                            <span class="btn-text">Editar</span>
                                        </a>
                                        <form action="${pageContext.request.contextPath}/eliminar-tarea" 
                                              method="post" 
                                              style="display:inline; flex: 1;"
                                              onsubmit="return confirm('¿Está seguro de eliminar la tarea: ${tarea.titulo}?');">
                                            <input type="hidden" name="id" value="${tarea.id}">
                                            <button type="submit" class="btn-modern btn-delete-modern">
                                                <span class="btn-icon">🗑️</span>
                                                <span class="btn-text">Eliminar</span>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Sección de tareas vencidas -->
        <c:if test="${not empty tareasVencidas}">
            <div class="tareas-section tareas-vencidas-section">
                <h2>⚠️ Tareas Vencidas</h2>
                <p class="vencidas-subtitle">Estas tareas han pasado su fecha de vencimiento y no están completadas</p>
                
                <div class="tareas-count vencidas-count">
                    Tareas vencidas: <strong>${tareasVencidas.size()}</strong>
                </div>

                <div class="tareas-grid-modern">
                    <c:forEach var="tarea" items="${tareasVencidas}">
                        <div class="tarea-card-modern tarea-vencida prioridad-${tarea.prioridad.toLowerCase()}">
                            <div class="vencida-badge">⚠️ VENCIDA</div>
                            
                            <div class="card-ribbon">
                                <span class="badge-prioridad badge-${tarea.prioridad.toLowerCase()}">
                                    ${tarea.prioridad == 'Alta' ? '🔴' : tarea.prioridad == 'Media' ? '🟡' : '🟢'} ${tarea.prioridad}
                                </span>
                            </div>
                            
                            <div class="card-content">
                                <div class="card-header-modern">
                                    <h3 class="tarea-titulo-modern">${tarea.titulo}</h3>
                                    <span class="tarea-id">#${tarea.id}</span>
                                </div>

                                <p class="tarea-descripcion-modern">${tarea.descripcion}</p>

                                <div class="tarea-info-grid">
                                    <div class="info-item">
                                        <span class="info-icon">📊</span>
                                        <div class="info-content">
                                            <span class="info-label">Estado</span>
                                            <form action="${pageContext.request.contextPath}/cambiar-estado" 
                                                  method="post" 
                                                  class="estado-form"
                                                  onchange="this.submit()">
                                                <input type="hidden" name="id" value="${tarea.id}">
                                                <select name="estado" class="estado-select estado-${tarea.estado.toLowerCase().replace(' ', '-')}">
                                                    <option value="Pendiente" ${tarea.estado == 'Pendiente' ? 'selected' : ''}>⏳ Pendiente</option>
                                                    <option value="En Progreso" ${tarea.estado == 'En Progreso' ? 'selected' : ''}>🔄 En Progreso</option>
                                                    <option value="Completada" ${tarea.estado == 'Completada' ? 'selected' : ''}>✅ Completada</option>
                                                </select>
                                            </form>
                                        </div>
                                    </div>

                                    <div class="info-item info-vencida">
                                        <span class="info-icon">⏰</span>
                                        <div class="info-content">
                                            <span class="info-label">Venció</span>
                                            <span class="info-value fecha-vencida">${tarea.fechaVencimiento}</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="card-actions">
                                    <a href="${pageContext.request.contextPath}/editar-tarea?id=${tarea.id}" 
                                       class="btn-modern btn-edit-modern">
                                        <span class="btn-icon">📝</span>
                                        <span class="btn-text">Editar</span>
                                    </a>
                                    <form action="${pageContext.request.contextPath}/eliminar-tarea" 
                                          method="post" 
                                          style="display:inline; flex: 1;"
                                          onsubmit="return confirm('¿Está seguro de eliminar la tarea: ${tarea.titulo}?');">
                                        <input type="hidden" name="id" value="${tarea.id}">
                                        <button type="submit" class="btn-modern btn-delete-modern">
                                            <span class="btn-icon">🗑️</span>
                                            <span class="btn-text">Eliminar</span>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <div class="info-box">
            <h3>ℹ️ Información del Sistema</h3>
            <p>Esta vista implementa el caso de uso <strong>"Organizar Tareas"</strong> del Incremento 1.</p>
            <ul>
                <li><strong>Patrón Strategy:</strong> Permite cambiar el criterio de ordenamiento dinámicamente</li>
                <li><strong>Cambio de Estado:</strong> Puedes cambiar el estado directamente desde el selector (auto-guarda)</li>
                <li><strong>Tareas Activas:</strong> Se muestran primero las tareas vigentes (no vencidas o completadas)</li>
                <li><strong>Tareas Vencidas:</strong> Se separan automáticamente las que pasaron su fecha límite</li>
                <li><strong>Prioridad:</strong> Ordena de mayor a menor (Alta → Media → Baja)</li>
                <li><strong>Fecha:</strong> Ordena por proximidad de vencimiento</li>
                <li><strong>Alfabético:</strong> Ordena por título de A a Z</li>
                <li><strong>CRUD Completo:</strong> Editar y eliminar tareas con confirmación</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>

<style>
    /* ========== Header con Notificaciones ========== */
    .header-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 20px;
    }

    .header-left {
        flex: 1;
    }

    .header-left h1 {
        margin-bottom: 10px;
    }

    .header-right {
        display: flex;
        align-items: center;
        gap: 15px;
    }

    /* ========== Grid Moderno ========== */
    .tareas-grid-modern {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(420px, 1fr));
        gap: 30px;
        padding: 10px;
        margin-bottom: 30px;
    }

    /* ========== Tarjetas Modernas ========== */
    .tarea-card-modern {
        background: white;
        border-radius: 20px;
        overflow: hidden;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        position: relative;
        border: 2px solid transparent;
        min-height: 280px;
        display: flex;
        flex-direction: column;
    }

    .tarea-card-modern:hover {
        transform: translateY(-8px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
    }

    /* Bordes de colores según prioridad */
    .tarea-card-modern.prioridad-alta {
        border-left: 5px solid #ef4444;
    }

    .tarea-card-modern.prioridad-media {
        border-left: 5px solid #f59e0b;
    }

    .tarea-card-modern.prioridad-baja {
        border-left: 5px solid #10b981;
    }

    /* ========== Ribbon de Prioridad ========== */
    .card-ribbon {
        position: absolute;
        top: 12px;
        right: -8px;
        z-index: 10;
    }

    .badge-prioridad {
        padding: 6px 16px;
        border-radius: 20px 0 0 20px;
        font-weight: 600;
        font-size: 12px;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        box-shadow: -2px 2px 8px rgba(0, 0, 0, 0.15);
    }

    .badge-alta {
        background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
        color: white;
    }

    .badge-media {
        background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
        color: white;
    }

    .badge-baja {
        background: linear-gradient(135deg, #10b981 0%, #059669 100%);
        color: white;
    }

    /* ========== Contenido de la Tarjeta ========== */
    .card-content {
        padding: 28px 24px;
        padding-top: 24px;
        flex: 1;
        display: flex;
        flex-direction: column;
    }

    .card-header-modern {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 18px;
        padding-right: 90px; /* Espacio para el ribbon */
        gap: 12px;
    }

    .tarea-titulo-modern {
        font-size: 20px;
        font-weight: 700;
        color: #1f2937;
        margin: 0;
        line-height: 1.4;
        flex: 1;
    }

    .tarea-id {
        font-size: 11px;
        color: #9ca3af;
        font-weight: 600;
        background: #f3f4f6;
        padding: 5px 12px;
        border-radius: 14px;
        white-space: nowrap;
    }

    .tarea-descripcion-modern {
        color: #6b7280;
        font-size: 14px;
        line-height: 1.7;
        margin-bottom: 24px;
        flex: 1;
        min-height: 60px;
    }

    /* ========== Grid de Información ========== */
    .tarea-info-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 16px;
        margin-bottom: 20px;
    }

    .info-item {
        display: flex;
        align-items: center;
        gap: 12px;
        background: #f9fafb;
        padding: 12px;
        border-radius: 12px;
    }

    .info-icon {
        font-size: 24px;
    }

    .info-content {
        display: flex;
        flex-direction: column;
        gap: 4px;
    }

    .info-label {
        font-size: 11px;
        color: #9ca3af;
        text-transform: uppercase;
        font-weight: 600;
        letter-spacing: 0.5px;
    }

    .info-value {
        font-size: 14px;
        color: #374151;
        font-weight: 600;
    }

    /* ========== Badges de Estado ========== */
    .badge-estado {
        padding: 4px 12px;
        border-radius: 12px;
        font-size: 12px;
        font-weight: 600;
        display: inline-block;
    }

    .badge-estado-pendiente {
        background: #fef3c7;
        color: #92400e;
    }

    .badge-estado-en-progreso {
        background: #dbeafe;
        color: #1e40af;
    }

    .badge-estado-completada {
        background: #d1fae5;
        color: #065f46;
    }

    /* ========== Selector de Estado ========== */
    .estado-form {
        margin: 0;
        padding: 0;
    }

    .estado-select {
        padding: 6px 12px;
        border-radius: 12px;
        font-size: 12px;
        font-weight: 600;
        border: 2px solid transparent;
        cursor: pointer;
        transition: all 0.3s ease;
        outline: none;
    }

    .estado-select:hover {
        transform: scale(1.05);
    }

    .estado-select:focus {
        border-color: #667eea;
        box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    }

    .estado-pendiente {
        background: #fef3c7;
        color: #92400e;
    }

    .estado-en-progreso {
        background: #dbeafe;
        color: #1e40af;
    }

    .estado-completada {
        background: #d1fae5;
        color: #065f46;
    }

    /* ========== Botones Modernos ========== */
    .card-actions {
        display: flex;
        gap: 10px;
        margin-top: 20px;
    }

    .btn-modern {
        flex: 1;
        padding: 12px 20px;
        border: none;
        border-radius: 12px;
        cursor: pointer;
        font-size: 14px;
        font-weight: 600;
        text-decoration: none;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        position: relative;
        overflow: hidden;
    }

    .btn-modern::before {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        width: 0;
        height: 0;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.3);
        transform: translate(-50%, -50%);
        transition: width 0.6s, height 0.6s;
    }

    .btn-modern:hover::before {
        width: 300px;
        height: 300px;
    }

    .btn-icon {
        font-size: 18px;
        z-index: 1;
    }

    .btn-text {
        z-index: 1;
    }

    .btn-edit-modern {
        background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
        color: white;
        box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
    }

    .btn-edit-modern:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
    }

    .btn-delete-modern {
        background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
        color: white;
        box-shadow: 0 4px 12px rgba(239, 68, 68, 0.3);
    }

    .btn-delete-modern:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(239, 68, 68, 0.4);
    }

    /* ========== Responsive ========== */
    @media (max-width: 768px) {
        .tareas-grid-modern {
            grid-template-columns: 1fr;
        }

        .tarea-info-grid {
            grid-template-columns: 1fr;
        }
    }

    /* ========== Sección Tareas Vencidas ========== */
    .tareas-vencidas-section {
        margin-top: 40px;
        padding-top: 30px;
        border-top: 3px dashed #f59e0b;
    }

    .vencidas-subtitle {
        color: #92400e;
        font-size: 14px;
        margin-bottom: 16px;
        font-weight: 500;
    }

    .vencidas-count {
        background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
        color: #92400e;
        padding: 12px 20px;
        border-radius: 12px;
        display: inline-block;
        font-weight: 600;
        margin-bottom: 20px;
        border: 2px solid #fbbf24;
    }

    .tarea-vencida {
        border: 2px solid #f59e0b !important;
        background: #fffbeb;
        position: relative;
        animation: pulseVencida 2s infinite;
    }

    @keyframes pulseVencida {
        0%, 100% {
            box-shadow: 0 4px 6px rgba(245, 158, 11, 0.3);
        }
        50% {
            box-shadow: 0 8px 16px rgba(245, 158, 11, 0.5);
        }
    }

    .vencida-badge {
        position: absolute;
        top: 0;
        left: 0;
        background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
        color: white;
        padding: 6px 12px;
        font-size: 11px;
        font-weight: 700;
        letter-spacing: 0.5px;
        border-radius: 16px 0 12px 0;
        z-index: 5;
    }

    .info-vencida {
        background: #fee2e2 !important;
        border: 1px solid #fca5a5;
    }

    .fecha-vencida {
        color: #dc2626 !important;
        font-weight: 700 !important;
    }
</style>
</body>
</html>