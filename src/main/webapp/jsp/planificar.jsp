<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Planificar Tareas - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>➕ Planificar Tareas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/detalleMateria" class="btn-link">← Regresar</a>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>Registrar Nueva Tarea</h2>
            <p class="form-description">
                Complete los siguientes campos para registrar una nueva tarea académica.
            </p>

            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/planificar" method="post" class="form">
                <div class="form-group">
                    <label for="titulo">Título de la Tarea *</label>
                    <input type="text"
                           id="titulo"
                           name="titulo"
                           class="form-control"
                           placeholder="Ej: Completar informe de proyecto"
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
                        <label for="fechaLimite">Fecha Límite *</label>
                        <input type="date"
                               id="fechaLimite"
                               name="fechaLimite"
                               class="form-control"
                               required
                               min="<%= java.time.LocalDate.now() %>">
                    </div>

                    <div class="form-group">
                        <label for="estado">Estado</label>
                        <select id="estado" name="estado" class="form-control">
                            <option value="Pendiente" selected>Pendiente</option>
                            <option value="En Progreso">En Progreso</option>
                            <option value="Completada">Completada</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="prioridad">Prioridad</label>
                    <select id="prioridad" name="prioridad" class="form-control">
                        <option value="Media" selected>Media</option>
                        <option value="Alta">Alta</option>
                        <option value="Baja">Baja</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        ✓ Guardar Tarea
                    </button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                        Cancelar
                    </a>
                </div>
            </form>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información</h3>
            <p>Este formulario implementa el caso de uso <strong>"Planificar Tareas"</strong>.</p>
            <ul>
                <li>Todos los campos marcados con (*) son obligatorios</li>
                <li>Puedes definir el estado inicial de la tarea</li>
                <li>Se aplica el patrón <strong>Repository</strong> para la persistencia</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>