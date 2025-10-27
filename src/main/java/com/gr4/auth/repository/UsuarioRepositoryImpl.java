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
        System.out.println("🔍 UsuarioRepository: Intentando registrar usuario: " + usuario.getCorreo());
        
        if (usuario == null || usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            System.err.println("✗ UsuarioRepository: Usuario o correo nulo/vacío");
            return false;
        }
        
        String correoNormalizado = usuario.getCorreo().trim().toLowerCase();
        System.out.println("🔍 UsuarioRepository: Correo normalizado: " + correoNormalizado);
        
        if (usuariosPorCorreo.containsKey(correoNormalizado)) {
            System.err.println("✗ UsuarioRepository: Correo ya existe: " + correoNormalizado);
            return false;
        }
        
        usuariosPorCorreo.put(correoNormalizado, usuario);
        System.out.println("✓ UsuarioRepository: Usuario guardado exitosamente. Total usuarios: " + usuariosPorCorreo.size());
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
        System.out.println("🔍 UsuarioRepository: Buscando usuario por correo: " + correo);
        
        if (correo == null || correo.trim().isEmpty()) {
            System.err.println("✗ UsuarioRepository: Correo nulo o vacío en búsqueda");
            return null;
        }
        String correoNormalizado = correo.trim().toLowerCase();
        Usuario usuario = usuariosPorCorreo.get(correoNormalizado);
        
        System.out.println("🔍 UsuarioRepository: Usuario encontrado: " + (usuario != null));
        System.out.println("🔍 UsuarioRepository: Total usuarios en memoria: " + usuariosPorCorreo.size());
        
        return usuario;
    }

    @Override
    public void limpiarCorreos() {
        System.out.println("🧹 UsuarioRepository: Limpiando todos los usuarios registrados");
        usuariosPorCorreo.clear();
        System.out.println("✓ UsuarioRepository: Usuarios limpiados. Total actual: " + usuariosPorCorreo.size());
    }
    
    @Override
    public int obtenerTotalUsuarios() {
        int total = usuariosPorCorreo.size();
        System.out.println("📊 UsuarioRepository: Total de usuarios: " + total);
        return total;
    }
    
    @Override
    public boolean eliminarTokenRememberMe(String token) {
        // Implementación simple para tokens remember me
        // En una aplicación real, esto se conectaría a una base de datos
        return token != null && !token.isEmpty();
    }
}
