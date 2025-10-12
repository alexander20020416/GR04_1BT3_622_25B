<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Tarea - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>➕ Agregar Tarea a Actividad</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/listar" class="btn-link">← Volver a Actividades</a>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>Agregar Tarea</h2>
            <p class="form-description">
                Actividad: <strong>${actividad.titulo}</strong><br>
                Completa los siguientes campos para agregar una nueva tarea a esta actividad.
            </p>

            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/agregar-tarea" method="post" class="form">
                <input type="hidden" name="actividadId" value="${actividad.id}">
                
                <div class="form-group">
                    <label for="titulo">Título de la Tarea *</label>
                    <input type="text"
                           id="titulo"
                           name="titulo"
                           class="form-control"
                           placeholder="Ej: Investigar bibliografía"
                           required
                           maxlength="200">
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción *</label>
                    <textarea id="descripcion"
                              name="descripcion"
                              class="form-control"
                              rows="4"
                              placeholder="Describe los detalles de la tarea..."
                              required></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="fechaVencimiento">Fecha de Vencimiento *</label>
                        <input type="date"
                               id="fechaVencimiento"
                               name="fechaVencimiento"
                               class="form-control"
                               required
                               max="${actividad.fechaEntrega}"
                               min="<%= java.time.LocalDate.now() %>">
                        <small>Debe ser antes de la fecha de entrega de la actividad</small>
                    </div>

                    <div class="form-group">
                        <label for="prioridad">Prioridad</label>
                        <select id="prioridad" name="prioridad" class="form-control">
                            <option value="Media" selected>Media</option>
                            <option value="Alta">Alta</option>
                            <option value="Baja">Baja</option>
                        </select>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        ✓ Agregar Tarea
                    </button>
                    <a href="${pageContext.request.contextPath}/listar" class="btn btn-secondary">
                        Cancelar
                    </a>
                </div>
            </form>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información</h3>
            <p>Las tareas son sub-elementos de una actividad.</p>
            <ul>
                <li>Una actividad puede tener múltiples tareas</li>
                <li>Cada tarea tiene su propia fecha de vencimiento</li>
                <li>La arquitectura mantiene la relación 1:N entre Actividad y Tarea</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>
