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
            <!-- Incremento 1 -->
            <div class="menu-section">
                <h3>📝 Incremento 1</h3>

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
                    <p>Visualiza y organiza tus tareas usando diferentes criterios de ordenamiento.</p>
                    <a href="${pageContext.request.contextPath}/organizar" class="btn btn-primary">
                        Ir a Organizar
                    </a>
                </div>
            </div>

            <!-- Incremento 2 -->
            <div class="menu-section">
                <h3>🔔 Incremento 2</h3>

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
                    <p>Visualiza y gestiona tus alertas. Crea alertas personalizadas para recordarte de tus tareas importantes.</p>
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
</body>
</html>