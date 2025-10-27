package com.gr4.auth.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;



class RegisterServletTest {

    /* Test con Mocks */
    @Test
    void dadoFormularioRegistro_cuandoSeCargue_entoncesDebeTenerCamposRequeridos() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("nombre")).thenReturn("Javier");
        when(request.getParameter("correo")).thenReturn("javier@example.com");
        when(request.getParameter("password")).thenReturn("12345");

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        RegisterServlet servlet = new RegisterServlet();
        servlet.doPost(request, response);

        // Verificamos comportamiento
        verify(request).getParameter("nombre");
    }

}
