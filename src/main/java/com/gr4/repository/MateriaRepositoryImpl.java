package com.gr4.repository;

import com.gr4.model.Materia;
import com.gr4.util.JPAUtil;

import javax.persistence.*;
import java.util.List;

public class MateriaRepositoryImpl implements MateriaRepository {


    @Override
    public Materia save(Materia materia) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(materia);
            tx.commit();
            return materia;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Materia> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Materia m", Materia.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Materia findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }
}

