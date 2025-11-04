<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Campana de Notificaciones -->
<div class="notification-bell-container">
    <div class="notification-bell" id="notificationBell" onclick="toggleNotificationDropdown()">
        <span class="bell-icon">🔔</span>
        <span class="notification-badge" id="notificationBadge" style="display: none;">0</span>
    </div>
    
    <!-- Dropdown de Notificaciones -->
    <div class="notification-dropdown" id="notificationDropdown" style="display: none;">
        <div class="notification-header">
            <h3>🔔 Alertas Activas</h3>
            <button class="close-dropdown" onclick="closeNotificationDropdown()">✕</button>
        </div>
        
        <div class="notification-list" id="notificationList">
            <div class="notification-loading">
                <div class="loading-spinner"></div>
                <p>Cargando alertas...</p>
            </div>
        </div>
        
        <div class="notification-footer">
            <a href="${pageContext.request.contextPath}/alertas" class="btn-ver-todas">
                Ver todas las alertas →
            </a>
        </div>
    </div>
</div>

<style>
    /* ========== Campana de Notificaciones ========== */
    .notification-bell-container {
        position: relative;
        display: inline-block;
    }

    .notification-bell {
        position: relative;
        cursor: pointer;
        padding: 10px 15px;
        background: rgba(255, 255, 255, 0.15);
        border-radius: 12px;
        transition: all 0.3s ease;
        display: inline-flex;
        align-items: center;
        justify-content: center;
    }

    .notification-bell:hover {
        background: rgba(255, 255, 255, 0.25);
        transform: scale(1.05);
    }

    .notification-bell.has-notifications {
        animation: bellRing 2s ease-in-out infinite;
    }

    @keyframes bellRing {
        0%, 100% { transform: rotate(0deg); }
        10%, 30% { transform: rotate(-10deg); }
        20%, 40% { transform: rotate(10deg); }
    }

    .bell-icon {
        font-size: 24px;
        display: block;
    }

    .notification-badge {
        position: absolute;
        top: 2px;
        right: 2px;
        background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);
        color: white;
        font-size: 11px;
        font-weight: 700;
        min-width: 20px;
        height: 20px;
        border-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 0 5px;
        box-shadow: 0 2px 8px rgba(235, 51, 73, 0.4);
        animation: pulseBadge 2s ease-in-out infinite;
    }

    @keyframes pulseBadge {
        0%, 100% { transform: scale(1); }
        50% { transform: scale(1.1); }
    }

    /* ========== Dropdown de Notificaciones ========== */
    .notification-dropdown {
        position: absolute;
        top: calc(100% + 10px);
        right: 0;
        width: 380px;
        max-height: 500px;
        background: white;
        border-radius: 16px;
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
        z-index: 10000;
        overflow: hidden;
        animation: dropdownSlideIn 0.3s ease;
    }

    @keyframes dropdownSlideIn {
        from {
            opacity: 0;
            transform: translateY(-10px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .notification-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 16px 20px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
    }

    .notification-header h3 {
        margin: 0;
        font-size: 1.1rem;
        font-weight: 600;
    }

    .close-dropdown {
        background: rgba(255, 255, 255, 0.2);
        border: none;
        color: white;
        width: 28px;
        height: 28px;
        border-radius: 50%;
        cursor: pointer;
        font-size: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        transition: all 0.3s ease;
    }

    .close-dropdown:hover {
        background: rgba(255, 255, 255, 0.3);
        transform: rotate(90deg);
    }

    .notification-list {
        max-height: 350px;
        overflow-y: auto;
        padding: 8px 0;
    }

    .notification-item {
        padding: 16px 20px;
        border-bottom: 1px solid #f0f0f0;
        cursor: pointer;
        transition: all 0.3s ease;
        position: relative;
    }

    .notification-item:hover {
        background: #f8f9fa;
    }

    .notification-item.urgente {
        border-left: 4px solid #eb3349;
        background: #fff5f5;
    }

    .notification-item.recordatorio {
        border-left: 4px solid #667eea;
        background: #f7f9ff;
    }

    .notification-item.informativa {
        border-left: 4px solid #6a1b9a;
        background: #faf7fc;
    }

    .notification-tipo {
        font-size: 12px;
        font-weight: 600;
        margin-bottom: 6px;
    }

    .notification-tipo.urgente {
        color: #eb3349;
    }

    .notification-tipo.recordatorio {
        color: #667eea;
    }

    .notification-tipo.informativa {
        color: #6a1b9a;
    }

    .notification-mensaje {
        font-size: 14px;
        color: #2d3748;
        margin-bottom: 8px;
        line-height: 1.5;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    .notification-tiempo {
        font-size: 12px;
        color: #718096;
        display: flex;
        align-items: center;
        gap: 6px;
    }

    .tiempo-restante {
        font-weight: 600;
    }

    .tiempo-restante.urgente-tiempo {
        color: #eb3349;
    }

    .notification-loading {
        padding: 40px 20px;
        text-align: center;
        color: #718096;
    }

    .loading-spinner {
        width: 40px;
        height: 40px;
        margin: 0 auto 16px;
        border: 4px solid #f3f3f3;
        border-top: 4px solid #667eea;
        border-radius: 50%;
        animation: spin 1s linear infinite;
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

    .notification-empty {
        padding: 40px 20px;
        text-align: center;
        color: #718096;
    }

    .notification-empty .empty-icon {
        font-size: 48px;
        margin-bottom: 12px;
        opacity: 0.5;
    }

    .notification-footer {
        padding: 12px 20px;
        background: #f8f9fa;
        border-top: 1px solid #e9ecef;
        text-align: center;
    }

    .btn-ver-todas {
        display: inline-block;
        color: #667eea;
        text-decoration: none;
        font-weight: 600;
        font-size: 14px;
        transition: all 0.3s ease;
    }

    .btn-ver-todas:hover {
        color: #764ba2;
        transform: translateX(5px);
    }

    /* Scrollbar personalizado */
    .notification-list::-webkit-scrollbar {
        width: 6px;
    }

    .notification-list::-webkit-scrollbar-track {
        background: #f1f1f1;
    }

    .notification-list::-webkit-scrollbar-thumb {
        background: #cbd5e0;
        border-radius: 3px;
    }

    .notification-list::-webkit-scrollbar-thumb:hover {
        background: #a0aec0;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .notification-dropdown {
            width: 320px;
            right: -50px;
        }
    }
</style>

<script>
    let notificationDropdownOpen = false;

    // Toggle del dropdown
    function toggleNotificationDropdown() {
        const dropdown = document.getElementById('notificationDropdown');
        if (notificationDropdownOpen) {
            closeNotificationDropdown();
        } else {
            dropdown.style.display = 'block';
            notificationDropdownOpen = true;
            cargarAlertasActivas();
        }
    }

    // Cerrar dropdown
    function closeNotificationDropdown() {
        const dropdown = document.getElementById('notificationDropdown');
        dropdown.style.display = 'none';
        notificationDropdownOpen = false;
    }

    // Cargar alertas activas
    function cargarAlertasActivas() {
        const listContainer = document.getElementById('notificationList');
        
        fetch('${pageContext.request.contextPath}/alertas?action=activas', {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.alertas && data.alertas.length > 0) {
                mostrarAlertas(data.alertas);
            } else {
                mostrarAlertasVacias();
            }
        })
        .catch(error => {
            console.error('Error al cargar alertas:', error);
            mostrarAlertasVacias();
        });
    }

    // Mostrar alertas en el dropdown
    function mostrarAlertas(alertas) {
        const listContainer = document.getElementById('notificationList');
        let html = '';
        
        alertas.forEach(alerta => {
            const tipoClass = alerta.tipo.toLowerCase();
            const tiempoRestante = calcularTiempoRestante(alerta.fechaAlerta);
            
            html += `
                <div class="notification-item ${tipoClass}" onclick="irAAlerta(${alerta.id})">
                    <div class="notification-tipo ${tipoClass}">
                        ${alerta.tipo === 'Urgente' ? '🚨' : alerta.tipo === 'Informativa' ? 'ℹ️' : '🔔'} ${alerta.tipo}
                    </div>
                    <div class="notification-mensaje">${alerta.mensaje}</div>
                    <div class="notification-tiempo">
                        <span>⏰</span>
                        <span class="tiempo-restante ${tiempoRestante.urgente ? 'urgente-tiempo' : ''}">
                            ${tiempoRestante.texto}
                        </span>
                    </div>
                </div>
            `;
        });
        
        listContainer.innerHTML = html;
    }

    // Mostrar estado vacío
    function mostrarAlertasVacias() {
        const listContainer = document.getElementById('notificationList');
        listContainer.innerHTML = `
            <div class="notification-empty">
                <div class="empty-icon">✅</div>
                <p>No tienes alertas activas</p>
            </div>
        `;
    }

    // Calcular tiempo restante
    function calcularTiempoRestante(fechaAlerta) {
        const ahora = new Date();
        const fecha = new Date(fechaAlerta);
        const diff = fecha - ahora;
        
        if (diff < 0) {
            return { texto: '¡Ya venció!', urgente: true };
        }
        
        const minutos = Math.floor(diff / 1000 / 60);
        const horas = Math.floor(minutos / 60);
        const dias = Math.floor(horas / 24);
        
        if (dias > 0) {
            return { texto: `En ${dias} día${dias > 1 ? 's' : ''}`, urgente: false };
        } else if (horas > 0) {
            return { texto: `En ${horas} hora${horas > 1 ? 's' : ''}`, urgente: horas < 2 };
        } else {
            return { texto: `En ${minutos} minuto${minutos > 1 ? 's' : ''}`, urgente: true };
        }
    }

    // Ir a una alerta específica
    function irAAlerta(alertaId) {
        window.location.href = '${pageContext.request.contextPath}/alertas';
    }

    // Actualizar badge de notificaciones
    function actualizarBadgeNotificaciones() {
        fetch('${pageContext.request.contextPath}/alertas?action=count', {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            const badge = document.getElementById('notificationBadge');
            const bell = document.getElementById('notificationBell');
            
            if (data.count > 0) {
                badge.textContent = data.count;
                badge.style.display = 'flex';
                bell.classList.add('has-notifications');
            } else {
                badge.style.display = 'none';
                bell.classList.remove('has-notifications');
            }
        })
        .catch(error => {
            console.error('Error al actualizar badge:', error);
        });
    }

    // Cerrar dropdown al hacer clic fuera
    document.addEventListener('click', function(event) {
        const bellContainer = document.querySelector('.notification-bell-container');
        if (bellContainer && !bellContainer.contains(event.target) && notificationDropdownOpen) {
            closeNotificationDropdown();
        }
    });

    // Actualizar badge cada 30 segundos
    setInterval(actualizarBadgeNotificaciones, 30000);
    
    // Actualizar badge al cargar la página
    document.addEventListener('DOMContentLoaded', actualizarBadgeNotificaciones);
</script>
