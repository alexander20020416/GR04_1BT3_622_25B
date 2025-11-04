package com.gr4.controller;

import com.gr4.model.Proyecto;
import com.gr4.service.MateriaService;
import com.gr4.service.ProyectoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class GestorProyectoServletTest {

    @Mock
    private ProyectoService proyectoService;

    @Mock
    private MateriaService materiaService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private GestorProyectoServlet gestorProyectoServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //Pasar ambos servicios al constructor
        gestorProyectoServlet = new GestorProyectoServlet(proyectoService, materiaService);

        //Mock para la lista de materias (devuelve lista vacía por defecto)
        when(materiaService.listarMaterias()).thenReturn(java.util.List.of());
    }

    @Test
    void given_RequestCuandoHacerGet_then_DeberiaRedirigirALaVista() throws Exception {
        // Arrange
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/crear_proyecto.jsp"))
                .thenReturn(requestDispatcher);

        // Act
        gestorProyectoServlet.doGet(request, response);

        // Assert
        verify(request).getRequestDispatcher("/WEB-INF/views/crear_proyecto.jsp");
        verify(requestDispatcher).forward(request, response);
        // ✅ NUEVO: Verificar que se cargaron las materias
        verify(materiaService).listarMaterias();
    }

    @Test
    void given_ProyectoConDatos_when_HacerPost_then_DebeGuardarProyectoYRedirigir() throws Exception {
        // Arrange
        String titulo = "Nuevo Proyecto";
        String descripcion = "Descripción del proyecto";
        when(request.getParameter("titulo")).thenReturn(titulo);
        when(request.getParameter("descripcion")).thenReturn(descripcion);
        // ✅ Opcional: Agregar parámetros de fecha y materia
        when(request.getParameter("fechaVencimiento")).thenReturn(null);
        when(request.getParameter("materiaId")).thenReturn(null);
        when(request.getContextPath()).thenReturn("");

        // Act
        gestorProyectoServlet.doPost(request, response);

        // Assert
        verify(proyectoService, times(1)).guardarProyecto(any(Proyecto.class));
        verify(response).sendRedirect("/proyectos");
    }

    @Test
    void given_ProyectoSinNombre_when_HacerPost_then_DebeMostrarError() throws Exception {
        // Arrange
        when(request.getParameter("titulo")).thenReturn(""); // Título vacío
        when(request.getParameter("descripcion")).thenReturn("Alguna descripción");
        when(materiaService.listarMaterias()).thenReturn(java.util.List.of());

        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/crear_proyecto.jsp"))
                .thenReturn(requestDispatcher);

        // Act
        gestorProyectoServlet.doPost(request, response);

        // Assert
        verify(request).setAttribute("error", "El nombre del proyecto es obligatorio");
        verify(requestDispatcher).forward(request, response);
        verify(proyectoService, never()).guardarProyecto(any()); // No debe guardar
    }
}