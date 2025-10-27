<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html><!DOCTYPE html>

<html lang="es"><html lang="es">

<head><head>

    <meta charset="UTF-8">    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Listado de Tareas - Gestor de Tareas</title>    <title>Listado de Actividades - Gestor de Tareas</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

</head></head>

<body><body>

<div class="container"><div class="container">

    <header>    <header>

        <h1>📋 Listado de Tareas</h1>        <h1>📋 Listado de Actividades</h1>

        <nav>        <nav>

            <a href="${pageContext.request.contextPath}/" class="btn-link">← Volver al inicio</a>            <a href="${pageContext.request.contextPath}/" class="btn-link">← Volver al inicio</a>

            <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">➕ Nueva Tarea</a>            <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">➕ Nueva Actividad</a>

        </nav>        </nav>

    </header>    </header>



    <main>    <main>

        <c:if test="${not empty error}">        <c:if test="${not empty error}">

            <div class="alert alert-error">            <div class="alert alert-error">

                <strong>❌ Error:</strong> ${error}                <strong>❌ Error:</strong> ${error}

            </div>            </div>

        </c:if>        </c:if>



        <c:if test="${not empty tareas}">        <c:if test="${not empty actividades}">

            <div class="activities-list">            <div class="activities-list">

                <c:forEach var="tarea" items="${tareas}">                <c:forEach var="actividad" items="${actividades}">

                    <div class="activity-card">                    <div class="activity-card">

                        <div class="activity-header">                        <div class="activity-header">

                            <h3>${tarea.titulo}</h3>                            <h3>${actividad.titulo}</h3>

                            <span class="badge badge-${tarea.prioridad == 'Alta' ? 'danger' : tarea.prioridad == 'Media' ? 'warning' : 'info'}">                            <span class="badge badge-${actividad.prioridad == 'Alta' ? 'danger' : actividad.prioridad == 'Media' ? 'warning' : 'info'}">

                                ${tarea.prioridad}                                ${actividad.prioridad}

                            </span>                            </span>

                        </div>                        </div>

                                                

                        <div class="activity-body">                        <div class="activity-body">

                            <p>${tarea.descripcion}</p>                            <p>${actividad.descripcion}</p>

                            <div class="activity-meta">                            <div class="activity-meta">

                                <span>📅 Vencimiento: ${tarea.fechaVencimiento}</span>                                <span>📅 Entrega: ${actividad.fechaEntrega}</span>

                                <span>📊 Estado: ${tarea.estado}</span>                                <span>📊 Estado: ${actividad.estado}</span>

                            </div>                            </div>

                        </div>                        </div>

                    </div>

                </c:forEach>                        <div class="activity-actions">

            </div>                            <a href="${pageContext.request.contextPath}/agregar-tarea?actividadId=${actividad.id}" 

        </c:if>                               class="btn btn-sm btn-success">

                                ➕ Agregar Tarea

        <c:if test="${empty tareas}">                            </a>

            <div class="empty-state">                            <a href="${pageContext.request.contextPath}/ver-tareas?actividadId=${actividad.id}" 

                <h3>📭 No hay tareas registradas</h3>                               class="btn btn-sm btn-info">

                <p>Comienza creando tu primera tarea académica</p>                                👁️ Ver Tareas

                <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">                            </a>

                    ➕ Crear Primera Tarea                        </div>

                </a>                    </div>

            </div>                </c:forEach>

        </c:if>            </div>

    </main>        </c:if>



    <footer>        <c:if test="${empty actividades}">

        <p>&copy; 2025 Grupo 4 - Gestor de Tareas Universitarias</p>            <div class="empty-state">

    </footer>                <h3>📭 No hay actividades registradas</h3>

</div>                <p>Comienza creando tu primera actividad académica</p>

</body>                <a href="${pageContext.request.contextPath}/planificar" class="btn btn-primary">

</html>                    ➕ Crear Primera Actividad

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
