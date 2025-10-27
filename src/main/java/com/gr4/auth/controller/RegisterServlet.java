package com.gr4.auth.controller;

import com.gr4.auth.model.Usuario;
import com.gr4.auth.service.ValidadorUsuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        /* Obtener datos del formulario */

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");

        /* Validación minima de Campos */

        if (nombre == null || nombre.isEmpty() ||
            correo == null || correo.isEmpty() ||
            contraseña == null || contraseña.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if(!ValidadorUsuario.validarUsuario(nombre, correo, contraseña)) {
            request.setAttribute("error", "Datos inválidos");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }

        /* Crear un nuevo Usuario */

        Usuario usuario = new Usuario(nombre, correo, contraseña);

        /* Redirección al Login*/
        response.sendRedirect("login.jsp");

    }

}
