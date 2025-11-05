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

        /* ========== Header con botones ========== */
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 16px;
            color: white;
        }

        .page-header h1 {
            margin: 0;
            font-size: 32px;
            font-weight: 700;
        }

        .header-actions {
            display: flex;
            gap: 15px;
            align-items: center;
        }

        .btn-icon {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: 2px solid rgba(255, 255, 255, 0.3);
            padding: 10px 15px;
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 20px;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            position: relative;
        }

        .btn-icon:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateY(-2px);
        }

        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background: #ef4444;
            color: white;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            font-size: 11px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .btn-logout {
            background: rgba(239, 68, 68, 0.2);
            border-color: rgba(239, 68, 68, 0.3);
        }

        .btn-logout:hover {
            background: rgba(239, 68, 68, 0.3);
        }

        /* ========== Tarjeta de Materia Moderna ========== */
        .materia-card {
            background: white;
            border-radius: 16px;
            overflow: visible;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border: 2px solid transparent;
            border-left: 6px solid #667eea; /* Color por defecto */
            position: relative;
            text-decoration: none;
            display: block;
        }

        .materia-card:hover {
            transform: translateY(-6px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
        }

        .materia-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
            display: flex;
            flex-direction: column;
            gap: 8px;
            position: relative;
        }

        .materia-codigo {
            color: rgba(255, 255, 255, 0.9);
            font-size: 12px;
            font-weight: 600;
            letter-spacing: 0.5px;
            text-transform: uppercase;
        }

        .materia-header h3 {
            color: white;
            margin: 0;
            font-size: 18px;
            font-weight: 700;
            padding-right: 40px;
        }

        .materia-id {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            align-self: flex-start;
            margin-top: 4px;
        }

        /* ========== Menú 3 Puntos ========== */
        .menu-tres-puntos {
            position: absolute;
            top: 15px;
            right: 15px;
            z-index: 10;
        }

        .btn-menu-puntos {
            background: rgba(255, 255, 255, 0.2);
            border: none;
            color: white;
            font-size: 20px;
            width: 32px;
            height: 32px;
            border-radius: 50%;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: all 0.3s ease;
            font-weight: bold;
        }

        .btn-menu-puntos:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: scale(1.1);
        }

        .dropdown-menu {
            position: absolute;
            top: 40px;
            right: 0;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            min-width: 160px;
            display: none;
            z-index: 100;
            overflow: hidden;
        }

        .dropdown-menu.show {
            display: block;
        }

        .dropdown-item {
            display: block;
            padding: 12px 16px;
            color: #374151;
            text-decoration: none;
            transition: background 0.2s;
            border: none;
            background: none;
            width: 100%;
            text-align: left;
            cursor: pointer;
            font-size: 14px;
        }

        .dropdown-item:hover {
            background: #f3f4f6;
        }

        .dropdown-item.danger {
            color: #ef4444;
        }

        .dropdown-item.danger:hover {
            background: #fee2e2;
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
    <div class="page-header">
        <h1>📚 Mi Espacio</h1>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/alertas" class="btn-icon" title="Notificaciones">
                🔔
                <c:if test="${numAlertas > 0}">
                    <span class="notification-badge">${numAlertas}</span>
                </c:if>
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="btn-icon btn-logout" title="Cerrar Sesión">
                🚪 Cerrar Sesión
            </a>
        </div>
    </div>

    <header style="margin-top: 0;">
        <a href="${pageContext.request.contextPath}/materias" class="btn btn-primary">
            ➕ Agregar Nueva Materia
        </a>
        <a href="${pageContext.request.contextPath}/planificar" class="btn btn-secondary">
            📝 Crear Tarea
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
                    <div class="materia-card" style="border-left-color: ${not empty m.color ? m.color : '#667eea'};">
                        <!-- Menú 3 puntos FUERA del enlace -->
                        <div class="menu-tres-puntos">
                            <button class="btn-menu-puntos" onclick="toggleMenu(event, ${m.id})">⋮</button>
                            <div id="menu-${m.id}" class="dropdown-menu">
                                <a href="${pageContext.request.contextPath}/editar-materia?id=${m.id}" 
                                   class="dropdown-item">
                                    ✏️ Editar
                                </a>
                                <button class="dropdown-item danger" 
                                        onclick="confirmarEliminacion(${m.id}, '${m.nombre}')">
                                    🗑️ Eliminar
                                </button>
                            </div>
                        </div>

                        <!-- Enlace clickeable a detalle -->
                        <a href="${pageContext.request.contextPath}/detalleMateria?id=${m.id}" 
                           style="text-decoration: none; color: inherit; display: block;">
                            <div class="materia-header">
                                <c:if test="${not empty m.codigo}">
                                    <div class="materia-codigo">${m.codigo}</div>
                                </c:if>
                                <h3>${m.nombre}</h3>
                                <span class="materia-id">ID: ${m.id}</span>
                            </div>
                            <div class="materia-body">
                                <p>${m.descripcion}</p>
                            </div>
                        </a>
                    </div>
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

<script>
    // Toggle menú desplegable
    function toggleMenu(event, id) {
        event.preventDefault();
        event.stopPropagation();
        
        // Cerrar todos los menús abiertos
        document.querySelectorAll('.dropdown-menu').forEach(menu => {
            if (menu.id !== 'menu-' + id) {
                menu.classList.remove('show');
            }
        });
        
        // Toggle el menú actual
        const menu = document.getElementById('menu-' + id);
        menu.classList.toggle('show');
    }

    // Confirmar eliminación
    function confirmarEliminacion(id, nombre) {
        if (confirm('¿Está seguro que desea eliminar la materia "' + nombre + '"?\n\nEsta acción no se puede deshacer.')) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/eliminar-materia';
            
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'id';
            input.value = id;
            
            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }

    // Cerrar menús al hacer clic fuera
    document.addEventListener('click', function(event) {
        if (!event.target.matches('.btn-menu-puntos')) {
            document.querySelectorAll('.dropdown-menu').forEach(menu => {
                menu.classList.remove('show');
            });
        }
    });
</script>

</body>
</html>