package com.hermandadproject.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada único para las peticiones HTTP dirigidas a los servicios de Hermandad Project.
 */
@SpringBootApplication
public class ApiGatewayApplication {

    /**
     * Inicia el API Gateway reactivo.
     *
     * @param args argumentos de arranque
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
