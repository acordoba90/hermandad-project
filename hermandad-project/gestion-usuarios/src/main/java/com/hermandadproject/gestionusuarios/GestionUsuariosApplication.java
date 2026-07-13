package com.hermandadproject.gestionusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Aplicacion principal del microservicio de gestion de usuarios.
 * Activa el escaneo de clases de configuracion tipada para consumir las
 * propiedades centralizadas servidas por Spring Cloud Config.
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class GestionUsuariosApplication {

    /**
     * Punto de entrada de la aplicacion Spring Boot.
     *
     * @param args argumentos recibidos desde la linea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(GestionUsuariosApplication.class, args);
    }
}
