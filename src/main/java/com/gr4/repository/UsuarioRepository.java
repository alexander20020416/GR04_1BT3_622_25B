package com.gr4.repository;

/**
 * Interface del repositorio de usuarios
 * Define las operaciones de persistencia para la entidad Usuario
 */
public interface UsuarioRepository {

    /**
     * Elimina el token "Remember Me" de la base de datos
     * Este método se invoca durante el cierre de sesión (CA3)
     *
     * @param token Token a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminarTokenRememberMe(String token);
}
