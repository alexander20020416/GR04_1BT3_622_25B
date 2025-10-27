package com.gr4.auth.service;


import com.gr4.auth.model.Usuario;
import com.gr4.auth.repository.UsuarioRepository;
import com.gr4.auth.util.UsuarioValidator;

/**
 * Servicio de autenticación que maneja la lógica de negocio relacionada con usuarios.
 * Cumple con el SRP - Solo se encarga de operaciones de autenticación y gestión de usuarios.
 * Cumple con DIP - Depende de abstracciones (interfaces), no de implementaciones concretas.
 */
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator validator;

    /**
     * Constructor que recibe dependencias inyectadas (DIP)
     * @param usuarioRepository repositorio para persistencia de usuarios
     * @param validator validador de usuarios
     */
    public AuthService(UsuarioRepository usuarioRepository, UsuarioValidator validator) {
        if (usuarioRepository == null || validator == null) {
            throw new IllegalArgumentException("Las dependencias no pueden ser null");
        }
        this.usuarioRepository = usuarioRepository;
        this.validator = validator;
        System.out.println("🔧 AuthService: Inicializado con dependencias inyectadas");
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario el usuario a registrar
     * @return true si el usuario se registró exitosamente, false si ya existe o es inválido
     */
    public boolean registrarUsuario(Usuario usuario) {
        System.out.println("🔍 AuthService: Iniciando registro de usuario: " + 
                          (usuario != null ? usuario.getCorreo() : "NULL"));
        
        if (usuario == null) {
            System.err.println("✗ AuthService: Usuario es null");
            return false;
        }
        
        System.out.println("🔍 AuthService: Validando usuario: " + usuario.getCorreo());
        System.out.println("🔍 AuthService: Datos del usuario - Nombre: " + usuario.getNombre() + 
                          ", Correo: " + usuario.getCorreo() + 
                          ", Activo: " + usuario.isActivo());
        
        if (!validator.validarUsuario(usuario)) {
            System.err.println("✗ AuthService: Validación falló para usuario: " + usuario.getCorreo());
            return false;
        }
        
        System.out.println("✓ AuthService: Validación exitosa, verificando si ya existe...");
        
        // Verificar si el correo ya existe
        if (usuarioRepository.existeCorreo(usuario.getCorreo())) {
            System.err.println("✗ AuthService: El correo ya está registrado: " + usuario.getCorreo());
            return false;
        }
        
        System.out.println("✓ AuthService: Correo disponible, guardando en repositorio...");
        boolean resultado = usuarioRepository.registrarCorreo(usuario);
        
        if (resultado) {
            System.out.println("✅ AuthService: Usuario guardado exitosamente en repositorio");
            // Verificar que realmente se guardó
            Usuario verificacion = usuarioRepository.buscarPorCorreo(usuario.getCorreo());
            if (verificacion != null) {
                System.out.println("✅ AuthService: Verificación exitosa - Usuario encontrado después del registro");
            } else {
                System.err.println("⚠️ AuthService: ADVERTENCIA - Usuario no se encuentra después del registro");
            }
        } else {
            System.err.println("✗ AuthService: Falló al guardar usuario en repositorio");
        }
        
        return resultado;
    }

    /**
     * Verifica si un correo ya está registrado
     * @param correo el correo a verificar
     * @return true si el correo está registrado, false en caso contrario
     */
    public boolean correoEstaRegistrado(String correo) {
        return usuarioRepository.existeCorreo(correo);
    }

    /**
     * Autentica al usuario verificando sus credenciales
     * @param correo el correo electrónico ingresado
     * @param contraseña la contraseña ingresada
     * @return el Usuario autenticado si las credenciales son correctas, null en caso contrario
     */
    public Usuario autenticar(String correo, String contraseña) {
        System.out.println("🔍 AuthService: Intentando autenticar usuario: " + correo);
        
        if (correo == null || contraseña == null || 
            correo.trim().isEmpty() || contraseña.trim().isEmpty()) {
            System.err.println("✗ AuthService: Credenciales vacías");
            return null;
        }
        
        Usuario usuario = usuarioRepository.buscarPorCorreo(correo);
        System.out.println("🔍 AuthService: Usuario encontrado en repositorio: " + (usuario != null));
        
        if (usuario == null) {
            System.err.println("✗ AuthService: Usuario no encontrado para correo: " + correo);
            return null;
        }
        
        // Verificar que el usuario esté activo
        if (!usuario.isActivo()) {
            System.err.println("✗ AuthService: Usuario inactivo: " + correo);
            return null;
        }

        // Verificar que la contraseña coincida
        if (usuario.getContraseña().equals(contraseña)) {
            System.out.println("✓ AuthService: Autenticación exitosa para: " + correo);
            return usuario;
        }
        
        System.err.println("✗ AuthService: Contraseña incorrecta para: " + correo);
        return null;
    }

    /**
     * Actualiza el nombre del usuario si cumple con las validaciones
     * @param usuario el usuario a actualizar
     * @param nuevoNombre el nuevo nombre a establecer
     * @return true si el nombre se actualizó correctamente, false si no cumple validaciones
     */
    public boolean actualizarNombre(Usuario usuario, String nuevoNombre) {
        if (usuario == null || !validator.validarLongitudNombre(nuevoNombre)) {
            return false;
        }
        usuario.setNombre(nuevoNombre);
        return true;
    }

    /**
     * Actualiza la contraseña del usuario si cumple con las validaciones
     * @param usuario el usuario a actualizar
     * @param nuevaContraseña la nueva contraseña a establecer
     * @return true si la contraseña se actualizó correctamente, false si no cumple validaciones
     */
    public boolean actualizarContraseña(Usuario usuario, String nuevaContraseña) {
        if (usuario == null || !validator.validarLongitudContraseña(nuevaContraseña)) {
            return false;
        }
        usuario.setContraseña(nuevaContraseña);
        return true;
    }

    /**
     * Desactiva el usuario si está activo
     * @param usuario el usuario a desactivar
     * @return true si el usuario se desactivó correctamente, false si ya estaba inactivo
     */
    public boolean desactivar(Usuario usuario) {
        if (usuario == null || !usuario.isActivo()) {
            return false;
        }
        usuario.setActivo(false);
        return true;
    }

    /**
     * Activa el usuario si está inactivo
     * @param usuario el usuario a activar
     * @return true si el usuario se activó correctamente, false si ya estaba activo
     */
    public boolean activar(Usuario usuario) {
        if (usuario == null || usuario.isActivo()) {
            return false;
        }
        usuario.setActivo(true);
        return true;
    }
    
    /**
     * Limpia todos los usuarios registrados (usado en pruebas)
     */
    public void limpiarUsuarios() {
        System.out.println("🧹 AuthService: Limpiando todos los usuarios");
        usuarioRepository.limpiarCorreos();
        System.out.println("✓ AuthService: Usuarios limpiados");
    }
    
    /**
     * Obtiene el número total de usuarios registrados
     * @return cantidad de usuarios en el sistema
     */
    public int obtenerTotalUsuarios() {
        int total = usuarioRepository.obtenerTotalUsuarios();
        System.out.println("📊 AuthService: Total de usuarios consultado: " + total);
        return total;
    }
}

