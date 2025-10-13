package com.gr4.strategy;

import com.gr4.model.Tarea;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Estrategia de ordenamiento por fecha de vencimiento
 * Ordena las tareas de más próxima a más lejana
 */
public class OrdenPorFecha implements OrdenStrategy {

    @Override
    public List<Tarea> ordenar(List<Tarea> tareas) {
        return tareas.stream()
                .sorted(new Comparator<Tarea>() {
                    @Override
                    public int compare(Tarea t1, Tarea t2) {
                        // Tareas sin fecha van al final
                        if (t1.getFechaVencimiento() == null && t2.getFechaVencimiento() == null) {
                            return 0;
                        }
                        if (t1.getFechaVencimiento() == null) {
                            return 1;
                        }
                        if (t2.getFechaVencimiento() == null) {
                            return -1;
                        }
                        return t1.getFechaVencimiento().compareTo(t2.getFechaVencimiento());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getNombre() {
        return "Orden por Fecha de Vencimiento";
    }
}