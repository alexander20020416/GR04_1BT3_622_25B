package com.gr4.auth.repository;

import com.gr4.auth.model.Usuario;
import com.gr4.auth.model.UsuarioEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Implementación JPA del repositorio de usuarios.
 * Cumple con:
 * - SRP: Solo se encarga de la persistencia JPA de usuarios
 * - LSP: Es intercambiable con UsuarioRepositoryImpl
 * - DIP: Implementa la interfaz UsuarioRepository
 */
public class UsuarioRepositoryJPA implements UsuarioRepository {
    
    private EntityManagerFactory emf;
    
    public UsuarioRepositoryJPA() {
        try {
            this.emf = Persistence.createEntityManagerFactory("GestorTareasPU");
            System.out.println("✅ UsuarioRepositoryJPA: EntityManagerFactory creado exitosamente");
        } catch (Exception e) {
            System.err.println("❌ UsuarioRepositoryJPA: Error al crear EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo inicializar JPA", e);
        }
    }
    
    @Override
    public boolean registrarCorreo(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            System.out.println("🔍 UsuarioRepositoryJPA: Iniciando registro de " + usuario.getCorreo());
            System.out.println("🔍 UsuarioRepositoryJPA: Datos del usuario - Nombre: " + usuario.getNombre() + 
                              ", Correo: " + usuario.getCorreo() + ", Activo: " + usuario.isActivo());
            
            // Verificar si ya existe
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM UsuarioEntity u WHERE u.correo = :correo", Long.class);
            query.setParameter("correo", usuario.getCorreo());
            Long count = query.getSingleResult();
            
            if (count > 0) {
                System.err.println("✗ UsuarioRepositoryJPA: El correo ya existe: " + usuario.getCorreo());
                tx.rollback();
                return false;
            }
            
            // Crear y persistir entidad
            UsuarioEntity entity = UsuarioEntity.fromUsuario(usuario);
            em.persist(entity);
            
            tx.commit();
            
            System.out.println("✅ UsuarioRepositoryJPA: Usuario guardado con ID: " + entity.getId());
            return true;
            
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("❌ UsuarioRepositoryJPA: Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
            
        } finally {
            em.close();
        }
    }
    
    @Override
    public boolean existeCorreo(String correo) {
        EntityManager em = emf.createEntityManager();
        
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM UsuarioEntity u WHERE u.correo = :correo", Long.class);
            query.setParameter("correo", correo);
            Long count = query.getSingleResult();
            
            boolean existe = count > 0;
            System.out.println("🔍 UsuarioRepositoryJPA: ¿Existe " + correo + "? " + existe);
            return existe;
            
        } catch (Exception e) {
            System.err.println("❌ UsuarioRepositoryJPA: Error al verificar correo: " + e.getMessage());
            return false;
            
        } finally {
            em.close();
        }
    }
    
    @Override
    public Usuario buscarPorCorreo(String correo) {
        EntityManager em = emf.createEntityManager();
        
        try {
            System.out.println("🔍 UsuarioRepositoryJPA: Buscando usuario por correo: " + correo);
            
            TypedQuery<UsuarioEntity> query = em.createQuery(
                "SELECT u FROM UsuarioEntity u WHERE u.correo = :correo", UsuarioEntity.class);
            query.setParameter("correo", correo);
            
            UsuarioEntity entity = query.getResultList().stream().findFirst().orElse(null);
            
            if (entity != null) {
                // Actualizar último acceso
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                entity.setUltimoAcceso(LocalDateTime.now());
                em.merge(entity);
                tx.commit();
                
                Usuario usuario = entity.toUsuario();
                System.out.println("✅ UsuarioRepositoryJPA: Usuario encontrado: " + correo);
                return usuario;
            } else {
                System.out.println("ℹ️ UsuarioRepositoryJPA: Usuario no encontrado: " + correo);
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("❌ UsuarioRepositoryJPA: Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
            
        } finally {
            em.close();
        }
    }
    
    @Override
    public void limpiarCorreos() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            int deletedCount = em.createQuery("DELETE FROM UsuarioEntity").executeUpdate();
            
            tx.commit();
            
            System.out.println("🧹 UsuarioRepositoryJPA: " + deletedCount + " usuarios eliminados");
            
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("❌ UsuarioRepositoryJPA: Error al limpiar usuarios: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            em.close();
        }
    }
    
    @Override
    public int obtenerTotalUsuarios() {
        EntityManager em = emf.createEntityManager();
        
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM UsuarioEntity u", Long.class);
            Long count = query.getSingleResult();
            
            int total = count.intValue();
            System.out.println("📊 UsuarioRepositoryJPA: Total de usuarios: " + total);
            return total;
            
        } catch (Exception e) {
            System.err.println("❌ UsuarioRepositoryJPA: Error al contar usuarios: " + e.getMessage());
            return 0;
            
        } finally {
            em.close();
        }
    }
    
    @Override
    public boolean eliminarTokenRememberMe(String token) {
        // Implementación simple para tokens remember me
        // En una aplicación real, esto manejaría una tabla separada de tokens
        System.out.println("🔍 UsuarioRepositoryJPA: Eliminando token remember me");
        return token != null && !token.isEmpty();
    }
    
    /**
     * Cierra el EntityManagerFactory cuando ya no se necesite
     */
    public void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("🔒 UsuarioRepositoryJPA: EntityManagerFactory cerrado");
        }
    }
}