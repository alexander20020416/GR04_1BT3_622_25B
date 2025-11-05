<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Tarea al Proyecto - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }

        .agregar-tarea-container {
            max-width: 800px;
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

        /* Información del proyecto */
        .proyecto-info {
            background: linear-gradient(135deg, #f0f4ff 0%, #e9ecff 100%);
            border-left: 5px solid #667eea;
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 30px;
        }

        .proyecto-info h2 {
            color: #4338ca;
            font-size: 20px;
            font-weight: 700;
            margin: 0 0 8px 0;
        }

        .proyecto-info p {
            color: #6b7280;
            font-size: 14px;
            margin: 0;
        }

        /* Pestañas */
        .tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 25px;
            border-bottom: 2px solid #e5e7eb;
        }

        .tab-button {
            padding: 12px 24px;
            background: none;
            border: none;
            border-bottom: 3px solid transparent;
            color: #6b7280;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 15px;
            margin-bottom: -2px;
        }

        .tab-button:hover {
            color: #667eea;
        }

        .tab-button.active {
            color: #667eea;
            border-bottom-color: #667eea;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        /* Formulario */
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

        /* Info Box */
        .info-box {
            background: #e0f2fe;
            border-left: 4px solid #0284c7;
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            color: #075985;
            font-size: 14px;
            line-height: 1.6;
        }

        .info-box strong {
            display: block;
            margin-bottom: 5px;
            color: #0c4a6e;
        }

        /* Error/Success Messages */
        .alert {
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 10px;
            border-left: 4px solid;
        }

        .alert-error {
            background: #fee2e2;
            color: #991b1b;
            border-color: #dc2626;
        }

        .alert-success {
            background: #d1fae5;
            color: #065f46;
            border-color: #10b981;
        }

        /* Lista de tareas existentes */
        .tareas-existentes {
            max-height: 300px;
            overflow-y: auto;
            border: 2px solid #e5e7eb;
            border-radius: 10px;
            padding: 10px;
        }

        .tarea-item {
            padding: 12px 16px;
            background: #f9fafb;
            border-radius: 8px;
            margin-bottom: 10px;
            cursor: pointer;
            transition: all 0.2s ease;
            border: 2px solid transparent;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .tarea-item:hover {
            background: #f3f4f6;
            border-color: #667eea;
        }

        .tarea-item.selected {
            background: #e0e7ff;
            border-color: #667eea;
        }

        .tarea-item input[type="radio"] {
            margin-right: 12px;
        }

        .tarea-info h4 {
            color: #374151;
            font-size: 14px;
            font-weight: 600;
            margin: 0 0 4px 0;
        }

        .tarea-info p {
            color: #6b7280;
            font-size: 12px;
            margin: 0;
        }

        .tarea-materia {
            display: inline-block;
            background: #e0e7ff;
            color: #4338ca;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: 600;
        }

        .empty-tareas {
            text-align: center;
            padding: 40px 20px;
            color: #9ca3af;
        }

        @media (max-width: 768px) {
            .agregar-tarea-container {
                padding: 25px 20px;
            }

            .page-header h1 {
                font-size: 24px;
            }

            .tabs {
                flex-direction: column;
            }

            .tab-button {
                text-align: left;
                border-bottom: none;
                border-left: 3px solid transparent;
                margin-left: 0;
                padding-left: 15px;
            }

            .tab-button.active {
                border-left-color: #667eea;
                border-bottom-color: transparent;
            }
        }
    </style>
</head>
<body>
<div class="agregar-tarea-container">
    <a href="${pageContext.request.contextPath}/seguimiento" class="back-button">
        ← Volver al Seguimiento
    </a>

    <div class="page-header">
        <h1>➕ Agregar Tarea al Proyecto</h1>
        <p>Asocia tareas existentes o crea nuevas tareas para tu proyecto</p>
    </div>

    <!-- Información del Proyecto -->
    <c:if test="${not empty proyecto}">
        <div class="proyecto-info">
            <h2>📋 ${proyecto.titulo}</h2>
            <p>${proyecto.descripcion}</p>
            <c:if test="${not empty proyecto.materia}">
                <p style="margin-top: 8px;">
                    <span class="tarea-materia">📚 ${proyecto.materia.nombre}</span>
                </p>
            </c:if>
        </div>
    </c:if>

    <!-- Mensajes -->
    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <span>❌</span>
            <span>${error}</span>
        </div>
    </c:if>

    <c:if test="${not empty mensaje}">
        <div class="alert alert-success">
            <span>✅</span>
            <span>${mensaje}</span>
        </div>
    </c:if>

    <!-- Pestañas -->
    <div class="tabs">
        <button class="tab-button active" onclick="switchTab('asociar')">
            🔗 Asociar Tarea Existente
        </button>
        <button class="tab-button" onclick="switchTab('crear')">
            ➕ Crear Nueva Tarea
        </button>
    </div>

    <!-- TAB 1: Asociar Tarea Existente (CA2 - Escenario 1) -->
    <div id="tab-asociar" class="tab-content active">
        <div class="info-box">
            <strong>💡 CA2 - Escenario 1:</strong>
            Selecciona una tarea existente para asociarla a este proyecto.
        </div>

        <form action="${pageContext.request.contextPath}/gestionarProyecto" method="post">
            <input type="hidden" name="idProyecto" value="${proyecto.id}">
            <input type="hidden" name="accion" value="asociarTarea">

            <div class="form-group">
                <label>
                    Selecciona una tarea
                    <span class="required">*</span>
                </label>

                <c:choose>
                    <c:when test="${not empty tareasDisponibles}">
                        <div class="tareas-existentes">
                            <c:forEach var="tarea" items="${tareasDisponibles}">
                                <label class="tarea-item">
                                    <input type="radio" name="idTarea" value="${tarea.id}" required>
                                    <div class="tarea-info">
                                        <h4>${tarea.titulo}</h4>
                                        <p>${tarea.descripcion}</p>
                                        <c:if test="${not empty tarea.materia}">
                                            <span class="tarea-materia">📚 ${tarea.materia.nombre}</span>
                                        </c:if>
                                        <p style="margin-top: 4px;">
                                            📅 ${tarea.fechaVencimiento} | ⚡ ${tarea.prioridad} | 
                                            📊 ${tarea.estado}
                                        </p>
                                    </div>
                                </label>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-tareas">
                            <p>📭 No hay tareas disponibles para asociar</p>
                            <p style="font-size: 12px;">Crea una nueva tarea en la pestaña "Crear Nueva Tarea"</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not empty tareasDisponibles}">
                <button type="submit" class="btn-submit">
                    ✓ Asociar Tarea Seleccionada
                </button>
            </c:if>
        </form>
    </div>

    <!-- TAB 2: Crear Nueva Tarea -->
    <div id="tab-crear" class="tab-content">
        <div class="info-box">
            <strong>💡 CA2 - Crear nueva tarea:</strong>
            La tarea se creará automáticamente asociada a este proyecto.
        </div>

        <form action="${pageContext.request.contextPath}/gestionarProyecto" method="post">
            <input type="hidden" name="idProyecto" value="${proyecto.id}">
            <input type="hidden" name="accion" value="crearTarea">

            <!-- Título -->
            <div class="form-group">
                <label for="tituloTarea">
                    Título de la Tarea
                    <span class="required">*</span>
                </label>
                <input
                        type="text"
                        id="tituloTarea"
                        name="tituloTarea"
                        placeholder="Ej: Investigación bibliográfica"
                        required
                        maxlength="200">
                <span class="hint">Máximo 200 caracteres</span>
            </div>

            <!-- Descripción -->
            <div class="form-group">
                <label for="descripcionTarea">Descripción</label>
                <textarea
                        id="descripcionTarea"
                        name="descripcionTarea"
                        placeholder="Describe los detalles de la tarea..."
                        rows="4"></textarea>
                <span class="hint">Opcional - Describe qué se debe hacer</span>
            </div>

            <!-- Fecha de Vencimiento -->
            <div class="form-group">
                <label for="fechaVencimientoTarea">
                    Fecha de Vencimiento
                    <span class="required">*</span>
                </label>
                <input
                        type="date"
                        id="fechaVencimientoTarea"
                        name="fechaVencimientoTarea"
                        required
                        min="${java.time.LocalDate.now()}">
                <span class="hint">Fecha límite para completar la tarea</span>
            </div>

            <!-- Prioridad -->
            <div class="form-group">
                <label for="prioridad">Prioridad</label>
                <select id="prioridad" name="prioridad">
                    <option value="Alta">🔴 Alta</option>
                    <option value="Media" selected>🟡 Media</option>
                    <option value="Baja">🟢 Baja</option>
                </select>
            </div>

            <button type="submit" class="btn-submit">
                ✓ Crear y Agregar Tarea
            </button>
        </form>
    </div>
</div>

<script>
    function switchTab(tabName) {
        // Ocultar todos los tabs
        document.querySelectorAll('.tab-content').forEach(tab => {
            tab.classList.remove('active');
        });

        // Desactivar todos los botones
        document.querySelectorAll('.tab-button').forEach(btn => {
            btn.classList.remove('active');
        });

        // Activar el tab seleccionado
        if (tabName === 'asociar') {
            document.getElementById('tab-asociar').classList.add('active');
            document.querySelectorAll('.tab-button')[0].classList.add('active');
        } else {
            document.getElementById('tab-crear').classList.add('active');
            document.querySelectorAll('.tab-button')[1].classList.add('active');
        }
    }

    // Efecto de selección en tareas
    document.querySelectorAll('.tarea-item').forEach(item => {
        item.addEventListener('click', function() {
            document.querySelectorAll('.tarea-item').forEach(i => i.classList.remove('selected'));
            this.classList.add('selected');
            this.querySelector('input[type="radio"]').checked = true;
        });
    });
</script>
</body>
</html>
