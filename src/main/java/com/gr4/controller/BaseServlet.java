package com.gr4.controller;

import com.gr4.repository.ActividadRepository;
import com.gr4.repository.ActividadRepositoryImpl;
import com.gr4.repository.TareaRepository;
import com.gr4.repository.TareaRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Clase base para todos los Servlets
 * Centraliza la inicialización de repositorios comunes
 */
public abstract class BaseServlet extends HttpServlet {

    protected TareaRepository tareaRepository;
    protected ActividadRepository actividadRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        inicializarRepositorios();
        mostrarInicializacion();
    }

    /**
     * Inicializa los repositorios comunes
     */
    protected void inicializarRepositorios() {
        this.tareaRepository = new TareaRepositoryImpl();
        this.actividadRepository = new ActividadRepositoryImpl();
    }

    /**
     * Muestra mensaje de inicialización (puede ser sobrescrito por subclases)
     */
    protected void mostrarInicializacion() {
        System.out.println("✔ " + this.getClass().getSimpleName() + " inicializado");
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("✔ " + this.getClass().getSimpleName() + " destruido");
    }
}