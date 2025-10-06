package com.gr4.controller;

import com.gr4.model.Actividad;
import com.gr4.repository.ActividadRepository;
import com.gr4.repository.ActividadRepositoryImpl;
import com.gr4.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * Servlet temporal para debug y verificación de guardado
 * Eliminar después de resolver el problema
 */
@WebServlet(name = "DebugServlet", urlPatterns = {"/debug"})
public class DebugServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Debug - Gestor Tareas</title></head><body>");
        out.println("<h1>🔍 Panel de Debug</h1>");

        try {
            // TEST 1: Verificar EntityManagerFactory
            out.println("<h2>TEST 1: EntityManagerFactory</h2>");
            try {
                EntityManager em = JPAUtil.getEntityManager();
                out.println("<p style='color:green'>✓ EntityManager creado correctamente</p>");
                out.println("<p>Clase: " + em.getClass().getName() + "</p>");
                em.close();
            } catch (Exception e) {
                out.println("<p style='color:red'>✗ Error al crear EntityManager: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }

            // TEST 2: Crear y guardar una actividad de prueba
            out.println("<h2>TEST 2: Guardar Actividad de Prueba</h2>");
            try {
                ActividadRepository repo = new ActividadRepositoryImpl();

                Actividad actividad = new Actividad();
                actividad.setTitulo("Actividad de Prueba " + System.currentTimeMillis());
                actividad.setDescripcion("Esta es una actividad de prueba para verificar el guardado");
                actividad.setFechaEntrega(LocalDate.now().plusDays(7));
                actividad.setPrioridad("Alta");
                actividad.setEstado("Pendiente");

                out.println("<p>Intentando guardar actividad...</p>");
                out.println("<p>Título: " + actividad.getTitulo() + "</p>");
                out.println("<p>Fecha: " + actividad.getFechaEntrega() + "</p>");

                Actividad guardada = repo.save(actividad);

                out.println("<p style='color:green'>✓ Actividad guardada con ID: " + guardada.getId() + "</p>");

            } catch (Exception e) {
                out.println("<p style='color:red'>✗ Error al guardar: " + e.getMessage() + "</p>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            }

            // TEST 3: Consultar todas las actividades
            out.println("<h2>TEST 3: Consultar Todas las Actividades</h2>");
            try {
                ActividadRepository repo = new ActividadRepositoryImpl();
                List<Actividad> actividades = repo.findAll();

                out.println("<p>Total de actividades encontradas: <strong>" + actividades.size() + "</strong></p>");

                if (actividades.isEmpty()) {
                    out.println("<p style='color:orange'>⚠️ No hay actividades en la base de datos</p>");
                } else {
                    out.println("<table border='1' cellpadding='10' style='border-collapse:collapse'>");
                    out.println("<tr><th>ID</th><th>Título</th><th>Descripción</th><th>Fecha</th><th>Estado</th><th>Prioridad</th></tr>");

                    for (Actividad act : actividades) {
                        out.println("<tr>");
                        out.println("<td>" + act.getId() + "</td>");
                        out.println("<td>" + act.getTitulo() + "</td>");
                        out.println("<td>" + act.getDescripcion() + "</td>");
                        out.println("<td>" + act.getFechaEntrega() + "</td>");
                        out.println("<td>" + act.getEstado() + "</td>");
                        out.println("<td>" + act.getPrioridad() + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                }

            } catch (Exception e) {
                out.println("<p style='color:red'>✗ Error al consultar: " + e.getMessage() + "</p>");
                out.println("<pre>");
                e.printStackTrace(out);
                out.println("</pre>");
            }

            // TEST 4: Verificar tabla de actividades directamente
            out.println("<h2>TEST 4: Verificar Tabla en Base de Datos</h2>");
            try {
                EntityManager em = JPAUtil.getEntityManager();
                Long count = em.createQuery("SELECT COUNT(a) FROM Actividad a", Long.class).getSingleResult();
                out.println("<p>Número de registros en tabla ACTIVIDADES: <strong>" + count + "</strong></p>");
                em.close();
            } catch (Exception e) {
                out.println("<p style='color:red'>✗ Error: " + e.getMessage() + "</p>");
            }

            out.println("<hr>");
            out.println("<h2>Acciones</h2>");
            out.println("<p><a href='" + request.getContextPath() + "/debug'>🔄 Refrescar Tests</a></p>");
            out.println("<p><a href='" + request.getContextPath() + "/'>🏠 Volver al Inicio</a></p>");
            out.println("<p><a href='" + request.getContextPath() + "/planificar'>➕ Planificar Actividad</a></p>");
            out.println("<p><a href='" + request.getContextPath() + "/organizar'>📊 Ver Tareas</a></p>");

        } catch (Exception e) {
            out.println("<h2 style='color:red'>Error General</h2>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("</body></html>");
    }
}