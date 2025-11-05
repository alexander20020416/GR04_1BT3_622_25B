package com.gr4.controller;

import com.gr4.model.Materia;
import com.gr4.model.Tarea;
import com.gr4.repository.MateriaRepositoryImpl;
import com.gr4.util.ParametroParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Servlet Controlador para Editar Tareas
 * Implementa operación UPDATE del CRUD
 * Cumple con SRP - Solo se encarga de editar tareas existentes
 */
@WebServlet(name = "EditarTareaServlet", urlPatterns = {"/editar-tarea"})
public class EditarTareaServlet extends BaseServlet {

    private MateriaRepositoryImpl materiaRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        materiaRepository = new MateriaRepositoryImpl();
    }

    /**
     * Maneja peticiones GET - Muestra el formulario de edición con datos prellenados
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Obtener el ID de la tarea a editar
            String idStr = request.getParameter("id");
            
            if (idStr == null || idStr.trim().isEmpty()) {
                request.setAttribute("error", "ID de tarea no proporcionado");
                response.sendRedirect(request.getContextPath() + "/consultar");
                return;
            }

            Long id = Long.parseLong(idStr);

            // PASO 2: Buscar la tarea en el repositorio
            Optional<Tarea> tareaOpt = tareaRepository.findById(id);

            if (!tareaOpt.isPresent()) {
                request.setAttribute("error", "Tarea no encontrada con ID: " + id);
                response.sendRedirect(request.getContextPath() + "/consultar");
                return;
            }

            Tarea tarea = tareaOpt.get();
            System.out.println("📝 Editando tarea: " + tarea.getTitulo());

            // PASO 3: Cargar lista de materias disponibles
            List<Materia> materias = materiaRepository.findAll();
            request.setAttribute("materias", materias);

            // PASO 4: Enviar tarea a la vista
            request.setAttribute("tarea", tarea);
            request.getRequestDispatcher("/jsp/editar_tarea.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("✗ Error: ID inválido - " + e.getMessage());
            request.setAttribute("error", "ID de tarea inválido");
            response.sendRedirect(request.getContextPath() + "/consultar");
        } catch (Exception e) {
            System.err.println("✗ Error al cargar tarea para editar: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar la tarea");
            response.sendRedirect(request.getContextPath() + "/consultar");
        }
    }

    /**
     * Maneja peticiones POST - Procesa la actualización de la tarea
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PASO 1: Obtener ID de la tarea
            String idStr = request.getParameter("id");
            
            if (idStr == null || idStr.trim().isEmpty()) {
                request.setAttribute("error", "ID de tarea no proporcionado");
                request.getRequestDispatcher("/jsp/editar_tarea.jsp").forward(request, response);
                return;
            }

            Long id = Long.parseLong(idStr);

            // PASO 2: Buscar la tarea existente
            Optional<Tarea> tareaOpt = tareaRepository.findById(id);

            if (!tareaOpt.isPresent()) {
                request.setAttribute("error", "Tarea no encontrada");
                response.sendRedirect(request.getContextPath() + "/consultar");
                return;
            }

            Tarea tarea = tareaOpt.get();

            // PASO 3: Obtener datos actualizados del formulario
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fechaVencimientoStr = request.getParameter("fechaVencimiento");
            String estado = request.getParameter("estado");
            String prioridad = request.getParameter("prioridad");
            String materiaIdStr = request.getParameter("materiaId");

            System.out.println("📝 Actualizando tarea ID: " + id);

            // PASO 4: Validar datos
            if (titulo == null || titulo.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty() ||
                fechaVencimientoStr == null || fechaVencimientoStr.trim().isEmpty()) {
                
                request.setAttribute("error", "Todos los campos son obligatorios");
                request.setAttribute("tarea", tarea);
                request.getRequestDispatcher("/jsp/editar_tarea.jsp").forward(request, response);
                return;
            }

            // PASO 5: Actualizar los datos de la tarea
            tarea.setTitulo(titulo.trim());
            tarea.setDescripcion(descripcion.trim());
            tarea.setFechaVencimiento(ParametroParser.parseFecha(fechaVencimientoStr));
            tarea.setEstado(estado != null ? estado : Tarea.ESTADO_PENDIENTE);
            tarea.setPrioridad(prioridad != null ? prioridad : Tarea.PRIORIDAD_MEDIA);

            // PASO 5.1: Actualizar la materia asociada
            if (materiaIdStr != null && !materiaIdStr.trim().isEmpty()) {
                try {
                    Long materiaId = Long.parseLong(materiaIdStr);
                    Materia materia = materiaRepository.findById(materiaId);
                    if (materia != null) {
                        tarea.setMateria(materia);
                        System.out.println("✓ Materia actualizada: " + materia.getNombre());
                    }
                } catch (NumberFormatException e) {
                    System.err.println("⚠️ ID de materia inválido: " + materiaIdStr);
                }
            } else {
                tarea.setMateria(null);
                System.out.println("⚠️ Materia removida de la tarea");
            }

            // PASO 6: Guardar cambios en el repositorio
            Tarea tareaActualizada = tareaRepository.save(tarea);

            System.out.println("✓ Tarea actualizada exitosamente: " + tareaActualizada.getTitulo());

            // PASO 7: Redirigir con mensaje de éxito
            request.setAttribute("mensaje", "Tarea actualizada exitosamente");
            request.setAttribute("tarea", tareaActualizada);
            request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.err.println("✗ Error: ID inválido - " + e.getMessage());
            request.setAttribute("error", "ID de tarea inválido");
            request.getRequestDispatcher("/jsp/editar_tarea.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("✗ Error al actualizar tarea: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar la tarea: " + e.getMessage());
            request.getRequestDispatcher("/jsp/editar_tarea.jsp").forward(request, response);
        }
    }
}
