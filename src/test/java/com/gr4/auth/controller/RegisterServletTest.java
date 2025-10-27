package com.gr4.auth.controller;

import static org.junit.jupiter.api.Assertions.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class RegisterServletTest {

    /* Test con Mocks */
    @Test
    void dadoFormularioRegistro_cuandoSeCargue_entoncesDebeTenerCamposRequeridos() throws Exception {

        RegisterServlet registro = new RegisterServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        Mockito.when(request.getParameter("nombre")).thenReturn("Javier");
        Mockito.when(request.getParameter("correo")).thenReturn("javier@example.com");
        Mockito.when(request.getParameter("contraseña")).thenReturn("123456");

        registro.doPost(request, response);

        assertNotNull(request.getParameter("nombre"), "El campo nombre no debe ser nulo");
        assertNotNull(request.getParameter("correo"), "El campo correo no debe ser nulo");
        assertNotNull(request.getParameter("contraseña"), "El campo contraseña no debe ser nulo");
    }

}
