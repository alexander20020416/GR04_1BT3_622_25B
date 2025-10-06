package com.gr4.strategy;

import com.gr4.model.Tarea;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Estrategia de ordenamiento por prioridad
 * Ordena las tareas de mayor a menor prioridad (Alta > Media > Baja)
 */
public class OrdenPorPrioridad implements OrdenStrategy {

    @Override
    public List<Tarea> ordenar(List<Tarea> tareas) {
        return tareas.stream()
                .sorted(new Comparator<Tarea>() {
                    @Override
                    public int compare(Tarea t1, Tarea t2) {
                        return getPrioridadValor(t2.getPrioridad()) - getPrioridadValor(t1.getPrioridad());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getNombre() {
        return "Orden por Prioridad";
    }

    /**
     * Convierte la prioridad textual a un valor numérico para comparación
     * @param prioridad Prioridad textual (Alta, Media, Baja)
     * @return Valor numérico (3=Alta, 2=Media, 1=Baja)
     */
    private int getPrioridadValor(String prioridad) {
        if (prioridad == null) return 0;
        switch (prioridad.toLowerCase()) {
            case "alta":
                return 3;
            case "media":
                return 2;
            case "baja":
                return 1;
            default:
                return 0;
        }
    }
}