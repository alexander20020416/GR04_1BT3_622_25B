package com.gr4.auth.repository;

import com.gr4.auth.model.Usuario;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementación del Repository de usuarios.
 * Usa un HashMap en memoria para almacenar usuarios por correo.
 * En una aplicación real, esto se conectaría a una base de datos.
 */
public class UsuarioRepositoryImpl implements UsuarioRepository {
    
    // Mapa que almacena usuarios por correo (simulando una base de datos)
    private static Map<String, Usuario> usuariosPorCorreo = new HashMap<>();

    @Override
    public boolean registrarCorreo(Usuario usuario) {
        if (usuario == null || usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            return false;
        }
        
        String correoNormalizado = usuario.getCorreo().trim().toLowerCase();
        
        if (usuariosPorCorreo.containsKey(correoNormalizado)) {
            return false;
        }
        
        usuariosPorCorreo.put(correoNormalizado, usuario);
        return true;
    }

    @Override
    public boolean existeCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        String correoNormalizado = correo.trim().toLowerCase();
        return usuariosPorCorreo.containsKey(correoNormalizado);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return null;
        }
        String correoNormalizado = correo.trim().toLowerCase();
        return usuariosPorCorreo.get(correoNormalizado);
    }

    @Override
    public void limpiarCorreos() {
        usuariosPorCorreo.clear();
    }
}
