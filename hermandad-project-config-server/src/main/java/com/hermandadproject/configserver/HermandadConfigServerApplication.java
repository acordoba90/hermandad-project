package com.hermandadproject.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Aplicacion principal del servidor de configuracion de Hermandad Project.
 * Expone Spring Cloud Config Server para servir propiedades centralizadas desde
 * un repositorio Git externo definido por variable de entorno.
 */
@EnableConfigServer
@SpringBootApplication
public class HermandadConfigServerApplication {

    /**
     * Punto de entrada de la aplicacion Spring Boot.
     *
     * @param args argumentos recibidos desde la linea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(HermandadConfigServerApplication.class, args);
    }
}
