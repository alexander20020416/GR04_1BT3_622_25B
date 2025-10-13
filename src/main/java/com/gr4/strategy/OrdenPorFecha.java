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
                .sorted(Comparator.comparing(
                        Tarea::getFechaVencimiento,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .toList();
    }

    @Override
    public String getNombre() {
        return "Orden por Fecha de Vencimiento";
    }
}