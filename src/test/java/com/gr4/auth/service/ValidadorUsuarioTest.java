package com.gr4.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidadorUsuarioTest {

    @ParameterizedTest
    @CsvSource({
            /* Casos válidos */
            "'Javier', 'javier@outlook.com', '123456', true",
            "'Ana', 'ana@gmail.com', 'abcdef', true",
            /* Casos inválidos */
            "'', 'correo@valido.com', '123456', false",
            "'Pedro', '', '123456', false",
            "'Pedro', 'correoincorrecto', '123456', false",
            "'Pedro', 'correo@valido.com', '', false",
            "'Pedro', 'correo@valido.com', '123', false"
    })
    void dadoUsuarioConParametros_cuandoSeValide_entoncesDebeRetornarResultadoEsperado(
            String nombre, String correo, String contraseña, boolean esperado) {

        boolean resultado = ValidadorUsuario.validarUsuario(nombre, correo, contraseña);

        assertEquals(esperado, resultado,
                () -> String.format("Fallo con: nombre='%s', correo='%s', contraseña='%s'",
                        nombre, correo, contraseña));
    }

}
