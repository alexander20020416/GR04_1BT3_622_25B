package com.gr4.repository;

import com.gr4.model.Tarea;
import com.gr4.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del Repository para Tarea
 * Maneja la persistencia usando JPA/Hibernate
 */
public class TareaRepositoryImpl implements TareaRepository {

    @Override
    public Tarea save(Tarea tarea) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (tarea.getId() == null) {
                em.persist(tarea);
            } else {
                tarea = em.merge(tarea);
            }

            em.getTransaction().commit();
            return tarea;
        } catch (Exception e) {
            rollbackIfActive(em);
            throw new RuntimeException("Error al guardar tarea: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Tarea tarea = em.find(Tarea.class, id);
            return Optional.ofNullable(tarea);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tarea> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Tarea> query = em.createQuery(
                    "SELECT t FROM Tarea t ORDER BY t.fechaVencimiento ASC",
                    Tarea.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tarea> findByEstado(String estado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Tarea> query = em.createQuery(
                    "SELECT t FROM Tarea t WHERE t.estado = :estado ORDER BY t.fechaVencimiento ASC",
                    Tarea.class
            );
            query.setParameter("estado", estado);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tarea> findByPrioridad(String prioridad) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Tarea> query = em.createQuery(
                    "SELECT t FROM Tarea t WHERE t.prioridad = :prioridad ORDER BY t.fechaVencimiento ASC",
                    Tarea.class
            );
            query.setParameter("prioridad", prioridad);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tarea> findByActividadId(Long actividadId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Tarea> query = em.createQuery(
                    "SELECT t FROM Tarea t WHERE t.actividad.id = :actividadId ORDER BY t.fechaVencimiento ASC",
                    Tarea.class
            );
            query.setParameter("actividadId", actividadId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Tarea tarea = em.find(Tarea.class, id);
            if (tarea != null) {
                em.remove(tarea);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            rollbackIfActive(em);
            throw new RuntimeException("Error al eliminar tarea: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(t) FROM Tarea t",
                    Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Realiza rollback de la transacción si está activa
     * @param em EntityManager con la transacción a verificar
     */
    private void rollbackIfActive(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}