<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles de ${materia.nombre} - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        /* ========== Container Principal ========== */
        .materia-detail-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        /* ========== Header de Materia ========== */
        .materia-hero {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 20px;
            padding: 40px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
            position: relative;
            overflow: hidden;
        }

        .materia-hero::before {
            content: '';
            position: absolute;
            top: 0;
            right: 0;
            width: 300px;
            height: 300px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
            transform: translate(30%, -30%);
        }

        .materia-hero h1 {
            color: white;
            font-size: 32px;
            font-weight: 700;
            margin: 0 0 12px 0;
            position: relative;
            z-index: 1;
        }

        .materia-hero p {
            color: rgba(255, 255, 255, 0.9);
            font-size: 16px;
            line-height: 1.6;
            margin: 0 0 20px 0;
            position: relative;
            z-index: 1;
        }

        /* ========== Botones de Acción Compactos ========== */
        .action-buttons-compact {
            display: flex;
            gap: 12px;
            position: relative;
            z-index: 1;
            flex-wrap: wrap;
        }

        .btn-compact {
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 10px;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: 2px solid rgba(255, 255, 255, 0.3);
            backdrop-filter: blur(10px);
        }

        .btn-compact:hover {
            background: rgba(255, 255, 255, 0.3);
            border-color: rgba(255, 255, 255, 0.5);
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        /* ========== Botón de Regreso ========== */
        .back-button {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 12px 20px;
            background: white;
            color: #667eea;
            text-decoration: none;
            border-radius: 12px;
            font-weight: 600;
            font-size: 14px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            margin-bottom: 20px;
            border: 2px solid #667eea;
        }

        .back-button:hover {
            background: #667eea;
            color: white;
            transform: translateX(-4px);
        }

        /* ========== Sección de Tareas ========== */
        .tareas-section {
            background: white;
            border-radius: 16px;
            padding: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
        }

        .tareas-section h2 {
            color: #1f2937;
            font-size: 24px;
            font-weight: 700;
            margin: 0 0 20px 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .tareas-count {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
        }

        /* ========== Grid de Tareas ========== */
        .tareas-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .tarea-card {
            background: #f9fafb;
            border-radius: 12px;
            padding: 20px;
            border-left: 4px solid #667eea;
            transition: all 0.3s ease;
        }

        .tarea-card:hover {
            transform: translateX(4px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.2);
        }

        .tarea-card h3 {
            color: #1f2937;
            font-size: 18px;
            font-weight: 700;
            margin: 0 0 10px 0;
        }

        .tarea-card p {
            color: #6b7280;
            font-size: 14px;
            line-height: 1.6;
            margin: 0 0 15px 0;
        }

        .tarea-meta {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            font-size: 13px;
        }

        .tarea-meta span {
            display: flex;
            align-items: center;
            gap: 5px;
            color: #4b5563;
            font-weight: 500;
        }

        .badge {
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: 600;
            text-transform: uppercase;
        }

        .badge-alta {
            background: #fee2e2;
            color: #991b1b;
        }

        .badge-media {
            background: #fef3c7;
            color: #92400e;
        }

        .badge-baja {
            background: #d1fae5;
            color: #065f46;
        }

        /* ========== Empty State ========== */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: linear-gradient(135deg, #f9fafb 0%, #f3f4f6 100%);
            border-radius: 16px;
            border: 3px dashed #d1d5db;
        }

        .empty-state h3 {
            color: #374151;
            font-size: 20px;
            margin-bottom: 10px;
        }

        .empty-state p {
            color: #6b7280;
            font-size: 14px;
        }

        /* ========== Responsive ========== */
        @media (max-width: 768px) {
            .materia-hero {
                padding: 30px 20px;
            }

            .materia-hero h1 {
                font-size: 24px;
            }

            .action-buttons-compact {
                flex-direction: column;
            }

            .btn-compact {
                width: 100%;
            }

            .tareas-grid {
                grid-template-columns: 1fr;
            }
        }

        /* ========== Animaciones ========== */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .materia-hero,
        .tareas-section,
        .back-button {
            animation: fadeInUp 0.6s ease-out;
        }

        .tareas-section {
            animation-delay: 0.1s;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="materia-detail-container">
        <a href="${pageContext.request.contextPath}/listarMateria" class="back-button">
            ← Volver a Materias
        </a>

        <div class="materia-hero">
            <h1>📚 ${materia.nombre}</h1>
            <p>${materia.descripcion}</p>

            <div class="action-buttons-compact">
                <a href="${pageContext.request.contextPath}/planificar?materiaId=${materia.id}" class="btn-compact">
                    📝 Planificar Tarea
                </a>
                <a href="${pageContext.request.contextPath}/organizar?materiaId=${materia.id}" class="btn-compact">
                    📊 Organizar Tareas
                </a>
            </div>
        </div>

        <!-- Sección de Tareas -->
        <div class="tareas-section">
            <h2>
                📋 Tareas de esta Materia
                <c:if test="${not empty tareas}">
                    <span class="tareas-count">${tareas.size()}</span>
                </c:if>
            </h2>

            <c:choose>
                <c:when test="${not empty tareas}">
                    <div class="tareas-grid">
                        <c:forEach var="tarea" items="${tareas}">
                            <div class="tarea-card">
                                <h3>${tarea.titulo}</h3>
                                <p>${tarea.descripcion}</p>
                                <div class="tarea-meta">
                                    <span>
                                        <span class="badge badge-${tarea.prioridad.toLowerCase()}">
                                                ${tarea.prioridad}
                                        </span>
                                    </span>
                                    <span>📅 ${tarea.fechaVencimiento}</span>
                                    <span>📊 ${tarea.estado}</span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <h3>📭 No hay tareas registradas</h3>
                        <p>Comienza planificando tu primera tarea para esta materia</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>