package com.gr4.strategy;

import com.gr4.model.Tarea;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Estrategia de ordenamiento alfabético por título
 * Ordena las tareas alfabéticamente de A a Z
 */
public class OrdenPorTitulo implements OrdenStrategy {

    @Override
    public List<Tarea> ordenar(List<Tarea> tareas) {
        return tareas.stream()
                .sorted(Comparator.comparing(
                        Tarea::getTitulo,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                ))
                .toList();
    }

    @Override
    public String getNombre() {
        return "Orden Alfabético";
    }
}