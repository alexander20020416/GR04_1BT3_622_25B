package com.gr4.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase de utilidad para gestionar el EntityManagerFactory y EntityManager
 * Patrón Singleton para el EntityManagerFactory
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "GestorTareasPU";
    private static EntityManagerFactory emf = null;

    // Constructor privado para evitar instanciación
    private JPAUtil() {
    }

    /**
     * Obtiene el EntityManagerFactory (Singleton)
     * @return EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                System.out.println("✓ EntityManagerFactory creado exitosamente");
            } catch (Exception e) {
                System.err.println("✗ Error al crear EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("No se pudo crear el EntityManagerFactory", e);
            }
        }
        return emf;
    }

    /**
     * Crea un nuevo EntityManager
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Cierra el EntityManagerFactory
     * Debe llamarse al finalizar la aplicación
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("✓ EntityManagerFactory cerrado");
        }
    }

    /**
     * Método de utilidad para ejecutar operaciones con manejo automático de transacciones
     * @param operation Operación a ejecutar
     */
    public static void executeInTransaction(EntityManagerOperation operation) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            operation.execute(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error en la transacción", e);
        } finally {
            em.close();
        }
    }

    /**
     * Interface funcional para operaciones con EntityManager
     */
    @FunctionalInterface
    public interface EntityManagerOperation {
        void execute(EntityManager em);
    }
}