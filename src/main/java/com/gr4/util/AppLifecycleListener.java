package com.gr4.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No-op (JPAUtil creará el EMF bajo demanda)
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cerrar EntityManagerFactory para evitar hilos y fugas
        try {
            JPAUtil.closeEntityManagerFactory();
        } catch (Exception e) {
            System.err.println("Error cerrando EMF: " + e.getMessage());
        }
    }
}
