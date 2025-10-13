<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h1>📚 Gestor de Tareas Universitarias</h1>
        <p class="subtitle">Sistema de gestión académica - Grupo 4</p>
    </header>

    <main>
        <div class="welcome-section">
            <h2>¡Bienvenido!</h2>
            <p>Organiza tus actividades académicas de manera eficiente</p>
        </div>

        <div class="menu-grid">
            <!-- Incremento 1 -->
            <div class="menu-section">
                <h3>📝 Incremento 1</h3>

                <div class="menu-card">
                    <div class="card-icon">➕</div>
                    <h4>Planificar Actividades</h4>
                    <p>Registra nuevas actividades académicas con título, descripción y fecha de entrega.</p>
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
                    <p>Visualiza y gestiona tus alertas. Crea alertas personalizadas para recordarte de tus actividades importantes.</p>
                    <a href="${pageContext.request.contextPath}/alertas" class="btn btn-secondary">
                        Ver Mis Alertas
                    </a>
                </div>
            </div>
        </div>

        <div class="info-section">
            <h3>ℹ️ Patrones de Diseño Implementados</h3>
            <div class="patterns-list">
                <div class="pattern-item">
                    <strong>Repository:</strong> Separación de lógica de negocio y persistencia
                </div>
                <div class="pattern-item">
                    <strong>Strategy:</strong> Diferentes estrategias de ordenamiento de tareas
                </div>
                <div class="pattern-item">
                    <strong>Observer:</strong> Notificación de eventos de alertas
                </div>
            </div>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Metodologías Ágiles - EPN</p>
        <p>Proyecto: Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>