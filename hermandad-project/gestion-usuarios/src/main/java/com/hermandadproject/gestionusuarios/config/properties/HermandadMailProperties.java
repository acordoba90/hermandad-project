package com.hermandadproject.gestionusuarios.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Agrupa la configuracion funcional de correo del microservicio de usuarios.
 * Las propiedades se leen con el prefijo {@code hermandad.mail} desde Spring
 * Cloud Config o desde la configuracion local equivalente.
 */
@ConfigurationProperties(prefix = "hermandad.mail")
public class HermandadMailProperties {

    private String from;
    private String activationUrl;
    private String resetPasswordUrl;
    private String supportEmail;

    /**
     * Devuelve el remitente visible de los correos enviados por la aplicacion.
     *
     * @return remitente configurado para los mensajes.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Actualiza el remitente visible de los correos enviados por la aplicacion.
     *
     * @param from remitente configurado para los mensajes.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Devuelve la URL publica usada para activar cuentas de usuario.
     *
     * @return URL de activacion de cuenta.
     */
    public String getActivationUrl() {
        return activationUrl;
    }

    /**
     * Actualiza la URL publica usada para activar cuentas de usuario.
     *
     * @param activationUrl URL de activacion de cuenta.
     */
    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }

    /**
     * Devuelve la URL publica usada para restablecer contrasenas.
     *
     * @return URL de restablecimiento de contrasena.
     */
    public String getResetPasswordUrl() {
        return resetPasswordUrl;
    }

    /**
     * Actualiza la URL publica usada para restablecer contrasenas.
     *
     * @param resetPasswordUrl URL de restablecimiento de contrasena.
     */
    public void setResetPasswordUrl(String resetPasswordUrl) {
        this.resetPasswordUrl = resetPasswordUrl;
    }

    /**
     * Devuelve el correo publico de soporte.
     *
     * @return direccion de soporte para usuarios.
     */
    public String getSupportEmail() {
        return supportEmail;
    }

    /**
     * Actualiza el correo publico de soporte.
     *
     * @param supportEmail direccion de soporte para usuarios.
     */
    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }
}
