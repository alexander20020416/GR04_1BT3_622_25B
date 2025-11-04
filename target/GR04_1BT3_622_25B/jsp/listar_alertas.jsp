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
            <a href="${pageContext.request.contextPath}/home" class="btn-link">← Volver al inicio</a>
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

            <c:if test="${not empty mensaje}">
                <div class="alert alert-success">
                    <strong>✓</strong> ${mensaje}
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
                    <!-- Estadísticas -->
                    <div class="stats-summary">
                        <div class="stat-card stat-total">
                            <div class="stat-icon">📊</div>
                            <div class="stat-number">${alertas.size()}</div>
                            <div class="stat-label">Total de Alertas</div>
                        </div>
                        <div class="stat-card stat-active">
                            <div class="stat-icon">✅</div>
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
                        <div class="stat-card stat-urgent">
                            <div class="stat-icon">🚨</div>
                            <div class="stat-number">
                                <c:set var="urgentes" value="0"/>
                                <c:forEach var="alerta" items="${alertas}">
                                    <c:if test="${alerta.tipo == 'Urgente' && alerta.activa}">
                                        <c:set var="urgentes" value="${urgentes + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${urgentes}
                            </div>
                            <div class="stat-label">Urgentes</div>
                        </div>
                    </div>

                    <!-- Grid de Alertas Moderno -->
                    <div class="alertas-grid">
                        <c:forEach var="alerta" items="${alertas}">
                            <div class="alerta-card ${alerta.activa ? 'alerta-activa' : 'alerta-inactiva'} alerta-${alerta.tipo.toLowerCase()}">
                                <!-- Ribbon de estado -->
                                <div class="alerta-ribbon ${alerta.activa ? 'ribbon-activa' : 'ribbon-inactiva'}">
                                    ${alerta.activa ? '✓ Activa' : '○ Inactiva'}
                                </div>

                                <!-- Header con ID y Tipo -->
                                <div class="alerta-header">
                                    <div class="alerta-id-badge">#${alerta.id}</div>
                                    <div class="alerta-tipo-badge">
                                        <c:choose>
                                            <c:when test="${alerta.tipo == 'Urgente'}">🚨 ${alerta.tipo}</c:when>
                                            <c:when test="${alerta.tipo == 'Informativa'}">ℹ️ ${alerta.tipo}</c:when>
                                            <c:otherwise>🔔 ${alerta.tipo}</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <!-- Contenido -->
                                <div class="alerta-content">
                                    <div class="alerta-mensaje">
                                        ${alerta.mensaje}
                                    </div>

                                    <div class="alerta-fecha">
                                        <span class="fecha-icon">📅</span>
                                        <span class="fecha-text">${alerta.fechaAlerta}</span>
                                    </div>

                                    <c:if test="${not empty alerta.tarea}">
                                        <div class="alerta-tarea">
                                            <div class="tarea-label">📋 Tarea asociada:</div>
                                            <div class="tarea-titulo">${alerta.tarea.titulo}</div>
                                            <div class="tarea-vencimiento">Vence: ${alerta.tarea.fechaVencimiento}</div>
                                        </div>
                                    </c:if>
                                </div>

                                <!-- Acciones -->
                                <div class="alerta-actions">
                                    <label class="toggle-switch" title="${alerta.activa ? 'Desactivar' : 'Activar'} alerta">
                                        <input type="checkbox" 
                                               ${alerta.activa ? 'checked' : ''} 
                                               onchange="toggleAlerta(${alerta.id}, this.checked)">
                                        <span class="toggle-slider"></span>
                                    </label>
                                    
                                    <button class="btn-action btn-edit" 
                                            onclick="editarAlerta(${alerta.id})"
                                            title="Editar alerta">
                                        ✏️
                                    </button>
                                    
                                    <button class="btn-action btn-delete" 
                                            onclick="eliminarAlerta(${alerta.id})"
                                            title="Eliminar alerta">
                                        🗑️
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
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
    /* ========== Estadísticas Mejoradas ========== */
    .stats-summary {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
    }

    .stat-card {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 24px;
        border-radius: 16px;
        text-align: center;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .stat-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
    }

    .stat-total {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }

    .stat-active {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
    }

    .stat-urgent {
        background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);
    }

    .stat-icon {
        font-size: 2.5rem;
        margin-bottom: 8px;
    }

    .stat-number {
        font-size: 3rem;
        font-weight: 700;
        margin-bottom: 8px;
    }

    .stat-label {
        font-size: 0.95rem;
        opacity: 0.95;
        font-weight: 500;
    }

    /* ========== Grid de Alertas Moderno ========== */
    .alertas-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
        gap: 24px;
        margin-top: 24px;
    }

    .alerta-card {
        background: white;
        border-radius: 20px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
        overflow: hidden;
        position: relative;
        transition: all 0.3s ease;
        border: 2px solid transparent;
    }

    .alerta-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
    }

    .alerta-activa {
        border-color: #38ef7d;
    }

    .alerta-inactiva {
        opacity: 0.7;
        filter: grayscale(20%);
    }

    /* Estilos por tipo de alerta */
    .alerta-urgente:hover {
        border-color: #eb3349;
    }

    .alerta-recordatorio:hover {
        border-color: #667eea;
    }

    .alerta-informativa:hover {
        border-color: #6a1b9a;
    }

    /* ========== Ribbon de Estado ========== */
    .alerta-ribbon {
        position: absolute;
        top: 16px;
        right: -35px;
        padding: 6px 45px;
        font-size: 12px;
        font-weight: 700;
        color: white;
        transform: rotate(45deg);
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
        z-index: 10;
    }

    .ribbon-activa {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
    }

    .ribbon-inactiva {
        background: linear-gradient(135deg, #757575 0%, #9e9e9e 100%);
    }

    /* ========== Header de la Card ========== */
    .alerta-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 24px 16px 24px;
        background: linear-gradient(to right, #f8f9fa, #ffffff);
        border-bottom: 2px solid #f0f0f0;
    }

    .alerta-id-badge {
        font-size: 13px;
        font-weight: 700;
        color: #667eea;
        background: #f0f4ff;
        padding: 6px 14px;
        border-radius: 12px;
    }

    .alerta-tipo-badge {
        font-size: 14px;
        font-weight: 600;
        padding: 6px 14px;
        border-radius: 12px;
        background: #fff;
        border: 2px solid #e0e0e0;
    }

    /* ========== Contenido ========== */
    .alerta-content {
        padding: 24px;
    }

    .alerta-mensaje {
        font-size: 15px;
        line-height: 1.7;
        color: #2d3748;
        margin-bottom: 20px;
        min-height: 60px;
    }

    .alerta-fecha {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 12px 16px;
        background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        border-radius: 12px;
        margin-bottom: 16px;
    }

    .fecha-icon {
        font-size: 20px;
    }

    .fecha-text {
        font-size: 14px;
        font-weight: 600;
        color: #495057;
    }

    .alerta-tarea {
        padding: 16px;
        background: linear-gradient(135deg, #fff5f5 0%, #ffe5e5 100%);
        border-left: 4px solid #667eea;
        border-radius: 8px;
    }

    .tarea-label {
        font-size: 12px;
        color: #667eea;
        font-weight: 600;
        margin-bottom: 6px;
    }

    .tarea-titulo {
        font-size: 14px;
        font-weight: 700;
        color: #2d3748;
        margin-bottom: 4px;
    }

    .tarea-vencimiento {
        font-size: 13px;
        color: #718096;
    }

    /* ========== Acciones ========== */
    .alerta-actions {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        gap: 12px;
        padding: 16px 24px;
        background: #f8f9fa;
        border-top: 2px solid #e9ecef;
    }

    .btn-action {
        width: 40px;
        height: 40px;
        border: none;
        border-radius: 50%;
        font-size: 18px;
        cursor: pointer;
        transition: all 0.3s ease;
        background: white;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .btn-action:hover {
        transform: scale(1.1);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }

    .btn-edit:hover {
        background: #667eea;
    }

    .btn-delete:hover {
        background: #eb3349;
    }

    /* ========== Toggle Switch ========== */
    .toggle-switch {
        position: relative;
        display: inline-block;
        width: 54px;
        height: 28px;
        margin-right: auto;
    }

    .toggle-switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .toggle-slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        transition: 0.4s;
        border-radius: 28px;
    }

    .toggle-slider:before {
        position: absolute;
        content: "";
        height: 20px;
        width: 20px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        transition: 0.4s;
        border-radius: 50%;
    }

    .toggle-switch input:checked + .toggle-slider {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
    }

    .toggle-switch input:checked + .toggle-slider:before {
        transform: translateX(26px);
    }

    /* ========== Empty State ========== */
    .empty-state {
        text-align: center;
        padding: 4rem 2rem;
    }

    .empty-icon {
        font-size: 6rem;
        margin-bottom: 1.5rem;
        opacity: 0.4;
        animation: float 3s ease-in-out infinite;
    }

    @keyframes float {
        0%, 100% { transform: translateY(0); }
        50% { transform: translateY(-10px); }
    }

    .empty-state h3 {
        color: #2d3748;
        margin-bottom: 1rem;
        font-size: 1.5rem;
    }

    .empty-state p {
        color: #718096;
        margin-bottom: 2rem;
        font-size: 1.1rem;
    }

    /* ========== Responsive ========== */
    @media (max-width: 768px) {
        .alertas-grid {
            grid-template-columns: 1fr;
        }

        .stats-summary {
            grid-template-columns: 1fr;
        }
    }
</style>

<script>
    // Toggle activar/desactivar alerta
    function toggleAlerta(alertaId, activa) {
        const formData = new FormData();
        formData.append('alertaId', alertaId);
        formData.append('activa', activa);

        fetch('${pageContext.request.contextPath}/alertas?action=toggle', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showToast(activa ? '✓ Alerta activada' : '○ Alerta desactivada', 'success');
                setTimeout(() => location.reload(), 1000);
            } else {
                showToast('❌ Error al cambiar estado', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showToast('❌ Error de conexión', 'error');
        });
    }

    // Editar alerta
    function editarAlerta(alertaId) {
        window.location.href = '${pageContext.request.contextPath}/alertas?action=editar&id=' + alertaId;
    }

    // Eliminar alerta
    function eliminarAlerta(alertaId) {
        if (confirm('¿Estás seguro de eliminar esta alerta?')) {
            fetch('${pageContext.request.contextPath}/alertas?action=eliminar&id=' + alertaId, {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showToast('✓ Alerta eliminada', 'success');
                    setTimeout(() => location.reload(), 1000);
                } else {
                    showToast('❌ Error al eliminar', 'error');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showToast('❌ Error de conexión', 'error');
            });
        }
    }

    // Sistema de Toast (notificación flotante)
    function showToast(mensaje, tipo) {
        const toast = document.createElement('div');
        toast.className = 'toast toast-' + tipo;
        toast.textContent = mensaje;
        
        const backgroundColor = (tipo === 'success') ? '#38ef7d' : '#eb3349';
        
        toast.style.cssText = 
            'position: fixed;' +
            'top: 20px;' +
            'right: 20px;' +
            'padding: 16px 24px;' +
            'background: ' + backgroundColor + ';' +
            'color: white;' +
            'border-radius: 12px;' +
            'box-shadow: 0 4px 12px rgba(0,0,0,0.2);' +
            'z-index: 10000;' +
            'font-weight: 600;' +
            'animation: slideIn 0.3s ease;';
        
        document.body.appendChild(toast);

        setTimeout(function() {
            toast.style.animation = 'slideOut 0.3s ease';
            setTimeout(function() { toast.remove(); }, 300);
        }, 3000);
    }
</script>

<style>
    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
</style>
</body>
</html>
