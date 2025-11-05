<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Proyecto - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .form-container-proyecto {
            max-width: 700px;
            margin: 0 auto;
            background: white;
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.12);
        }

        .page-title {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 10px;
            font-size: 2rem;
        }

        .page-subtitle {
            text-align: center;
            color: #7f8c8d;
            margin-bottom: 30px;
            font-size: 1rem;
        }

        .form-group-proyecto {
            margin-bottom: 24px;
        }

        .form-group-proyecto label {
            display: block;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .required-star {
            color: #e74c3c;
            margin-left: 4px;
        }

        .form-control-proyecto {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e0e6ed;
            border-radius: 10px;
            font-size: 15px;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        .form-control-proyecto:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }

        textarea.form-control-proyecto {
            min-height: 120px;
            resize: vertical;
            font-family: inherit;
        }

        .form-hint {
            font-size: 13px;
            color: #95a5a6;
            margin-top: 6px;
            display: block;
        }

        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 32px;
        }

        .btn-submit-proyecto {
            flex: 1;
            padding: 14px 28px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }

        .btn-submit-proyecto:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-cancel {
            padding: 14px 28px;
            background: #e0e6ed;
            color: #2c3e50;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }

        .btn-cancel:hover {
            background: #cbd5e0;
        }

        .alert-error-proyecto {
            background: #fee;
            border: 2px solid #e74c3c;
            color: #c0392b;
            padding: 16px 20px;
            border-radius: 10px;
            margin-bottom: 24px;
            display: flex;
            align-items: center;
            gap: 12px;
            font-weight: 500;
        }

        .icon-proyecto {
            font-size: 20px;
        }

        .materia-preview {
            background: #f8f9fa;
            padding: 12px 16px;
            border-radius: 8px;
            border-left: 4px solid #667eea;
            margin-top: 8px;
        }

        .materia-preview strong {
            color: #667eea;
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📚 Gestor de Tareas Universitarias</h1>
        <nav>
            <a href="${pageContext.request.contextPath}/jsp/materia-lista.jsp" class="btn-link">← Volver a Materias</a>
        </nav>
    </header>

    <main>
        <div class="form-container-proyecto">
            <h2 class="page-title">📋 Crear Nuevo Proyecto</h2>
            <p class="page-subtitle">Organiza tu trabajo en proyectos con seguimiento completo</p>

            <!-- Mostrar errores -->
            <c:if test="${not empty error}">
                <div class="alert-error-proyecto">
                    <span class="icon-proyecto">⚠️</span>
                    <span>${error}</span>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/proyectos" method="post">
                <!-- Título del Proyecto -->
                <div class="form-group-proyecto">
                    <label for="titulo">
                        <span class="icon-proyecto">📝</span>
                        Nombre del Proyecto
                        <span class="required-star">*</span>
                    </label>
                    <input type="text"
                           id="titulo"
                           name="titulo"
                           class="form-control-proyecto"
                           placeholder="Ej: Proyecto Final de Física"
                           required
                           maxlength="200">
                    <span class="form-hint">Nombre descriptivo de tu proyecto</span>
                </div>

                <!-- Descripción -->
                <div class="form-group-proyecto">
                    <label for="descripcion">
                        <span class="icon-proyecto">📄</span>
                        Descripción del Proyecto
                    </label>
                    <textarea id="descripcion"
                              name="descripcion"
                              class="form-control-proyecto"
                              placeholder="Describe los objetivos y alcance del proyecto..."></textarea>
                    <span class="form-hint">Detalla qué incluye este proyecto (opcional)</span>
                </div>

                <!-- Materia -->
                <div class="form-group-proyecto">
                    <label for="materiaId">
                        <span class="icon-proyecto">📚</span>
                        Materia
                        <span class="required-star">*</span>
                    </label>
                    <select id="materiaId" name="materiaId" class="form-control-proyecto" required>
                        <option value="">-- Selecciona una materia --</option>
                        <c:forEach var="materia" items="${materias}">
                            <option value="${materia.id}"
                                ${materiaIdPreseleccionada == materia.id ? 'selected' : ''}>
                                    ${materia.nombre}
                            </option>
                        </c:forEach>
                    </select>
                    <span class="form-hint">Asocia este proyecto a una materia</span>

                    <!-- Preview de materia seleccionada -->
                    <c:if test="${not empty param.materiaId}">
                        <div class="materia-preview">
                            <strong>Materia seleccionada:</strong>
                            <c:forEach var="materia" items="${materias}">
                                <c:if test="${materia.id == param.materiaId}">
                                    ${materia.nombre}
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <!-- Fecha de Vencimiento -->
                <div class="form-group-proyecto">
                    <label for="fechaVencimiento">
                        <span class="icon-proyecto">📅</span>
                        Fecha de Entrega Final
                        <span class="required-star">*</span>
                    </label>
                    <input type="date"
                           id="fechaVencimiento"
                           name="fechaVencimiento"
                           class="form-control-proyecto"
                           required
                           min="${java.time.LocalDate.now()}">
                    <span class="form-hint">Fecha límite para completar el proyecto</span>
                </div>

                <!-- Botones de Acción -->
                <div class="form-actions">
                    <button type="submit" class="btn-submit-proyecto">
                        <span>✓</span>
                        <span>Crear Proyecto</span>
                    </button>
                    <a href="${pageContext.request.contextPath}/jsp/materia-lista.jsp"
                       class="btn-cancel">
                        <span>✕</span>
                        <span>Cancelar</span>
                    </a>
                </div>
            </form>
        </div>

        <!-- Información adicional -->
        <div class="info-box" style="max-width: 700px; margin: 30px auto;">
            <h3>ℹ️ Acerca de los Proyectos</h3>
            <ul>
                <li><strong>Proyectos Especiales:</strong> Organiza trabajos grandes con múltiples tareas</li>
                <li><strong>Seguimiento Visual:</strong> Barra de progreso automática según tareas completadas</li>
                <li><strong>Indicadores de Urgencia:</strong> Alertas visuales según proximidad de fecha límite</li>
                <li><strong>Gestión de Tareas:</strong> Asigna y da seguimiento a tareas dentro del proyecto</li>
            </ul>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>

<script>
    // Auto-configurar fecha mínima como hoy
    document.addEventListener('DOMContentLoaded', function() {
        const fechaInput = document.getElementById('fechaVencimiento');
        const today = new Date().toISOString().split('T')[0];
        fechaInput.setAttribute('min', today);

        // Si no hay fecha, sugerir una semana adelante
        if (!fechaInput.value) {
            const nextWeek = new Date();
            nextWeek.setDate(nextWeek.getDate() + 7);
            fechaInput.value = nextWeek.toISOString().split('T')[0];
        }
    });
</script>
</body>
</html>