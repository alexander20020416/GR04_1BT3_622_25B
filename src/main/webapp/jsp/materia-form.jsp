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
        <h1>📘 Registrar Materia</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/home" class="btn-link">← Volver al inicio</a>
        </nav>
    </header>

    <main>
        <div class="form-container">
            <h2>Crear Materia</h2>
            <p class="form-description">
                Complete los siguientes campos para registrar una nueva materia.
            </p>

            <!-- Mostrar errores si existen -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>❌ Error:</strong> ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/materias" method="post" class="form">
                <div class="form-group">
                    <label for="nombre">Nombre de la Materia *</label>
                    <input type="text"
                           id="nombre"
                           name="nombre"
                           class="form-control"
                           placeholder="Ej: Programación I"
                           required
                           maxlength="100">
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción</label>
                    <input type="text"
                           id="descripcion"
                           name="descripcion"
                           class="form-control"
                           placeholder="Ej: Introducción a la programación en Java">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        ✓ Guardar Materia
                    </button>
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

