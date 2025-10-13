package com.gr4.repository;

import com.gr4.model.Alerta;
import com.gr4.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del Repository para Alerta
 * Maneja la persistencia usando JPA/Hibernate
 */
public class AlertaRepositoryImpl implements AlertaRepository {

    @Override
    public Alerta save(Alerta alerta) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (alerta.getId() == null) {
                em.persist(alerta);
            } else {
                alerta = em.merge(alerta);
            }

            em.getTransaction().commit();
            return alerta;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al guardar alerta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Alerta> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Alerta alerta = em.find(Alerta.class, id);
            return Optional.ofNullable(alerta);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Alerta> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Alerta> query = em.createQuery(
                    "SELECT a FROM Alerta a ORDER BY a.fechaAlerta DESC",
                    Alerta.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Alerta> findByActividadId(Long actividadId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Alerta> query = em.createQuery(
                    "SELECT a FROM Alerta a WHERE a.actividad.id = :actividadId ORDER BY a.fechaAlerta DESC",
                    Alerta.class
            );
            query.setParameter("actividadId", actividadId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Alerta> findActivas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Alerta> query = em.createQuery(
                    "SELECT a FROM Alerta a WHERE a.activa = true ORDER BY a.fechaAlerta ASC",
                    Alerta.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Alerta> findByTipo(String tipo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Alerta> query = em.createQuery(
                    "SELECT a FROM Alerta a WHERE a.tipo = :tipo ORDER BY a.fechaAlerta DESC",
                    Alerta.class
            );
            query.setParameter("tipo", tipo);
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

            Alerta alerta = em.find(Alerta.class, id);
            if (alerta != null) {
                em.remove(alerta);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar alerta: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(a) FROM Alerta a",
                    Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}