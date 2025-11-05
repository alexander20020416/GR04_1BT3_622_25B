<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Materias - Gestor de Tareas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        /* ========== Grid de Materias ========== */
        .materias-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
            gap: 24px;
            padding: 5px;
        }

        /* ========== Tarjeta de Materia Moderna ========== */
        .materia-card {
            background: white;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border: 2px solid transparent;
            position: relative;
            text-decoration: none;
            display: block;
            cursor: pointer;
        }

        .materia-card:hover {
            transform: translateY(-6px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
            border-color: #667eea;
        }

        .materia-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 16px;
        }

        .materia-header h3 {
            color: white;
            margin: 0;
            font-size: 18px;
            font-weight: 700;
            flex: 1;
        }

        .materia-id {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }

        .materia-body {
            padding: 20px;
        }

        .materia-body p {
            color: #4a5568;
            font-size: 14px;
            line-height: 1.6;
            margin: 0;
            min-height: 60px;
        }

        /* ========== Indicador de Click ========== */
        .materia-card::after {
            content: '→';
            position: absolute;
            right: 20px;
            bottom: 20px;
            font-size: 24px;
            color: #667eea;
            opacity: 0;
            transform: translateX(-10px);
            transition: all 0.3s ease;
        }

        .materia-card:hover::after {
            opacity: 1;
            transform: translateX(0);
        }

        /* ========== Empty State Mejorado ========== */
        .empty-state {
            text-align: center;
            padding: 80px 20px;
            background: linear-gradient(135deg, #f9fafb 0%, #f3f4f6 100%);
            border-radius: 20px;
            border: 3px dashed #d1d5db;
        }

        .empty-state h3 {
            color: #374151;
            font-size: 24px;
            margin-bottom: 12px;
        }

        .empty-state p {
            color: #6b7280;
            font-size: 16px;
            margin-bottom: 24px;
        }

        /* ========== Header Mejorado ========== */
        header nav {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        /* ========== Responsive ========== */
        @media (max-width: 768px) {
            .materias-list {
                grid-template-columns: 1fr;
            }

            .materia-header {
                flex-direction: column;
                text-align: center;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📚 Mi Espacio </h1>
        <a href="${pageContext.request.contextPath}/materias" class="btn btn-primary">
            ➕ Agregar Nueva Materia
        </a>
    </header>

    <main>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>❌ Error:</strong> ${error}
            </div>
        </c:if>

        <c:if test="${not empty materias}">
            <div class="materias-list">
                <c:forEach var="m" items="${materias}">
                    <a href="${pageContext.request.contextPath}/detalleMateria?id=${m.id}" class="materia-card" style="text-decoration: none; color: inherit; display: block;">
                        <div class="materia-header">
                            <h3>${m.nombre}</h3>
                            <span class="materia-id">ID: ${m.id}</span>
                        </div>
                        <div class="materia-body">
                            <p>${m.descripcion}</p>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty materias}">
            <div class="empty-state">
                <h3>📭 No hay materias registradas</h3>
                <p>Comienza creando tu primera materia</p>
                <a href="${pageContext.request.contextPath}/materias" class="btn btn-primary">
                    ➕ Crear Primera Materia
                </a>
            </div>
        </c:if>
    </main>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>