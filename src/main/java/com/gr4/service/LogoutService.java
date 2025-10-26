package com.gr4.service;

import com.gr4.repository.UsuarioRepository;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutService {

    private final UsuarioRepository usuarioRepository;

    public LogoutService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean limpiarOValidarCookie(HttpServletRequest request, HttpServletResponse response,
                                         boolean eliminarSiempreToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return true;
        for (Cookie cookie : cookies) {
            if ("rememberMe".equals(cookie.getName())) {
                String token = cookie.getValue();
                if (eliminarSiempreToken || esTokenValido(token)) {
                    usuarioRepository.eliminarTokenRememberMe(token);
                }
                eliminarCookieDelNavegador(response);
                return true;
            }
        }
        return true;
    }

    private boolean esTokenValido(String token) {
        return token != null && !token.trim().isEmpty();
    }

    private void eliminarCookieDelNavegador(HttpServletResponse response) {
        Cookie cookieToDelete = new Cookie("rememberMe", "");
        cookieToDelete.setMaxAge(0);
        cookieToDelete.setPath("/");
        response.addCookie(cookieToDelete);
    }
}
