package com.gr4.auth.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    
    @Test
    public void given_DatosDeUsuarioValidos_when_CrearNuevoUsuario_then_DebeCrearseCorrectamente() {
        // Arrange
        String nombre = "Juan Pérez";
        String correo = "juan.perez@gmail.com";
        String contraseña = "password123";

        // Act
        Usuario usuario = new Usuario(nombre, correo, contraseña);

        // Assert
        assertNotNull(usuario, "El usuario no debería ser null");
        assertEquals(nombre, usuario.getNombre(), "El nombre debería coincidir");
        assertEquals(correo, usuario.getCorreo(), "El correo debería coincidir");
        assertEquals(contraseña, usuario.getContraseña(), "La contraseña debería coincidir");
    }

    @Test
    public void given_CorreoConDiferentesFormatos_when_ValidarFormatoCorreo_then_DebeValidarFormatoCorrectamente() {
        // Arrange
        Usuario usuarioValido = new Usuario("Juan", "usuario@gmail.com", "pass123");
        Usuario usuarioSinArroba = new Usuario("Pedro", "usuariogmail.com", "pass123");
        Usuario usuarioCorreoVacio = new Usuario("Ana", "", "pass123");
        Usuario usuarioCorreoNull = new Usuario("Luis", null, "pass123");
        Usuario usuarioSinDominio = new Usuario("María", "usuario@", "pass123");

        // Act & Assert
        assertTrue(usuarioValido.validarFormatoCorreo(), 
            "Correo con formato válido debe ser aceptado");
        assertFalse(usuarioSinArroba.validarFormatoCorreo(), 
            "Correo sin @ debe ser rechazado");
        assertFalse(usuarioCorreoVacio.validarFormatoCorreo(), 
            "Correo vacío debe ser rechazado");
        assertFalse(usuarioCorreoNull.validarFormatoCorreo(), 
            "Correo null debe ser rechazado");
        assertFalse(usuarioSinDominio.validarFormatoCorreo(), 
            "Correo sin dominio debe ser rechazado");
    }

    @Test
    public void given_ContraseñasConDiferentesLongitudes_when_ValidarLongitud_then_DebeValidarLongitudMinima() {
        // Arrange
        Usuario usuarioContraseñaExacta = new Usuario("Juan", "juan@mail.com", "12345678");
        Usuario usuarioContraseñaLarga = new Usuario("Pedro", "pedro@mail.com", "123456789");
        Usuario usuarioContraseñaCorta = new Usuario("Ana", "ana@mail.com", "1234567");
        Usuario usuarioContraseñaVacia = new Usuario("Luis", "luis@mail.com", "");
        Usuario usuarioContraseñaNull = new Usuario("María", "maria@mail.com", null);

        // Act & Assert
        assertTrue(usuarioContraseñaExacta.validarLongitudContraseña(),
            "Contraseña de 8 caracteres debe ser válida");
        assertTrue(usuarioContraseñaLarga.validarLongitudContraseña(),
            "Contraseña de más de 8 caracteres debe ser válida");
        assertFalse(usuarioContraseñaCorta.validarLongitudContraseña(),
            "Contraseña de menos de 8 caracteres debe ser inválida");
        assertFalse(usuarioContraseñaVacia.validarLongitudContraseña(),
            "Contraseña vacía debe ser inválida");
        assertFalse(usuarioContraseñaNull.validarLongitudContraseña(),
            "Contraseña null debe ser inválida");
    }

    @BeforeEach
    public void setUp() {
        Usuario.limpiarCorreosRegistrados();
    }

    @Test
    public void given_NombresConDiferentesLongitudes_when_ValidarLongitud_then_DebeValidarRangoCorrecto() {
        // Arrange
        Usuario usuarioNombreMinimo = new Usuario("Ana", "ana@mail.com", "pass123");
        Usuario usuarioNombrePromedio = new Usuario("Juan Pérez", "juan@mail.com", "pass123");
        Usuario usuarioNombreMaximo = new Usuario("N".repeat(50), "max@mail.com", "pass123");
        Usuario usuarioNombreCorto = new Usuario("Ab", "ab@mail.com", "pass123");
        Usuario usuarioNombreLargo = new Usuario("N".repeat(51), "largo@mail.com", "pass123");

        // Act & Assert
        assertTrue(usuarioNombreMinimo.validarLongitudNombre(),
            "Nombre de 3 caracteres debe ser válido");
        assertTrue(usuarioNombrePromedio.validarLongitudNombre(),
            "Nombre de 10 caracteres debe ser válido");
        assertTrue(usuarioNombreMaximo.validarLongitudNombre(),
            "Nombre de 50 caracteres debe ser válido");
        assertFalse(usuarioNombreCorto.validarLongitudNombre(),
            "Nombre de 2 caracteres debe ser inválido");
        assertFalse(usuarioNombreLargo.validarLongitudNombre(),
            "Nombre de 51 caracteres debe ser inválido");
    }

    @Test
    public void given_UsuariosConDiferentesCorreos_when_RegistrarCorreo_then_DebeValidarCorreosUnicos() {
        // Arrange
        Usuario primerUsuario = new Usuario("Juan", "juan@mail.com", "pass123");
        Usuario usuarioDuplicado = new Usuario("Pedro", "juan@mail.com", "pass123");
        Usuario usuarioMayusculas = new Usuario("Ana", "JUAN@MAIL.COM", "pass123");
        Usuario usuarioConEspacios = new Usuario("Luis", " juan@mail.com ", "pass123");
        Usuario usuarioCorreoNulo = new Usuario("María", null, "pass123");

        // Act & Assert
        assertTrue(primerUsuario.registrarCorreo(),
            "Primer correo debe registrarse exitosamente");
        assertFalse(usuarioDuplicado.registrarCorreo(),
            "Correo duplicado debe ser rechazado");
        assertFalse(usuarioMayusculas.registrarCorreo(),
            "Correo duplicado con mayúsculas debe ser rechazado");
        assertFalse(usuarioConEspacios.registrarCorreo(),
            "Correo duplicado con espacios debe ser rechazado");
        assertFalse(usuarioCorreoNulo.registrarCorreo(),
            "Correo nulo debe ser rechazado");
    }

    @Test
    public void given_CredencialesDeUsuario_when_Autenticar_then_DebeValidarCredencialesCorrectamente() {
        // Arrange
        Usuario usuarioRegistrado = new Usuario("Juan", "juan@mail.com", "pass123");
        Usuario usuarioNoRegistrado = new Usuario("Pedro", "pedro@mail.com", "pass123");
        Usuario usuarioCorreoInvalido = new Usuario("Luis", "luis", "pass123");
        Usuario usuarioCredencialesVacias = new Usuario("Ana", "", "");

        // Registrar el usuario válido
        assertTrue(usuarioRegistrado.registrarCorreo(), 
            "El usuario debería registrarse correctamente");

        // Act & Assert
        assertTrue(usuarioRegistrado.autenticar("juan@mail.com", "pass123"),
            "Usuario registrado con credenciales correctas debe autenticarse");
        assertFalse(usuarioNoRegistrado.autenticar("pedro@mail.com", "pass123"),
            "Usuario no registrado no debe autenticarse");
        assertFalse(usuarioRegistrado.autenticar("juan@mail.com", "pass456"),
            "Usuario con contraseña incorrecta no debe autenticarse");
        assertFalse(usuarioCorreoInvalido.autenticar("luis", "pass123"),
            "Usuario con correo inválido no debe autenticarse");
        assertFalse(usuarioCredencialesVacias.autenticar("", ""),
            "Usuario con credenciales vacías no debe autenticarse");
    }

    @Test
    public void given_DatosActualizados_when_ActualizarDatosUsuario_then_DebeActualizarCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario("Juan Pérez", "juan@mail.com", "pass123");
        String nuevoNombre = "Juan Carlos Pérez";
        String nuevaContraseña = "newpass456";
        String nombreInvalido = "Ab"; // Muy corto
        String contraseñaInvalida = "123"; // Muy corta
        
        // Registrar el usuario
        assertTrue(usuario.registrarCorreo(), 
            "El usuario debería registrarse correctamente");

        // Act & Assert - Actualizar nombre válido
        assertTrue(usuario.actualizarNombre(nuevoNombre),
            "Debe permitir actualizar con nombre válido");
        assertEquals(nuevoNombre, usuario.getNombre(),
            "El nombre debe actualizarse correctamente");

        // Actualizar contraseña válida
        assertTrue(usuario.actualizarContraseña(nuevaContraseña),
            "Debe permitir actualizar con contraseña válida");
        assertTrue(usuario.autenticar("juan@mail.com", nuevaContraseña),
            "Debe autenticarse con la nueva contraseña");
        assertFalse(usuario.autenticar("juan@mail.com", "pass123"),
            "No debe autenticarse con la contraseña antigua");

        // Intentar actualizar con nombre inválido
        assertFalse(usuario.actualizarNombre(nombreInvalido),
            "No debe permitir actualizar con nombre inválido");
        assertEquals(nuevoNombre, usuario.getNombre(),
            "El nombre no debe cambiar si la actualización falla");

        // Intentar actualizar con contraseña inválida
        assertFalse(usuario.actualizarContraseña(contraseñaInvalida),
            "No debe permitir actualizar con contraseña inválida");
        assertTrue(usuario.autenticar("juan@mail.com", nuevaContraseña),
            "Debe mantener la contraseña válida anterior");
    }

    @Test
    public void given_EstadoUsuario_when_ActivarDesactivarUsuario_then_DebeGestionarEstadoCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario("Juan", "juan@mail.com", "pass123");
        
        // Registrar el usuario
        assertTrue(usuario.registrarCorreo(), 
            "El usuario debería registrarse correctamente");

        // Act & Assert - Usuario debe estar activo por defecto
        assertTrue(usuario.estaActivo(),
            "Usuario recién creado debe estar activo por defecto");
        assertTrue(usuario.autenticar("juan@mail.com", "pass123"),
            "Usuario activo debe poder autenticarse");

        // Desactivar usuario
        assertTrue(usuario.desactivar(),
            "Debe permitir desactivar un usuario activo");
        assertFalse(usuario.estaActivo(),
            "Usuario debe estar inactivo después de desactivarlo");
        assertFalse(usuario.autenticar("juan@mail.com", "pass123"),
            "Usuario inactivo no debe poder autenticarse");

        // Intentar desactivar usuario ya inactivo
        assertFalse(usuario.desactivar(),
            "No debe permitir desactivar un usuario ya inactivo");

        // Activar usuario
        assertTrue(usuario.activar(),
            "Debe permitir activar un usuario inactivo");
        assertTrue(usuario.estaActivo(),
            "Usuario debe estar activo después de activarlo");
        assertTrue(usuario.autenticar("juan@mail.com", "pass123"),
            "Usuario reactivado debe poder autenticarse");

        // Intentar activar usuario ya activo
        assertFalse(usuario.activar(),
            "No debe permitir activar un usuario ya activo");
    }
}