package com.gr4.observer;

import com.gr4.model.Alerta;

/**
 * Interface del Patrón Observer para escuchar eventos de alertas
 * Basado en el diagrama de clases del Incremento 2
 * Permite notificar cuando se crean, modifican o activan alertas
 */
public interface AlertaListener {

    /**
     * Se invoca cuando se crea una nueva alerta
     * @param alerta La alerta que fue creada
     */
    void onAlertaCreada(Alerta alerta);

    /**
     * Se invoca cuando una alerta es activada/disparada
     * @param alerta La alerta que fue activada
     */
    void onAlertaActivada(Alerta alerta);

    /**
     * Se invoca cuando una alerta es desactivada
     * @param alerta La alerta que fue desactivada
     */
    void onAlertaDesactivada(Alerta alerta);
}