package com.gr4.service;

import com.gr4.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class LogoutServiceMockTest {

    private LogoutService logoutService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        // Given: Se inicializa el servicio con el repositorio mockeado
        logoutService = new LogoutService(usuarioRepository);
    }

    /**
     * CA3: Verifica que al cerrar sesión se elimine completamente
     * la cookie "rememberMe" del navegador Y su token de la base de datos
     */
    @Test
    public void given_UsuarioConCookieRememberMe_when_CerrarSesion_then_DebeEliminarCookieYTokenDeBD() {
        // Given: Se prepara el mock del request con una cookie "rememberMe"
        String tokenValido = "abc123token456xyz";
        Cookie rememberMeCookie = new Cookie("rememberMe", tokenValido);
        Cookie sessionCookie = new Cookie("JSESSIONID", "session123");
        Cookie[] cookies = {rememberMeCookie, sessionCookie};

        when(request.getCookies()).thenReturn(cookies);
        when(usuarioRepository.eliminarTokenRememberMe(tokenValido)).thenReturn(true);

        // When: Se ejecuta el cierre de sesión limpiando la cookie
        boolean resultado = logoutService.limpiarOValidarCookie(request, response, true);

        // Then: Se debe eliminar el token de la BD exactamente UNA vez
        verify(usuarioRepository, times(1)).eliminarTokenRememberMe(tokenValido);

        // Then: Debe crear una cookie con MaxAge=0 para eliminarla del navegador
        verify(response, times(1)).addCookie(argThat(cookie ->
                cookie.getName().equals("rememberMe") &&
                        cookie.getMaxAge() == 0 &&
                        cookie.getValue().equals("") &&
                        cookie.getPath().equals("/")
        ));

        // Then: El resultado debe ser exitoso
        assertTrue(resultado, "La limpieza de cookie debe retornar true");

        // Then: NO debe eliminar otras cookies
        verify(response, never()).addCookie(argThat(cookie ->
                cookie.getName().equals("JSESSIONID")
        ));
    }

}





