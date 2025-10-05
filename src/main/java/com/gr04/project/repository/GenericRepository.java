package com.gr04.project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class GenericRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPU");

    protected EntityManager em() {
        return emf.createEntityManager();
    }
}
