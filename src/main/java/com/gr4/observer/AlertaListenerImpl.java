package com.gr4.observer;

import com.gr4.model.Alerta;

/**
 * Implementación del Observer para Alertas
 * En una aplicación real, aquí se enviarían notificaciones por email, SMS, etc.
 * Para este proyecto, solo registramos en consola
 */
public class AlertaListenerImpl implements AlertaListener {

    @Override
    public void onAlertaCreada(Alerta alerta) {
        System.out.println("════════════════════════════════════════");
        System.out.println("✓ ALERTA CREADA EXITOSAMENTE");
        System.out.println("  ID: " + alerta.getId());
        System.out.println("  Mensaje: " + alerta.getMensaje());
        System.out.println("  Fecha: " + alerta.getFechaAlerta());
        System.out.println("  Tipo: " + alerta.getTipo());
        System.out.println("════════════════════════════════════════");

        // Aquí podrías agregar lógica adicional como:
        // - Enviar email
        // - Enviar notificación push
        // - Registrar en un log
        // - Actualizar caché
    }

    @Override
    public void onAlertaActivada(Alerta alerta) {
        System.out.println("════════════════════════════════════════");
        System.out.println("⚠️  ALERTA ACTIVADA");
        System.out.println("  Mensaje: " + alerta.getMensaje());
        System.out.println("  Es momento de: " + alerta.getActividad().getTitulo());
        System.out.println("════════════════════════════════════════");

        // Lógica para disparar la notificación real al usuario
    }

    @Override
    public void onAlertaDesactivada(Alerta alerta) {
        System.out.println("════════════════════════════════════════");
        System.out.println("✗ ALERTA DESACTIVADA");
        System.out.println("  ID: " + alerta.getId());
        System.out.println("  Mensaje: " + alerta.getMensaje());
        System.out.println("════════════════════════════════════════");
    }
}