package com.gr4.auth.model;

import com.gr4.auth.util.UsuarioValidator;
import com.gr4.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    private UsuarioValidator validator;
    private AuthService authService;
    
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
        String correoValido = "usuario@gmail.com";
        String correoSinArroba = "usuariogmail.com";
        String correoVacio = "";
        String correoNull = null;
        String correoSinDominio = "usuario@";

        // Act & Assert
        assertTrue(validator.validarFormatoCorreo(correoValido), 
            "Correo con formato válido debe ser aceptado");
        assertFalse(validator.validarFormatoCorreo(correoSinArroba), 
            "Correo sin @ debe ser rechazado");
        assertFalse(validator.validarFormatoCorreo(correoVacio), 
            "Correo vacío debe ser rechazado");
        assertFalse(validator.validarFormatoCorreo(correoNull), 
            "Correo null debe ser rechazado");
        assertFalse(validator.validarFormatoCorreo(correoSinDominio), 
            "Correo sin dominio debe ser rechazado");
    }

    @Test
    public void given_ContraseñasConDiferentesLongitudes_when_ValidarLongitud_then_DebeValidarLongitudMinima() {
        // Arrange
        String contraseñaExacta = "12345678";
        String contraseñaLarga = "123456789";
        String contraseñaCorta = "1234567";
        String contraseñaVacia = "";
        String contraseñaNull = null;

        // Act & Assert
        assertTrue(validator.validarLongitudContraseña(contraseñaExacta),
            "Contraseña de 8 caracteres debe ser válida");
        assertTrue(validator.validarLongitudContraseña(contraseñaLarga),
            "Contraseña de más de 8 caracteres debe ser válida");
        assertFalse(validator.validarLongitudContraseña(contraseñaCorta),
            "Contraseña de menos de 8 caracteres debe ser inválida");
        assertFalse(validator.validarLongitudContraseña(contraseñaVacia),
            "Contraseña vacía debe ser inválida");
        assertFalse(validator.validarLongitudContraseña(contraseñaNull),
            "Contraseña null debe ser inválida");
    }

    @BeforeEach
    public void setUp() {
        validator = new UsuarioValidator();
        authService = new AuthService();
        authService.limpiarUsuarios();
    }

    @Test
    public void given_NombresConDiferentesLongitudes_when_ValidarLongitud_then_DebeValidarRangoCorrecto() {
        // Arrange
        String nombreMinimo = "Ana";
        String nombrePromedio = "Juan Pérez";
        String nombreMaximo = "N".repeat(50);
        String nombreCorto = "Ab";
        String nombreLargo = "N".repeat(51);

        // Act & Assert
        assertTrue(validator.validarLongitudNombre(nombreMinimo),
            "Nombre de 3 caracteres debe ser válido");
        assertTrue(validator.validarLongitudNombre(nombrePromedio),
            "Nombre de 10 caracteres debe ser válido");
        assertTrue(validator.validarLongitudNombre(nombreMaximo),
            "Nombre de 50 caracteres debe ser válido");
        assertFalse(validator.validarLongitudNombre(nombreCorto),
            "Nombre de 2 caracteres debe ser inválido");
        assertFalse(validator.validarLongitudNombre(nombreLargo),
            "Nombre de 51 caracteres debe ser inválido");
    }

    @Test
    public void given_UsuariosConDiferentesCorreos_when_RegistrarCorreo_then_DebeValidarCorreosUnicos() {
        // Arrange
        Usuario primerUsuario = new Usuario("Juan", "juan@mail.com", "password123");
        Usuario usuarioDuplicado = new Usuario("Pedro", "juan@mail.com", "password123");
        Usuario usuarioMayusculas = new Usuario("Ana", "JUAN@MAIL.COM", "password123");
        Usuario usuarioConEspacios = new Usuario("Luis", " juan@mail.com ", "password123");
        Usuario usuarioCorreoNulo = new Usuario("María", null, "password123");

        // Act & Assert
        assertTrue(authService.registrarUsuario(primerUsuario),
            "Primer correo debe registrarse exitosamente");
        assertFalse(authService.registrarUsuario(usuarioDuplicado),
            "Correo duplicado debe ser rechazado");
        assertFalse(authService.registrarUsuario(usuarioMayusculas),
            "Correo duplicado con mayúsculas debe ser rechazado");
        assertFalse(authService.registrarUsuario(usuarioConEspacios),
            "Correo duplicado con espacios debe ser rechazado");
        assertFalse(authService.registrarUsuario(usuarioCorreoNulo),
            "Correo nulo debe ser rechazado");
    }

    @Test
    public void given_CredencialesDeUsuario_when_Autenticar_then_DebeValidarCredencialesCorrectamente() {
        // Arrange
        Usuario usuarioRegistrado = new Usuario("Juan", "juan@mail.com", "password123");
        Usuario usuarioNoRegistrado = new Usuario("Pedro", "pedro@mail.com", "password123");

        // Registrar el usuario válido
        assertTrue(authService.registrarUsuario(usuarioRegistrado), 
            "El usuario debería registrarse correctamente");

        // Act & Assert
        assertNotNull(authService.autenticar("juan@mail.com", "password123"),
            "Usuario registrado con credenciales correctas debe autenticarse");
        assertNull(authService.autenticar("pedro@mail.com", "password123"),
            "Usuario no registrado no debe autenticarse");
        assertNull(authService.autenticar("juan@mail.com", "pass456789"),
            "Usuario con contraseña incorrecta no debe autenticarse");
        assertNull(authService.autenticar("", ""),
            "Usuario con credenciales vacías no debe autenticarse");
    }

    @Test
    public void given_DatosActualizados_when_ActualizarDatosUsuario_then_DebeActualizarCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario("Juan Pérez", "juan@mail.com", "password123");
        String nuevoNombre = "Juan Carlos Pérez";
        String nuevaContraseña = "newpass456";
        String nombreInvalido = "Ab"; // Muy corto
        String contraseñaInvalida = "123"; // Muy corta
        
        // Registrar el usuario
        assertTrue(authService.registrarUsuario(usuario), 
            "El usuario debería registrarse correctamente");

        // Act & Assert - Actualizar nombre válido
        assertTrue(authService.actualizarNombre(usuario, nuevoNombre),
            "Debe permitir actualizar con nombre válido");
        assertEquals(nuevoNombre, usuario.getNombre(),
            "El nombre debe actualizarse correctamente");

        // Actualizar contraseña válida
        assertTrue(authService.actualizarContraseña(usuario, nuevaContraseña),
            "Debe permitir actualizar con contraseña válida");
        assertNotNull(authService.autenticar("juan@mail.com", nuevaContraseña),
            "Debe autenticarse con la nueva contraseña");
        assertNull(authService.autenticar("juan@mail.com", "password123"),
            "No debe autenticarse con la contraseña antigua");

        // Intentar actualizar con nombre inválido
        assertFalse(authService.actualizarNombre(usuario, nombreInvalido),
            "No debe permitir actualizar con nombre inválido");
        assertEquals(nuevoNombre, usuario.getNombre(),
            "El nombre no debe cambiar si la actualización falla");

        // Intentar actualizar con contraseña inválida
        assertFalse(authService.actualizarContraseña(usuario, contraseñaInvalida),
            "No debe permitir actualizar con contraseña inválida");
        assertNotNull(authService.autenticar("juan@mail.com", nuevaContraseña),
            "Debe mantener la contraseña válida anterior");
    }

    @Test
    public void given_EstadoUsuario_when_ActivarDesactivarUsuario_then_DebeGestionarEstadoCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario("Juan", "juan@mail.com", "password123");
        
        // Registrar el usuario
        assertTrue(authService.registrarUsuario(usuario), 
            "El usuario debería registrarse correctamente");

        // Act & Assert - Usuario debe estar activo por defecto
        assertTrue(usuario.isActivo(),
            "Usuario recién creado debe estar activo por defecto");
        assertNotNull(authService.autenticar("juan@mail.com", "password123"),
            "Usuario activo debe poder autenticarse");

        // Desactivar usuario
        assertTrue(authService.desactivar(usuario),
            "Debe permitir desactivar un usuario activo");
        assertFalse(usuario.isActivo(),
            "Usuario debe estar inactivo después de desactivarlo");
        assertNull(authService.autenticar("juan@mail.com", "password123"),
            "Usuario inactivo no debe poder autenticarse");

        // Intentar desactivar usuario ya inactivo
        assertFalse(authService.desactivar(usuario),
            "No debe permitir desactivar un usuario ya inactivo");

        // Activar usuario
        assertTrue(authService.activar(usuario),
            "Debe permitir activar un usuario inactivo");
        assertTrue(usuario.isActivo(),
            "Usuario debe estar activo después de activarlo");
        assertNotNull(authService.autenticar("juan@mail.com", "password123"),
            "Usuario reactivado debe poder autenticarse");

        // Intentar activar usuario ya activo
        assertFalse(authService.activar(usuario),
            "No debe permitir activar un usuario ya activo");
    }
}