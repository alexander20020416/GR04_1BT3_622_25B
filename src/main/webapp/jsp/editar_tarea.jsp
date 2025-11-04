<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Tarea - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>📝 Editar Tarea</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/consultar" class="btn-link">← Volver a Consultar</a>
            <a href="${pageContext.request.contextPath}/home" class="btn-link">🏠 Inicio</a>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>Modificar Datos de la Tarea</h2>

            <!-- Mostrar errores -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/editar-tarea" method="post" class="form">
                <!-- Campo oculto con el ID -->
                <input type="hidden" name="id" value="${tarea.id}">

                <div class="form-group">
                    <label for="titulo">Título *</label>
                    <input type="text" 
                           id="titulo" 
                           name="titulo" 
                           class="form-control" 
                           value="${tarea.titulo}"
                           required 
                           maxlength="200"
                           placeholder="Ej: Proyecto Final de POO">
                    <small class="form-text">Máximo 200 caracteres</small>
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción *</label>
                    <textarea id="descripcion" 
                              name="descripcion" 
                              class="form-control" 
                              rows="4" 
                              required
                              placeholder="Describe los detalles de la tarea...">${tarea.descripcion}</textarea>
                    <small class="form-text">Describe la tarea con el mayor detalle posible</small>
                </div>

                <div class="form-group">
                    <label for="fechaVencimiento">Fecha de Vencimiento *</label>
                    <input type="date" 
                           id="fechaVencimiento" 
                           name="fechaVencimiento" 
                           class="form-control"
                           value="${tarea.fechaVencimiento}"
                           required>
                    <small class="form-text">Fecha límite para completar la tarea</small>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="estado">Estado *</label>
                        <select id="estado" name="estado" class="form-control" required>
                            <option value="Pendiente" ${tarea.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                            <option value="En Progreso" ${tarea.estado == 'En Progreso' ? 'selected' : ''}>En Progreso</option>
                            <option value="Completada" ${tarea.estado == 'Completada' ? 'selected' : ''}>Completada</option>
                        </select>
                        <small class="form-text">Estado actual de la tarea</small>
                    </div>

                    <div class="form-group">
                        <label for="prioridad">Prioridad *</label>
                        <select id="prioridad" name="prioridad" class="form-control" required>
                            <option value="Alta" ${tarea.prioridad == 'Alta' ? 'selected' : ''}>🔴 Alta</option>
                            <option value="Media" ${tarea.prioridad == 'Media' ? 'selected' : ''}>🟡 Media</option>
                            <option value="Baja" ${tarea.prioridad == 'Baja' ? 'selected' : ''}>🟢 Baja</option>
                        </select>
                        <small class="form-text">Nivel de importancia</small>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        ✓ Guardar Cambios
                    </button>
                    <a href="${pageContext.request.contextPath}/consultar" class="btn btn-secondary">
                        ✗ Cancelar
                    </a>
                </div>
            </form>
        </div>

        <!-- Información adicional -->
        <div class="info-box">
            <h3>ℹ️ Información</h3>
            <p><strong>ID de la Tarea:</strong> ${tarea.id}</p>
            <p>Los campos marcados con <strong>*</strong> son obligatorios.</p>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Metodologías Ágiles - EPN</p>
    </footer>
</div>

<style>
    .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 1rem;
    }

    .form-actions {
        display: flex;
        gap: 1rem;
        margin-top: 2rem;
    }

    .info-box {
        background-color: #e8f4f8;
        border-left: 4px solid #2196F3;
        padding: 1rem;
        margin-top: 2rem;
        border-radius: 4px;
    }

    .info-box h3 {
        margin-top: 0;
        color: #1976D2;
    }

    .info-box p {
        margin: 0.5rem 0;
        color: #555;
    }

    @media (max-width: 768px) {
        .form-row {
            grid-template-columns: 1fr;
        }

        .form-actions {
            flex-direction: column;
        }
    }
</style>
</body>
</html>
