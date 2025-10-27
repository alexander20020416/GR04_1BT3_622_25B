package com.gr4.auth.config;

import com.gr4.auth.service.AuthService;
import com.gr4.auth.repository.UsuarioRepository;
import com.gr4.auth.repository.UsuarioRepositoryJPA;
import com.gr4.auth.util.UsuarioValidator;

/**
 * ServiceLocator para gestionar instancias únicas de servicios.
 * Cumple con:
 * - SRP: Solo se encarga de crear y mantener servicios
 * - Singleton Pattern: Garantiza una sola instancia del repositorio
 * - DIP: Permite inyección de dependencias
 */
public class ServiceLocator {
    
    private static ServiceLocator instance;
    private static final Object lock = new Object();
    
    // Instancias únicas compartidas
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final AuthService authService;
    
    private ServiceLocator() {
        System.out.println("🔧 ServiceLocator: Inicializando servicios únicos...");
        
        // Crear instancias únicas - Usando JPA para persistencia real
        this.usuarioRepository = new UsuarioRepositoryJPA();
        this.usuarioValidator = new UsuarioValidator();
        this.authService = new AuthService(usuarioRepository, usuarioValidator);
        
        System.out.println("✅ ServiceLocator: Servicios inicializados con JPA/H2");
    }
    
    /**
     * Obtiene la instancia única del ServiceLocator (Singleton)
     */
    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ServiceLocator();
                }
            }
        }
        return instance;
    }
    
    /**
     * Obtiene la instancia única del AuthService
     * Garantiza que todos los servlets usen el mismo repositorio
     */
    public AuthService getAuthService() {
        return authService;
    }
    
    /**
     * Obtiene la instancia única del UsuarioRepository
     */
    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
    
    /**
     * Obtiene la instancia única del UsuarioValidator
     */
    public UsuarioValidator getUsuarioValidator() {
        return usuarioValidator;
    }
    
    /**
     * Método para testing - permite limpiar datos
     */
    public void limpiarDatos() {
        usuarioRepository.limpiarCorreos();
        System.out.println("🧹 ServiceLocator: Datos limpiados");
    }
}