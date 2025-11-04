<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calendario de Tareas - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .calendario-grid {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            gap: 10px;
            margin: 20px 0;
        }

        .dia-calendario {
            border: 1px solid #ddd;
            padding: 10px;
            min-height: 100px;
            background: #fff;
            border-radius: 5px;
        }

        .dia-calendario.hoy {
            border: 2px solid #4CAF50;
        }

        .dia-calendario.tiene-conflicto {
            border: 2px solid #f44336;
        }

        .dia-calendario h3 {
            margin: 0 0 10px 0;
            font-size: 1rem;
        }

        .tarea-item {
            margin-bottom: 5px;
            padding: 5px;
            border-radius: 3px;
            font-size: 0.9rem;
        }

        .tarea-item.alta {
            background-color: #ffebee;
        }

        .resumen-dia {
            margin-bottom: 20px;
            padding: 20px;
            background: #f5f5f5;
            border-radius: 5px;
        }

        .selector-fecha {
            margin: 20px 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .selector-fecha input[type="date"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .selector-fecha button {
            padding: 8px 16px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .selector-fecha button:hover {
            background: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📅 Calendario de Tareas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/home" class="btn-link">← Volver al inicio</a>
        </nav>
    </header>

    <main>
        <!-- Mensajes de error -->
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Selector de fecha -->
        <div class="selector-fecha">
            <form action="${pageContext.request.contextPath}/calendario" method="GET">
                <label for="fecha">Seleccionar fecha:</label>
                <input type="date" id="fecha" name="fecha" 
                       value="${fechaSeleccionada}" 
                       onchange="this.form.submit()">
            </form>
        </div>

        <!-- Resumen del día -->
        <div class="resumen-dia">
            <h2>Resumen del día ${fechaSeleccionada}</h2>
            <c:if test="${hayConflictos}">
                <div class="alert alert-warning">
                    ⚠️ Hay conflictos de tareas de alta prioridad en este día
                </div>
            </c:if>
            <p>Tareas pendientes hoy: <strong>${tareasPendientes}</strong></p>
            <p>Total de tareas hoy: <strong>${tareasDia.size()}</strong></p>
        </div>

        <!-- Calendario semanal -->
        <h2>Vista Semanal (${inicioSemana} - ${finSemana})</h2>
        <div class="calendario-grid">
            <c:forEach var="fecha" begin="0" end="6">
                <c:set var="diaActual" value="${inicioSemana.plusDays(fecha)}"/>
                <div class="dia-calendario ${diaActual.equals(fechaSeleccionada) ? 'hoy' : ''}">
                    <h3>${diaActual}</h3>
                    <c:forEach var="tarea" items="${tareasAgrupadas[diaActual]}">
                        <div class="tarea-item ${tarea.prioridad == 'ALTA' ? 'alta' : ''}">
                            <strong>${tarea.titulo}</strong>
                            <br>
                            <small>${tarea.estado}</small>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>

        <!-- Lista de tareas del día -->
        <h2>Tareas para hoy</h2>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Estado</th>
                        <th>Prioridad</th>
                        <th>Descripción</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tarea" items="${tareasDia}">
                        <tr>
                            <td><strong>${tarea.titulo}</strong></td>
                            <td>
                                <span class="badge badge-estado-${tarea.estado.toLowerCase().replace(' ', '-')}">
                                    ${tarea.estado}
                                </span>
                            </td>
                            <td>
                                <span class="badge badge-${tarea.prioridad.toLowerCase()}">
                                    ${tarea.prioridad}
                                </span>
                            </td>
                            <td>${tarea.descripcion}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>