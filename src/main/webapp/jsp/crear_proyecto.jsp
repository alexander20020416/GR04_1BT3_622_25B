<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Crear Proyecto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
        }
        .error {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
            padding: 10px;
            background-color: #ffe6e6;
            border: 1px solid red;
            border-radius: 4px;
        }
        form {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            border: 1px solid #ddd;
        }
        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"],
        input[type="date"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }
        textarea {
            resize: vertical;
            min-height: 80px;
        }
        .required {
            color: red;
        }
        button {
            margin-top: 20px;
            padding: 12px 30px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            font-size: 16px;
            font-weight: bold;
        }
        button:hover {
            background-color: #45a049;
        }
        .hint {
            font-size: 12px;
            color: #666;
            margin-top: 3px;
        }
    </style>
</head>
<body>
<h1>Crear Proyecto Especial</h1>
<p class="hint">Crea un proyecto con seguimiento completo de tareas</p>

<!-- ✅ Mostrar mensaje de error si existe (CA1 - Escenario 2) -->
<c:if test="${not empty error}">
    <div class="error">
            ${error}
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/gestionarProyecto" method="post">

    <!-- Título (obligatorio) -->
    <label for="titulo">
        Nombre del Proyecto <span class="required">*</span>
    </label>
    <input type="text" id="titulo" name="titulo" required
           placeholder="Ej: Proyecto Final de Física">

    <!-- Descripción -->
    <label for="descripcion">Descripción:</label>
    <textarea id="descripcion" name="descripcion"
              placeholder="Describe tu proyecto..."></textarea>

    <!-- ✅ NUEVO: Selector de Materia (CA1 - HU completa) -->
    <label for="materiaId">
        Materia <span class="required">*</span>
    </label>
    <select id="materiaId" name="materiaId" required>
        <option value="">-- Selecciona una materia --</option>
        <c:forEach var="materia" items="${materias}">
            <option value="${materia.id}">
                    ${materia.nombre}
            </option>
        </c:forEach>
    </select>
    <p class="hint">Selecciona la materia a la que pertenece este proyecto</p>

    <!-- Fecha de entrega final -->
    <label for="fechaVencimiento">
        Fecha de Entrega Final <span class="required">*</span>
    </label>
    <input type="date" id="fechaVencimiento" name="fechaVencimiento" required>
    <p class="hint">Fecha límite para completar todo el proyecto</p>

    <button type="submit">✓ Crear Proyecto</button>
</form>

<p style="margin-top: 20px; text-align: center;">
    <a href="${pageContext.request.contextPath}/proyectos">← Volver a la lista</a>
</p>
</body>
</html>