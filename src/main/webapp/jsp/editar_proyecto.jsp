<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Proyecto - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }

        .edit-project-container {
            max-width: 700px;
            margin: 0 auto;
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            animation: slideIn 0.5s ease-out;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .page-header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #f0f0f0;
        }

        .page-header h1 {
            color: #1f2937;
            font-size: 32px;
            font-weight: 700;
            margin: 0 0 10px 0;
        }

        .page-header p {
            color: #6b7280;
            font-size: 16px;
            margin: 0;
        }

        .back-button {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background: #f3f4f6;
            color: #667eea;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s ease;
            margin-bottom: 20px;
            border: 2px solid #e5e7eb;
        }

        .back-button:hover {
            background: #667eea;
            color: white;
            border-color: #667eea;
            transform: translateX(-4px);
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            color: #374151;
            font-weight: 600;
            font-size: 14px;
            margin-bottom: 8px;
        }

        .form-group label .required {
            color: #dc2626;
            margin-left: 4px;
        }

        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e5e7eb;
            border-radius: 10px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        .form-group input:focus,
        .form-group textarea:focus,
        .form-group select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }

        .form-group .hint {
            font-size: 12px;
            color: #6b7280;
            margin-top: 6px;
            display: block;
        }

        .btn-submit {
            width: 100%;
            padding: 14px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
        }

        .btn-submit:active {
            transform: translateY(0);
        }

        .error-message {
            background: #fee2e2;
            color: #991b1b;
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            border-left: 4px solid #dc2626;
            font-weight: 500;
        }
    </style>
</head>
<body>
<div class="edit-project-container">
    <a href="${pageContext.request.contextPath}/seguimiento" class="back-button">
        ← Volver al Seguimiento
    </a>

    <div class="page-header">
        <h1>✏️ Editar Proyecto</h1>
        <p>Actualiza la información de tu proyecto</p>
    </div>

    <c:if test="${not empty error}">
        <div class="error-message">
            ❌ ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/editarProyecto" method="post">
        <input type="hidden" name="id" value="${proyecto.id}">

        <!-- Título -->
        <div class="form-group">
            <label for="titulo">
                Nombre del Proyecto
                <span class="required">*</span>
            </label>
            <input
                    type="text"
                    id="titulo"
                    name="titulo"
                    value="${proyecto.titulo}"
                    required
                    maxlength="200">
            <span class="hint">Máximo 200 caracteres</span>
        </div>

        <!-- Descripción -->
        <div class="form-group">
            <label for="descripcion">Descripción</label>
            <textarea
                    id="descripcion"
                    name="descripcion"
                    rows="4">${proyecto.descripcion}</textarea>
            <span class="hint">Opcional - Describe los detalles de tu proyecto</span>
        </div>

        <!-- Materia -->
        <div class="form-group">
            <label for="materiaId">
                Materia
                <span class="required">*</span>
            </label>
            <select id="materiaId" name="materiaId" required>
                <option value="">-- Selecciona una materia --</option>
                <c:forEach var="materia" items="${materias}">
                    <option value="${materia.id}"
                            ${proyecto.materia != null && proyecto.materia.id == materia.id ? 'selected' : ''}>
                            ${materia.nombre}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Fecha de Vencimiento -->
        <div class="form-group">
            <label for="fechaVencimiento">
                Fecha de Entrega Final
                <span class="required">*</span>
            </label>
            <input
                    type="date"
                    id="fechaVencimiento"
                    name="fechaVencimiento"
                    value="${proyecto.fechaVencimiento}"
                    required>
            <span class="hint">Fecha límite para completar todo el proyecto</span>
        </div>

        <!-- Botón -->
        <button type="submit" class="btn-submit">
            ✓ Guardar Cambios
        </button>
    </form>
</div>
</body>
</html>
