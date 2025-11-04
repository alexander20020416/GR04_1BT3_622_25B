<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Alerta - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>✏️ Editar Alerta</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/alertas" class="btn-link">← Volver a Alertas</a>
        </nav>
    </header>

    <main>
        <div class="form-container-modern">
            <div class="form-card">
                <div class="form-header">
                    <h2>📝 Modificar Alerta #${alerta.id}</h2>
                    <p>Actualiza los datos de tu alerta</p>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-error">
                        <strong>❌ Error:</strong> ${error}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/alertas" method="post" class="form-modern">
                    <input type="hidden" name="action" value="actualizar">
                    <input type="hidden" name="alertaId" value="${alerta.id}">

                    <div class="form-group-modern">
                        <label for="tareaId" class="form-label">
                            📋 Tarea Asociada *
                        </label>
                        <select id="tareaId" name="tareaId" class="form-control-modern" required>
                            <option value="">-- Seleccione una tarea --</option>
                            <c:forEach var="tarea" items="${tareas}">
                                <option value="${tarea.id}" ${tarea.id == alerta.tarea.id ? 'selected' : ''}>
                                    ${tarea.titulo} (${tarea.fechaVencimiento}) - ${tarea.prioridad}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group-modern">
                        <label for="mensaje" class="form-label">
                            💬 Mensaje de la Alerta *
                        </label>
                        <textarea id="mensaje"
                                  name="mensaje"
                                  class="form-control-modern"
                                  rows="4"
                                  placeholder="Escribe el mensaje de tu alerta..."
                                  required
                                  maxlength="500">${alerta.mensaje}</textarea>
                        <small class="form-hint">Máximo 500 caracteres</small>
                    </div>

                    <div class="form-row-modern">
                        <div class="form-group-modern">
                            <label for="fechaAlerta" class="form-label">
                                📅 Fecha y Hora *
                            </label>
                            <input type="datetime-local"
                                   id="fechaAlerta"
                                   name="fechaAlerta"
                                   class="form-control-modern"
                                   value="<fmt:formatDate value='${alerta.fechaAlerta}' pattern='yyyy-MM-dd\'T\'HH:mm' />"
                                   required>
                        </div>

                        <div class="form-group-modern">
                            <label for="tipo" class="form-label">
                                🏷️ Tipo de Alerta
                            </label>
                            <select id="tipo" name="tipo" class="form-control-modern">
                                <option value="Recordatorio" ${alerta.tipo == 'Recordatorio' ? 'selected' : ''}>
                                    🔔 Recordatorio
                                </option>
                                <option value="Urgente" ${alerta.tipo == 'Urgente' ? 'selected' : ''}>
                                    🚨 Urgente
                                </option>
                                <option value="Informativa" ${alerta.tipo == 'Informativa' ? 'selected' : ''}>
                                    ℹ️ Informativa
                                </option>
                            </select>
                        </div>
                    </div>

                    <div class="form-actions-modern">
                        <button type="submit" class="btn-submit-modern">
                            ✓ Guardar Cambios
                        </button>
                        <a href="${pageContext.request.contextPath}/alertas" class="btn-cancel-modern">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>

<style>
    .form-container-modern {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
    }

    .form-card {
        background: white;
        border-radius: 20px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        overflow: hidden;
    }

    .form-header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 32px;
        text-align: center;
    }

    .form-header h2 {
        margin: 0 0 8px 0;
        font-size: 1.8rem;
    }

    .form-header p {
        margin: 0;
        opacity: 0.95;
        font-size: 1.05rem;
    }

    .form-modern {
        padding: 32px;
    }

    .form-group-modern {
        margin-bottom: 24px;
    }

    .form-label {
        display: block;
        font-weight: 600;
        color: #2d3748;
        margin-bottom: 8px;
        font-size: 1rem;
    }

    .form-control-modern {
        width: 100%;
        padding: 14px 16px;
        border: 2px solid #e2e8f0;
        border-radius: 12px;
        font-size: 1rem;
        font-family: inherit;
        transition: all 0.3s ease;
        background: white;
    }

    .form-control-modern:focus {
        outline: none;
        border-color: #667eea;
        box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
    }

    textarea.form-control-modern {
        resize: vertical;
        min-height: 100px;
    }

    .form-hint {
        display: block;
        margin-top: 6px;
        font-size: 0.85rem;
        color: #718096;
    }

    .form-row-modern {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 20px;
    }

    .form-actions-modern {
        display: flex;
        gap: 12px;
        margin-top: 32px;
        padding-top: 24px;
        border-top: 2px solid #e2e8f0;
    }

    .btn-submit-modern {
        flex: 1;
        padding: 16px 32px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        border: none;
        border-radius: 12px;
        font-size: 1.05rem;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
    }

    .btn-submit-modern:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
    }

    .btn-cancel-modern {
        flex: 1;
        padding: 16px 32px;
        background: white;
        color: #718096;
        border: 2px solid #e2e8f0;
        border-radius: 12px;
        font-size: 1.05rem;
        font-weight: 600;
        text-align: center;
        text-decoration: none;
        transition: all 0.3s ease;
        display: inline-block;
    }

    .btn-cancel-modern:hover {
        border-color: #cbd5e0;
        background: #f7fafc;
    }

    .alert {
        margin-bottom: 20px;
        padding: 16px 20px;
        border-radius: 12px;
        font-size: 0.95rem;
    }

    .alert-error {
        background: #fee;
        color: #c00;
        border: 2px solid #fcc;
    }
</style>
</body>
</html>
