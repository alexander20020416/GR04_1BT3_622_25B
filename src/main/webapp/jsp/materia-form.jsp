<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Materia - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="container">
    <header>
        <h1>📘 ${isEdit ? 'Editar' : 'Registrar'} Materia</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/listarMateria" class="btn-link">← Volver al inicio</a>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>${isEdit ? 'Editar' : 'Crear'} Materia</h2>
            <p class="form-description">
                ${isEdit ? 'Modifique los campos que desee actualizar.' : 'Complete los siguientes campos para registrar una nueva materia.'}
            </p>

            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/${isEdit ? 'editar-materia' : 'materias'}" method="post" class="form">
                <c:if test="${isEdit}">
                    <input type="hidden" name="id" value="${materia.id}">
                </c:if>

                <div class="form-group">
                    <label for="nombre">Nombre de la Materia *</label>
                    <input type="text"
                           id="nombre"
                           name="nombre"
                           class="form-control"
                           placeholder="Ej: Programación I"
                           value="${materia.nombre}"
                           required
                           maxlength="100">
                </div>

                <div class="form-group">
                    <label for="codigo">Código de Materia</label>
                    <input type="text"
                           id="codigo"
                           name="codigo"
                           class="form-control"
                           placeholder="Ej: PROG-101"
                           value="${materia.codigo}"
                           maxlength="50">
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción</label>
                    <input type="text"
                           id="descripcion"
                           name="descripcion"
                           class="form-control"
                           placeholder="Ej: Introducción a la programación en Java"
                           value="${materia.descripcion}">
                </div>

                <div class="form-group">
                    <label for="color">Color Identificativo</label>
                    <div style="display: flex; gap: 15px; align-items: center;">
                        <input type="color"
                               id="color"
                               name="color"
                               class="form-control"
                               value="${not empty materia.color ? materia.color : '#667eea'}"
                               style="width: 80px; height: 40px; cursor: pointer;">
                        <span style="color: #6b7280; font-size: 14px;">Seleccione un color para identificar visualmente esta materia</span>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        ✓ ${isEdit ? 'Actualizar' : 'Guardar'} Materia
                    </button>
                    <c:if test="${isEdit}">
                        <a href="${pageContext.request.contextPath}/detalleMateria?id=${materia.id}" class="btn btn-secondary">
                            ✗ Cancelar
                        </a>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="info-box">
            <h3>ℹ️ Información</h3>
            <p>Este formulario implementa el caso de uso <strong>"Registrar Materia"</strong>.</p>
            <ul>
                <li>El campo <strong>Nombre</strong> es obligatorio</li>
                <li>Las materias se gestionan mediante el patrón <strong>Repository</strong> y Hibernate</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>

