package com.gr4.repository;

import com.gr4.model.Proyecto;
import com.gr4.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ProyectoRepositoryImpl implements ProyectoRepository {

    @Override
    public Proyecto save(Proyecto proyecto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (proyecto.getId() == null) {
                em.persist(proyecto);
            } else {
                proyecto = em.merge(proyecto);
            }

            em.getTransaction().commit();
            return proyecto;
        } catch (Exception e) {
            rollbackIfActive(em);
            throw new RuntimeException("Error al guardar proyecto: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Proyecto> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Proyecto proyecto = em.find(Proyecto.class, id);
            return Optional.ofNullable(proyecto);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Proyecto> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Proyecto> query = em.createQuery(
                    "SELECT p FROM Proyecto p",
                    Proyecto.class
            );
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

            Proyecto proyecto = em.find(Proyecto.class, id);
            if (proyecto != null) {
                em.remove(proyecto);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            rollbackIfActive(em);
            throw new RuntimeException("Error al eliminar proyecto: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    private void rollbackIfActive(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}