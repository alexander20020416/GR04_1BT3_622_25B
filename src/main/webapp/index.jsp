<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redirigir al HomeServlet que maneja la lógica de autenticación
    response.sendRedirect(request.getContextPath() + "/home");
%>