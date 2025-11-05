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
            margin: 0;
            position: relative;
            z-index: 1;
        }

        /* ========== Navegación Horizontal Moderna ========== */
        .nav-horizontal {
            display: flex;
            gap: 16px;
            background: white;
            padding: 16px;
            border-radius: 16px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
            flex-wrap: wrap;
        }

        .nav-horizontal a {
            flex: 1;
            min-width: 200px;
            text-decoration: none;
            padding: 16px 24px;
            border-radius: 12px;
            font-weight: 600;
            font-size: 15px;
            text-align: center;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            position: relative;
            overflow: hidden;
        }

        .nav-horizontal a::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, transparent 0%, rgba(255, 255, 255, 0.2) 100%);
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .nav-horizontal a:hover::before {
            opacity: 1;
        }

        .nav-planificar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
        }

        .nav-planificar:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
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

        /* ========== Responsive ========== */
        @media (max-width: 768px) {
            .materia-hero {
                padding: 30px 20px;
            }

            .materia-hero h1 {
                font-size: 24px;
            }

            .nav-horizontal {
                flex-direction: column;
                gap: 12px;
            }

            .nav-horizontal a {
                min-width: auto;
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
        .nav-horizontal,
        .back-button {
            animation: fadeInUp 0.6s ease-out;
        }

        .nav-horizontal {
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
        </div>

        <div class="nav-horizontal">
            <a href="${pageContext.request.contextPath}/planificar?materiaId=${materia.id}" class="nav-planificar">
                📝 Planificar
            </a>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>
    </footer>
</div>
</body>
</html>