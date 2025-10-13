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
                .sorted(new Comparator<Tarea>() {
                    @Override
                    public int compare(Tarea t1, Tarea t2) {
                        if (t1.getTitulo() == null && t2.getTitulo() == null) {
                            return 0;
                        }
                        if (t1.getTitulo() == null) {
                            return 1;
                        }
                        if (t2.getTitulo() == null) {
                            return -1;
                        }
                        return t1.getTitulo().compareToIgnoreCase(t2.getTitulo());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getNombre() {
        return "Orden Alfabético";
    }
}