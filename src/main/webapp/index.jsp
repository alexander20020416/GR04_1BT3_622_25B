<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestor de Tareas Universitarias</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <div class="header-content">
            <div class="title-section">
                <h1>📚 Gestor de Tareas Universitarias</h1>
                <p class="subtitle">Sistema de gestión académica - Grupo 4</p>
            </div>

            <!-- Información del usuario autenticado -->
            <c:if test="${not empty sessionScope.usuarioNombre}">
                <div class="user-info">
                    <!-- Campana de Notificaciones -->
                    <div class="notification-bell-container">
                        <div class="notification-bell" id="notificationBell">
                            <span class="bell-icon">🔔</span>
                            <span class="notification-badge" id="notificationBadge" style="display: none;">0</span>
                        </div>
                    </div>

                    <span class="welcome-text">
                        👋 Bienvenido, <strong>${sessionScope.usuarioNombre}</strong>
                    </span>
                    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                        🚪 Cerrar Sesión
                    </a>
                </div>
            </c:if>
        </div>
    </header>

    <main>
        <div class="welcome-section">
            <h2>¡Bienvenido!</h2>
            <p>Organiza tus tareas académicas de manera eficiente</p>

            <c:if test="${not empty sessionScope.usuarioNombre}">
                <p class="user-welcome">
                    Hola <strong>${sessionScope.usuarioNombre}</strong>, selecciona una opción del menú para comenzar.
                </p>
            </c:if>
        </div>

        <div class="menu-grid">
            <div class="menu-section">
                <div class="menu-card">
                    <div class="card-icon">➕</div>
                    <h4>Planificar Tareas</h4>
                    <p>Registra nuevas tareas académicas con título, descripción y fecha de vencimiento.</p>
                    <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">
                        Ir a Planificar
                    </a>
                </div>

                <div class="menu-card">
                    <div class="card-icon">📊</div>
                    <h4>Organizar Tareas</h4>
                    <p>Visualiza, organiza, edita y elimina tus tareas usando diferentes criterios de ordenamiento.</p>
                    <a href="${pageContext.request.contextPath}/organizar" class="btn btn-primary">
                        Ir a Organizar
                    </a>
                </div>
            </div>

            <div class="menu-section">
                <div class="menu-card">
                    <div class="card-icon">🔍</div>
                    <h4>Consultar Tareas</h4>
                    <p>Consulta el listado completo de tareas pendientes con opciones de filtrado.</p>
                    <a href="${pageContext.request.contextPath}/consultar" class="btn btn-secondary">
                        Ir a Consultar
                    </a>
                </div>

                <div class="menu-card">
                    <div class="card-icon">⏰</div>
                    <h4>Configurar Alertas</h4>
                    <p>Visualiza y gestiona tus alertas. Crea alertas personalizadas para recordarte de tus tareas
                        importantes.</p>
                    <a href="${pageContext.request.contextPath}/alertas" class="btn btn-secondary">
                        Ver Mis Alertas
                    </a>
                </div>

                <div class="menu-card">
                    <div class="card-icon">📅</div>
                    <h4>Calendario de Tareas</h4>
                    <p>Visualiza tus tareas en un calendario semanal, detecta conflictos y administra tu tiempo.</p>
                    <a href="${pageContext.request.contextPath}/calendario" class="btn btn-secondary">
                        Ver Calendario
                    </a>
                </div>

                <div class="menu-card">
                    <div class="card-icon">📊</div>
                    <h4>Seguimiento de Proyectos</h4>
                    <p>Visualiza el progreso de tus proyectos con barras de avance y alertas visuales de urgencia.</p>
                    <a href="${pageContext.request.contextPath}/seguimiento" class="btn btn-secondary">
                        Ver Seguimiento
                    </a>
                </div>
            </div>

        </div>

    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Metodologías Ágiles - EPN</p>
        <p>Proyecto: Gestor de Tareas Universitarias</p>
    </footer>
</div>

<style>
    .header-content {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
        gap: 1rem;
    }

    .title-section {
        flex: 1;
    }

    .user-info {
        display: flex;
        align-items: center;
        gap: 1rem;
        background-color: #f8f9fa;
        padding: 0.75rem 1rem;
        border-radius: 8px;
        border: 1px solid #e9ecef;
    }

    .welcome-text {
        color: #495057;
        font-size: 0.9rem;
    }

    .logout-btn {
        background-color: #dc3545;
        color: white;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        text-decoration: none;
        font-size: 0.85rem;
        transition: background-color 0.2s;
    }

    .logout-btn:hover {
        background-color: #c82333;
        text-decoration: none;
        color: white;
    }

    .user-welcome {
        background-color: #e8f5e8;
        padding: 1rem;
        border-radius: 8px;
        border-left: 4px solid #28a745;
        margin-top: 1rem;
        color: #155724;
    }

    /* ========== Campana de Notificaciones ========== */
    .notification-bell-container {
        position: relative;
        display: inline-block;
    }

    .notification-bell {
        position: relative;
        cursor: pointer;
        padding: 10px 15px;
        background: rgba(102, 126, 234, 0.15);
        border-radius: 12px;
        transition: all 0.3s ease;
        display: inline-flex;
        align-items: center;
        justify-content: center;
    }

    .notification-bell:hover {
        background: rgba(102, 126, 234, 0.25);
        transform: scale(1.05);
    }

    .notification-bell.has-notifications {
        animation: bellRing 2s ease-in-out infinite;
    }

    @keyframes bellRing {
        0%, 100% {
            transform: rotate(0deg);
        }
        10%, 30% {
            transform: rotate(-10deg);
        }
        20%, 40% {
            transform: rotate(10deg);
        }
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
        0%, 100% {
            transform: scale(1);
        }
        50% {
            transform: scale(1.1);
        }
    }

    @media (max-width: 768px) {
        .header-content {
            flex-direction: column;
            text-align: center;
        }

        .user-info {
            width: 100%;
            justify-content: center;
        }
    }
</style>

<script>
    // Actualizar badge de notificaciones
    function actualizarBadgeNotificaciones() {
        fetch('${pageContext.request.contextPath}/alertas?action=count', {
            method: 'GET'
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
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
            .catch(function (error) {
                console.error('Error al actualizar badge:', error);
            });
    }

    // Ir a alertas al hacer clic
    document.addEventListener('DOMContentLoaded', function () {
        const bell = document.getElementById('notificationBell');
        if (bell) {
            bell.addEventListener('click', function () {
                window.location.href = '${pageContext.request.contextPath}/alertas';
            });
        }

        // Actualizar badge al cargar
        actualizarBadgeNotificaciones();

        // Actualizar cada 30 segundos
        setInterval(actualizarBadgeNotificaciones, 30000);
    });
</script>
</body>
</html>