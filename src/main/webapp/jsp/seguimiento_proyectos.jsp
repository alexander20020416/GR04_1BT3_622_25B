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
        .proyectos-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .proyecto-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .proyecto-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        .proyecto-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .proyecto-titulo {
            font-size: 1.5rem;
            font-weight: bold;
            color: #2c3e50;
            margin: 0;
        }

        /* CA3: Indicadores de urgencia */
        .urgencia-badge {
            padding: 6px 15px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: bold;
            text-transform: uppercase;
        }

        .urgencia-VENCIDO {
            background: #dc3545;
            color: white;
        }

        .urgencia-URGENTE {
            background: #dc3545;
            color: white;
            animation: pulse 1.5s infinite;
        }

        .urgencia-PROXIMO {
            background: #ff9800;
            color: white;
        }

        .urgencia-A_TIEMPO {
            background: #28a745;
            color: white;
        }

        .urgencia-SIN_FECHA {
            background: #6c757d;
            color: white;
        }

        @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.7; }
        }

        .proyecto-info {
            display: flex;
            gap: 20px;
            margin-bottom: 15px;
            flex-wrap: wrap;
        }

        .info-item {
            display: flex;
            align-items: center;
            gap: 8px;
            color: #555;
            font-size: 0.95rem;
        }

        /* CA1: Barra de progreso visual */
        .progreso-container {
            margin: 20px 0;
        }

        .progreso-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
        }

        .progreso-label {
            font-weight: 600;
            color: #2c3e50;
        }

        .progreso-porcentaje {
            font-weight: bold;
            font-size: 1.1rem;
            color: #667eea;
        }

        .progreso-barra {
            width: 100%;
            height: 25px;
            background: #e9ecef;
            border-radius: 12px;
            overflow: hidden;
            position: relative;
        }

        .progreso-fill {
            height: 100%;
            background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
            transition: width 0.5s ease;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            padding-right: 10px;
            color: white;
            font-weight: bold;
            font-size: 0.85rem;
        }

        .progreso-fill.completo {
            background: linear-gradient(90deg, #11998e 0%, #38ef7d 100%);
        }

        /* CA2: Sección de tareas */
        .tareas-section {
            margin-top: 20px;
            border-top: 2px solid #e9ecef;
            padding-top: 15px;
        }

        .ver-detalles-btn {
            background: #667eea;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            transition: background 0.2s;
            width: 100%;
            text-align: center;
            margin-top: 15px;
        }

        .ver-detalles-btn:hover {
            background: #5568d3;
        }

        .tareas-lista {
            display: none;
            margin-top: 15px;
        }

        .tareas-lista.visible {
            display: block;
        }

        .tarea-item {
            background: #f8f9fa;
            padding: 12px 15px;
            margin: 8px 0;
            border-radius: 8px;
            border-left: 4px solid #667eea;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .tarea-info {
            flex: 1;
        }

        .tarea-titulo {
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 4px;
        }

        .tarea-fecha {
            font-size: 0.85rem;
            color: #6c757d;
        }

        .tarea-estado {
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 0.8rem;
            font-weight: 600;
        }

        .estado-Pendiente {
            background: #ffc107;
            color: #000;
        }

        .estado-En_Progreso {
            background: #17a2b8;
            color: white;
        }

        .estado-Completada {
            background: #28a745;
            color: white;
        }

        /* CA2: Mensaje cuando no hay tareas */
        .sin-tareas {
            text-align: center;
            padding: 30px;
            color: #6c757d;
            font-style: italic;
        }

        .sin-proyectos {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .sin-proyectos-icon {
            font-size: 4rem;
            margin-bottom: 20px;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .btn-crear {
            background: #28a745;
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: background 0.2s;
        }

        .btn-crear:hover {
            background: #218838;
        }

        .materia-badge {
            background: #e3f2fd;
            color: #1976d2;
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 0.85rem;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📊 Seguimiento de Proyectos</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/proyectos" class="btn-link">➕ Crear Proyecto</a>
            <a href="${pageContext.request.contextPath}/" class="btn-link">🏠 Inicio</a>
        </nav>
    </header>

    <main class="proyectos-container">
        <c:choose>
            <c:when test="${empty proyectos}">
                <!-- CA2: Escenario 2 - No hay proyectos -->
                <div class="sin-proyectos">
                    <div class="sin-proyectos-icon">📋</div>
                    <h2>No tienes proyectos creados</h2>
                    <p>Crea tu primer proyecto para comenzar a dar seguimiento a tus trabajos importantes.</p>
                    <div class="action-buttons" style="justify-content: center;">
                        <a href="${pageContext.request.contextPath}/proyectos" class="btn-crear">
                            ➕ Crear Primer Proyecto
                        </a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Listar proyectos -->
                <c:forEach var="proyecto" items="${proyectos}">
                    <div class="proyecto-card">
                        <!-- CA1: Nombre del proyecto y CA3: Indicador de urgencia -->
                        <div class="proyecto-header">
                            <h2 class="proyecto-titulo">${proyecto.titulo}</h2>
                            <span class="urgencia-badge urgencia-${proyecto.determinarEstadoUrgencia()}">
                                <c:choose>
                                    <c:when test="${proyecto.determinarEstadoUrgencia() == 'VENCIDO'}">🔴 Vencido</c:when>
                                    <c:when test="${proyecto.determinarEstadoUrgencia() == 'URGENTE'}">🔴 Urgente</c:when>
                                    <c:when test="${proyecto.determinarEstadoUrgencia() == 'PROXIMO'}">🟠 Próximo</c:when>
                                    <c:when test="${proyecto.determinarEstadoUrgencia() == 'A_TIEMPO'}">🟢 A tiempo</c:when>
                                    <c:otherwise>⚪ Sin fecha</c:otherwise>
                                </c:choose>
                            </span>
                        </div>

                        <!-- Información del proyecto -->
                        <div class="proyecto-info">
                            <c:if test="${not empty proyecto.descripcion}">
                                <div class="info-item">
                                    <span>📝</span>
                                    <span>${proyecto.descripcion}</span>
                                </div>
                            </c:if>
                            <c:if test="${not empty proyecto.fechaVencimiento}">
                                <div class="info-item">
                                    <span>📅</span>
                                    <span>Vence: ${proyecto.fechaVencimiento}</span>
                                </div>
                            </c:if>
                            <c:if test="${not empty proyecto.materia}">
                                <div class="info-item">
                                    <span class="materia-badge">
                                        📚 ${proyecto.materia.nombre}
                                    </span>
                                </div>
                            </c:if>
                        </div>

                        <!-- CA1: Barra de progreso visual con porcentaje -->
                        <div class="progreso-container">
                            <div class="progreso-header">
                                <span class="progreso-label">Progreso del Proyecto</span>
                                <span class="progreso-porcentaje">${proyecto.calcularProgreso()}%</span>
                            </div>
                            <div class="progreso-barra">
                                <div class="progreso-fill ${proyecto.calcularProgreso() == 100 ? 'completo' : ''}" 
                                     style="width: ${proyecto.calcularProgreso()}%">
                                    <c:if test="${proyecto.calcularProgreso() > 10}">
                                        ${proyecto.calcularProgreso()}%
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <!-- CA2: Botón para ver detalles -->
                        <button class="ver-detalles-btn" onclick="toggleTareas('proyecto-${proyecto.id}')">
                            👁️ Ver Detalles de Tareas
                        </button>

                        <!-- CA2: Lista de tareas con estados -->
                        <div id="proyecto-${proyecto.id}" class="tareas-lista">
                            <div class="tareas-section">
                                <h3>Tareas del Proyecto (${proyecto.tareas.size()})</h3>
                                <c:choose>
                                    <c:when test="${empty proyecto.tareas}">
                                        <!-- CA2: Escenario 2 - Sin tareas asignadas -->
                                        <div class="sin-tareas">
                                            📋 Este proyecto no tiene tareas asignadas
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- CA2: Escenario 1 - Mostrar tareas con estados -->
                                        <c:forEach var="tarea" items="${proyecto.tareas}">
                                            <div class="tarea-item">
                                                <div class="tarea-info">
                                                    <div class="tarea-titulo">${tarea.titulo}</div>
                                                    <div class="tarea-fecha">
                                                        📅 ${tarea.fechaVencimiento} | 
                                                        Prioridad: ${tarea.prioridad}
                                                    </div>
                                                </div>
                                                <span class="tarea-estado estado-${tarea.estado}">
                                                    ${tarea.estado}
                                                </span>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>

<script>
    function toggleTareas(proyectoId) {
        const tareasDiv = document.getElementById(proyectoId);
        const btn = event.target;
        
        if (tareasDiv.classList.contains('visible')) {
            tareasDiv.classList.remove('visible');
            btn.textContent = '👁️ Ver Detalles de Tareas';
        } else {
            tareasDiv.classList.add('visible');
            btn.textContent = '🔼 Ocultar Detalles';
        }
    }
</script>
</body>
</html>
