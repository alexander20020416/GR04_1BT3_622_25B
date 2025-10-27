package com.gr4.auth.repository;

import com.gr4.auth.model.Usuario;

/**
 * Interfaz Repository para gestionar la persistencia de usuarios.
 * Cumple con el patrón Repository y el principio de Inversión de Dependencias (DIP).
 */
public interface UsuarioRepository {
    
    /**
     * Registra un nuevo correo de usuario
     * @param usuario el usuario cuyo correo se va a registrar
     * @return true si el correo se registró exitosamente, false si ya existe
     */
    boolean registrarCorreo(Usuario usuario);
    
    /**
     * Verifica si un correo ya está registrado
     * @param correo el correo a verificar
     * @return true si el correo está registrado, false en caso contrario
     */
    boolean existeCorreo(String correo);
    
    /**
     * Busca un usuario por su correo electrónico
     * @param correo el correo del usuario a buscar
     * @return el usuario encontrado, o null si no existe
     */
    Usuario buscarPorCorreo(String correo);
    
    /**
     * Limpia todos los correos registrados (útil para testing)
     */
    void limpiarCorreos();
    
    /**
     * Obtiene el número total de usuarios registrados
     * @return cantidad de usuarios en el repositorio
     */
    int obtenerTotalUsuarios();
    
    /**
     * Elimina el token "Remember Me" de la base de datos
     * Este método se invoca durante el cierre de sesión (CA3)
     *
     * @param token Token a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminarTokenRememberMe(String token);
}
