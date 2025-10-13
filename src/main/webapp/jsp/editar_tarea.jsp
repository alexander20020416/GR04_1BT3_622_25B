<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Tarea</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>✏️ Editar Tarea</h1>
        <nav>
            <a href="javascript:history.back()" class="btn-link">← Volver</a>
        </nav>
    </header>

    <main>
        <c:if test="${not empty tarea}">
            <form action="${pageContext.request.contextPath}/administracion" method="post" class="form">
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="id" value="${tarea.id}" />

                <label>Título</label>
                <input type="text" name="titulo" value="${tarea.titulo}" required />

                <label>Descripción</label>
                <textarea name="descripcion">${tarea.descripcion}</textarea>

                <label>Prioridad</label>
                <select name="prioridad">
                    <option value="Alta" ${tarea.prioridad == 'Alta' ? 'selected' : ''}>Alta</option>
                    <option value="Media" ${tarea.prioridad == 'Media' ? 'selected' : ''}>Media</option>
                    <option value="Baja" ${tarea.prioridad == 'Baja' ? 'selected' : ''}>Baja</option>
                </select>

                <label>Estado</label>
                <select name="estado">
                    <option value="Pendiente" ${tarea.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                    <option value="En Progreso" ${tarea.estado == 'En Progreso' ? 'selected' : ''}>En Progreso</option>
                    <option value="Completada" ${tarea.estado == 'Completada' ? 'selected' : ''}>Completada</option>
                </select>

                <label>Fecha Vencimiento</label>
                <input type="date" name="fechaVencimiento" value="${tarea.fechaVencimiento}" />

                <label>Orden (posición)</label>
                <input type="number" name="orden" value="${tarea.orden}" min="0" />

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Guardar</button>
                    <a href="javascript:history.back()" class="btn btn-outline">Cancelar</a>
                </div>
            </form>
        </c:if>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>