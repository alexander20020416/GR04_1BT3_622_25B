package com.gr4.auth.service;


import com.gr4.auth.model.Usuario;
import com.gr4.auth.repository.UsuarioRepository;
import com.gr4.auth.repository.UsuarioRepositoryImpl;
import com.gr4.auth.util.UsuarioValidator;

/**
 * Servicio de autenticación que maneja la lógica de negocio relacionada con usuarios.
 * Cumple con el SRP - Solo se encarga de operaciones de autenticación y gestión de usuarios.
 */
public class AuthService {
    
    private UsuarioRepository usuarioRepository;
    private UsuarioValidator validator;

    public AuthService() {
        this.usuarioRepository = new UsuarioRepositoryImpl();
        this.validator = new UsuarioValidator();
    }

    public AuthService(UsuarioRepository usuarioRepository, UsuarioValidator validator) {
        this.usuarioRepository = usuarioRepository;
        this.validator = validator;
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario el usuario a registrar
     * @return true si el usuario se registró exitosamente, false si ya existe o es inválido
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (!validator.validarUsuario(usuario)) {
            return false;
        }
        
        return usuarioRepository.registrarCorreo(usuario);
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
        if (correo == null || contraseña == null || 
            correo.trim().isEmpty() || contraseña.trim().isEmpty()) {
            return null;
        }
        
        Usuario usuario = usuarioRepository.buscarPorCorreo(correo);
        
        if (usuario == null) {
            return null;
        }
        
        // Verificar que el usuario esté activo
        if (!usuario.isActivo()) {
            return null;
        }

        // Verificar que la contraseña coincida
        if (usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }
        
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
        usuarioRepository.limpiarCorreos();
    }
}

