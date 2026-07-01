package com.hermandadproject.gestionrecompensas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.hermandadproject.gestionrecompensas",
        "com.hermandadproject.gestioninventario.service",
        "com.hermandadproject.gestioninventario.mapper"
})
@EntityScan(basePackages = {
        "com.hermandadproject.gestionrecompensas.model.entity",
        "com.hermandadproject.gestioninventario.model.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.hermandadproject.gestionrecompensas.repository",
        "com.hermandadproject.gestioninventario.repository"
})
public class GestionRecompensasApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionRecompensasApplication.class, args);
    }
}
