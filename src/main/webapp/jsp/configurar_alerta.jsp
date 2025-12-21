<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configurar Alertas - Gestor de Tareas</title>
    <%@ include file="includes/styles.jsp" %>
</head>
<body>
<div class="container">
    <header>
        <h1>⏰ Configurar Alertas</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/alertas" class="btn-link">← Volver a Mis Alertas</a>
            <a href="${pageContext.request.contextPath}/" class="btn-link">🏠 Inicio</a>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>Crear Nueva Alerta</h2>
            <p class="form-description">
                <p>
                Configure una alerta personalizada para recordarle de sus tareas importantes.
            </p>
                <strong>Patrón Observer</strong> aplicado para notificaciones.
            </p>

            <!-- Mostrar errores -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/alertas" method="post" class="form">
                <div class="form-group">
                    <label for="tareaId">Tarea Asociada *</label>
                    <select id="tareaId" name="tareaId" class="form-control" required>
                        <option value="">-- Seleccione una tarea --</option>
                        <c:forEach var="tarea" items="${tareas}">
                            <option value="${tarea.id}">
                                    ${tarea.titulo} (${tarea.fechaVencimiento}) - ${tarea.prioridad}
                            </option>
                        </c:forEach>
                    </select>
                    <small class="form-text">
                        <c:if test="${empty tareas}">
                            ⚠️ No hay tareas disponibles.
                            <a href="${pageContext.request.contextPath}/planificar">Crear una tarea primero</a>
                        </c:if>
                    </small>
                </div>

                <div class="form-group">
                    <label for="mensaje">Mensaje de la Alerta *</label>
                    <textarea id="mensaje"
                              name="mensaje"
                              class="form-control"
                              rows="3"
                              placeholder="Ej: Recordatorio: Entregar proyecto mañana"
                              required
                              maxlength="500"></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="fechaAlerta">Fecha y Hora de la Alerta *</label>
                        <input type="datetime-local"
                               id="fechaAlerta"
                               name="fechaAlerta"
                               class="form-control"
                               required
                               min="<%= java.time.LocalDateTime.now() %>">
                        <small class="form-text">La fecha debe ser futura</small>
                    </div>

                    <div class="form-group">
                        <label for="tipo">Tipo de Alerta</label>
                        <select id="tipo" name="tipo" class="form-control">
                            <option value="Recordatorio" selected>Recordatorio</option>
                            <option value="Urgente">Urgente</option>
                            <option value="Informativa">Informativa</option>
                        </select>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" ${empty tareas ? 'disabled' : ''}>
                        ✓ Configurar Alerta
                    </button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                        Cancelar
                    </a>
                </div>
            </form>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información del Sistema</h3>
            <p>Este formulario implementa el caso de uso <strong>"Configurar Alertas"</strong>.</p>
            <ul>
                <li><strong>Patrón Observer:</strong> Cuando se crea una alerta, se notifica automáticamente a los listeners</li>
                <li><strong>Validación de fecha:</strong> La fecha debe ser futura (CP08)</li>
                <li><strong>Asociación:</strong> Cada alerta debe estar asociada a una tarea</li>
                <li><strong>Tipos disponibles:</strong> Recordatorio, Urgente, Informativa</li>
            </ul>
        </div>

        <div class="observer-info">
            <h3>👁️ Patrón Observer en Acción</h3>
            <p>Al guardar la alerta, el sistema ejecutará:</p>
            <ol>
                <li>Validación del DTO (AlertaDTO.validar())</li>
                <li>Creación de la entidad Alerta</li>
                <li>Persistencia en AlertaRepository</li>
                <li><strong>Notificación al AlertaListener (onAlertaCreada)</strong></li>
                <li>Confirmación al usuario</li>
            </ol>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>