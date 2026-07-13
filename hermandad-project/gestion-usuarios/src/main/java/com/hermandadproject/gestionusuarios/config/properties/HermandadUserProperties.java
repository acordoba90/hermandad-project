package com.hermandadproject.gestionusuarios.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Agrupa las politicas configurables del dominio de usuarios.
 * Las propiedades se leen con el prefijo {@code hermandad.user} y cubren
 * activacion, bloqueo de login, inactividad, contrasenas y registro.
 */
@ConfigurationProperties(prefix = "hermandad.user")
public class HermandadUserProperties {

    private final Activation activation = new Activation();
    private final Login login = new Login();
    private final Inactivity inactivity = new Inactivity();
    private final Password password = new Password();
    private final Registration registration = new Registration();

    /**
     * Devuelve la configuracion de activacion de cuenta.
     *
     * @return propiedades de activacion.
     */
    public Activation getActivation() {
        return activation;
    }

    /**
     * Devuelve la configuracion de intentos y bloqueo de login.
     *
     * @return propiedades de login.
     */
    public Login getLogin() {
        return login;
    }

    /**
     * Devuelve la configuracion de desactivacion por inactividad.
     *
     * @return propiedades de inactividad.
     */
    public Inactivity getInactivity() {
        return inactivity;
    }

    /**
     * Devuelve la politica de contrasenas.
     *
     * @return propiedades de contrasena.
     */
    public Password getPassword() {
        return password;
    }

    /**
     * Devuelve la configuracion de validacion de registro.
     *
     * @return propiedades de registro.
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * Propiedades que controlan la caducidad de los tokens de activacion.
     */
    public static class Activation {

        private long expirationHours;

        /**
         * Devuelve las horas de validez de un token de activacion.
         *
         * @return horas de expiracion del token.
         */
        public long getExpirationHours() {
            return expirationHours;
        }

        /**
         * Actualiza las horas de validez de un token de activacion.
         *
         * @param expirationHours horas de expiracion del token.
         */
        public void setExpirationHours(long expirationHours) {
            this.expirationHours = expirationHours;
        }
    }

    /**
     * Propiedades que controlan los intentos fallidos y el bloqueo temporal.
     */
    public static class Login {

        private int maxFailedAttempts;
        private long lockMinutes;

        /**
         * Devuelve el numero maximo de intentos fallidos permitidos.
         *
         * @return intentos fallidos antes del bloqueo.
         */
        public int getMaxFailedAttempts() {
            return maxFailedAttempts;
        }

        /**
         * Actualiza el numero maximo de intentos fallidos permitidos.
         *
         * @param maxFailedAttempts intentos fallidos antes del bloqueo.
         */
        public void setMaxFailedAttempts(int maxFailedAttempts) {
            this.maxFailedAttempts = maxFailedAttempts;
        }

        /**
         * Devuelve los minutos que dura el bloqueo temporal.
         *
         * @return minutos de bloqueo.
         */
        public long getLockMinutes() {
            return lockMinutes;
        }

        /**
         * Actualiza los minutos que dura el bloqueo temporal.
         *
         * @param lockMinutes minutos de bloqueo.
         */
        public void setLockMinutes(long lockMinutes) {
            this.lockMinutes = lockMinutes;
        }
    }

    /**
     * Propiedades que controlan la desactivacion de cuentas inactivas.
     */
    public static class Inactivity {

        private int disableAfterDays;

        /**
         * Devuelve los dias de inactividad tras los que puede desactivarse una cuenta.
         *
         * @return dias de inactividad configurados.
         */
        public int getDisableAfterDays() {
            return disableAfterDays;
        }

        /**
         * Actualiza los dias de inactividad tras los que puede desactivarse una cuenta.
         *
         * @param disableAfterDays dias de inactividad configurados.
         */
        public void setDisableAfterDays(int disableAfterDays) {
            this.disableAfterDays = disableAfterDays;
        }
    }

    /**
     * Propiedades que describen los requisitos minimos de contrasena.
     */
    public static class Password {

        private int minLength;
        private boolean requireUppercase;
        private boolean requireNumber;
        private boolean requireSpecialCharacter;

        /**
         * Devuelve la longitud minima de contrasena.
         *
         * @return longitud minima configurada.
         */
        public int getMinLength() {
            return minLength;
        }

        /**
         * Actualiza la longitud minima de contrasena.
         *
         * @param minLength longitud minima configurada.
         */
        public void setMinLength(int minLength) {
            this.minLength = minLength;
        }

        /**
         * Indica si la contrasena debe incluir una letra mayuscula.
         *
         * @return {@code true} si se exige mayuscula.
         */
        public boolean isRequireUppercase() {
            return requireUppercase;
        }

        /**
         * Actualiza si la contrasena debe incluir una letra mayuscula.
         *
         * @param requireUppercase {@code true} si se exige mayuscula.
         */
        public void setRequireUppercase(boolean requireUppercase) {
            this.requireUppercase = requireUppercase;
        }

        /**
         * Indica si la contrasena debe incluir un numero.
         *
         * @return {@code true} si se exige numero.
         */
        public boolean isRequireNumber() {
            return requireNumber;
        }

        /**
         * Actualiza si la contrasena debe incluir un numero.
         *
         * @param requireNumber {@code true} si se exige numero.
         */
        public void setRequireNumber(boolean requireNumber) {
            this.requireNumber = requireNumber;
        }

        /**
         * Indica si la contrasena debe incluir un caracter especial.
         *
         * @return {@code true} si se exige caracter especial.
         */
        public boolean isRequireSpecialCharacter() {
            return requireSpecialCharacter;
        }

        /**
         * Actualiza si la contrasena debe incluir un caracter especial.
         *
         * @param requireSpecialCharacter {@code true} si se exige caracter especial.
         */
        public void setRequireSpecialCharacter(boolean requireSpecialCharacter) {
            this.requireSpecialCharacter = requireSpecialCharacter;
        }
    }

    /**
     * Propiedades que controlan limites de campos durante el registro.
     */
    public static class Registration {

        private int aliasMinLength;
        private int aliasMaxLength;
        private int usernameMaxLength;

        /**
         * Devuelve la longitud minima del alias de usuario.
         *
         * @return longitud minima del alias.
         */
        public int getAliasMinLength() {
            return aliasMinLength;
        }

        /**
         * Actualiza la longitud minima del alias de usuario.
         *
         * @param aliasMinLength longitud minima del alias.
         */
        public void setAliasMinLength(int aliasMinLength) {
            this.aliasMinLength = aliasMinLength;
        }

        /**
         * Devuelve la longitud maxima del alias de usuario.
         *
         * @return longitud maxima del alias.
         */
        public int getAliasMaxLength() {
            return aliasMaxLength;
        }

        /**
         * Actualiza la longitud maxima del alias de usuario.
         *
         * @param aliasMaxLength longitud maxima del alias.
         */
        public void setAliasMaxLength(int aliasMaxLength) {
            this.aliasMaxLength = aliasMaxLength;
        }

        /**
         * Devuelve la longitud maxima del nombre de usuario.
         *
         * @return longitud maxima del nombre de usuario.
         */
        public int getUsernameMaxLength() {
            return usernameMaxLength;
        }

        /**
         * Actualiza la longitud maxima del nombre de usuario.
         *
         * @param usernameMaxLength longitud maxima del nombre de usuario.
         */
        public void setUsernameMaxLength(int usernameMaxLength) {
            this.usernameMaxLength = usernameMaxLength;
        }
    }
}
