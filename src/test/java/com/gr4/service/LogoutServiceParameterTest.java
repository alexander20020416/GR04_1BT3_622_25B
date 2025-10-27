package com.gr4.service;

import static org.junit.Assert.*;
import com.gr4.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class LogoutServiceParameterTest {

    private LogoutService logoutService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    // Parámetros del test
    private String tokenValue;
    private boolean debeEliminarEnBD;
    private String descripcionEscenario;

    public LogoutServiceParameterTest(String tokenValue, boolean debeEliminarEnBD, String descripcionEscenario) {
        this.tokenValue = tokenValue;
        this.debeEliminarEnBD = debeEliminarEnBD;
        this.descripcionEscenario = descripcionEscenario;
    }

    @Parameters(name = "Escenario {index}: {2}")
    public static Collection<Object[]> parametros() {
        return Arrays.asList(new Object[][]{
                // Escenario 1: Token válido normal
                {"abc123token456", true, "Token válido debe eliminarse correctamente"},

                // Escenario 2: Token vacío
                {"", false, "Token vacío no debe intentar eliminar en BD"},

                // Escenario 3: Token muy largo (más de 255 caracteres)
                {generarTokenLargo(300), true, "Token largo debe manejarse correctamente"},

                // Escenario 4: Token con caracteres especiales
                {"token@#$%&*()!", true, "Token con caracteres especiales debe manejarse"},

                // Escenario 5: Token con espacios
                {"token con espacios", true, "Token con espacios debe manejarse"},

                // Escenario 6: Token solo con espacios
                {"   ", false, "Token solo con espacios no debe procesarse"},

                // Escenario 7: Token alfanumérico complejo
                {"aBc123XyZ789_-.", true, "Token alfanumérico complejo debe eliminarse"}
        });
    }

    private static String generarTokenLargo(int longitud) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append("a");
        }
        return sb.toString();
    }

    @Before
    public void setUp() {
        // Given: Se inicializan los mocks y el servicio antes de cada test
        MockitoAnnotations.openMocks(this);
        logoutService = new LogoutService(usuarioRepository);
    }

    //Verifica que diferentes estados de tokens se manejen correctamente durante el cierre de sesión
    @Test
    public void given_CookieConDiferentesEstados_when_ValidarYLimpiarCookie_then_DebeManejarseCorrectamente() {
        // Given: Preparamos una cookie "rememberMe" con el token parametrizado
        Cookie rememberMeCookie = new Cookie("rememberMe", tokenValue);
        Cookie[] cookies = {rememberMeCookie};

        when(request.getCookies()).thenReturn(cookies);
        when(usuarioRepository.eliminarTokenRememberMe(anyString())).thenReturn(true);

        // When: Validamos y limpiamos la cookie
        boolean resultado = logoutService.limpiarOValidarCookie(request, response, debeEliminarEnBD);

        // Then: Verificamos si debe eliminar en BD según el tipo de token
        if (debeEliminarEnBD) {
            verify(usuarioRepository, times(1)).eliminarTokenRememberMe(tokenValue);
        } else {
            verify(usuarioRepository, never()).eliminarTokenRememberMe(anyString());
        }

        // Then: SIEMPRE debe eliminar la cookie del navegador por seguridad
        verify(response, times(1)).addCookie(argThat(cookie ->
                cookie.getName().equals("rememberMe") &&
                        cookie.getMaxAge() == 0
        ));

        // Then: Debe retornar true en todos los casos (operación completada)
        String mensajeError = String.format(
                "%s - esperaba true pero obtuvo %s",
                descripcionEscenario, resultado);
        assertTrue(mensajeError, resultado);
    }
}


