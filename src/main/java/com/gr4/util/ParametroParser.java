package com.gr4.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParametroParser {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Parsea una cadena a LocalDate de forma segura
     * @param fechaStr cadena con la fecha en formato ISO (yyyy-MM-dd)
     * @return LocalDate parseado
     * @throws IllegalArgumentException si la fecha es null, vacía o formato inválido
     */
    public static LocalDate parseFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }

        try {
            return LocalDate.parse(fechaStr.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de fecha inválido. Use el formato: yyyy-MM-dd. Recibido: " + fechaStr,
                    e
            );
        }
    }

    /**
     * Parsea un String a Long de forma segura
     * @param numeroStr cadena con el número
     * @param nombreParametro nombre del parámetro (para mensajes de error)
     * @return Long parseado
     * @throws IllegalArgumentException si el String no es un número válido
     */
    public static Long parseNumero(String numeroStr, String nombreParametro) {
        if (numeroStr == null || numeroStr.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreParametro + " no puede estar vacío");
        }

        try {
            return Long.parseLong(numeroStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    nombreParametro + " debe ser un número válido. Recibido: " + numeroStr,
                    e
            );
        }
    }
}