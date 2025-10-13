package com.gr4.repository;

import com.gr4.model.Actividad;
import com.gr4.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del Repository para Actividad
 * Maneja la persistencia usando JPA/Hibernate
 */
public class ActividadRepositoryImpl implements ActividadRepository {

    @Override
    public Actividad save(Actividad actividad) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (actividad.getId() == null) {
                // Nueva actividad
                em.persist(actividad);
            } else {
                // Actualizar actividad existente
                actividad = em.merge(actividad);
            }

            em.getTransaction().commit();
            return actividad;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar actividad: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Actividad> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Actividad actividad = em.find(Actividad.class, id);
            return Optional.ofNullable(actividad);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Actividad> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Actividad> query = em.createQuery(
                    "SELECT a FROM Actividad a ORDER BY a.fechaEntrega ASC",
                    Actividad.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Actividad> findByEstado(String estado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Actividad> query = em.createQuery(
                    "SELECT a FROM Actividad a WHERE a.estado = :estado ORDER BY a.fechaEntrega ASC",
                    Actividad.class
            );
            query.setParameter("estado", estado);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Actividad> findByPrioridad(String prioridad) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Actividad> query = em.createQuery(
                    "SELECT a FROM Actividad a WHERE a.prioridad = :prioridad ORDER BY a.fechaEntrega ASC",
                    Actividad.class
            );
            query.setParameter("prioridad", prioridad);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Actividad actividad = em.find(Actividad.class, id);
            if (actividad == null) {
                tx.rollback();
                return false;
            }

            em.remove(actividad);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar actividad: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }


    @Override
    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(a) FROM Actividad a",
                    Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}