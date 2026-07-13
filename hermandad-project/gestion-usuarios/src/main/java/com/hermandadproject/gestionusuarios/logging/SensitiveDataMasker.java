package com.hermandadproject.gestionusuarios.logging;

/**
 * Utilidad centralizada para registrar datos potencialmente sensibles sin
 * exponer su valor completo en los logs.
 */
public final class SensitiveDataMasker {

    private static final String MASKED_NULL_VALUE = "null";
    private static final String MASKED_INVALID_EMAIL = "********";

    private SensitiveDataMasker() {
    }

    /**
     * Enmascara una direccion de correo conservando solo informacion minima
     * para diagnostico. Gestiona valores nulos, vacios e invalidos sin lanzar
     * excepciones ni mostrar el correo completo.
     *
     * @param email correo original.
     * @return correo enmascarado y seguro para logs.
     */
    public static String maskEmail(String email) {
        if (email == null) {
            return MASKED_NULL_VALUE;
        }

        String trimmedEmail = email.trim();
        int atIndex = trimmedEmail.indexOf('@');
        if (trimmedEmail.isBlank() || atIndex <= 0 || atIndex != trimmedEmail.lastIndexOf('@')
                || atIndex == trimmedEmail.length() - 1) {
            return MASKED_INVALID_EMAIL;
        }

        String localPart = trimmedEmail.substring(0, atIndex);
        String domain = trimmedEmail.substring(atIndex);
        if (localPart.length() == 1) {
            return localPart.charAt(0) + "****" + domain;
        }

        return localPart.charAt(0)
                + "*".repeat(Math.max(localPart.length() - 2, 1))
                + localPart.charAt(localPart.length() - 1)
                + domain;
    }
}
