package com.hermandadproject.gestionrecorridos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.hermandadproject.gestionrecorridos"
})
@EntityScan(basePackages = {
        "com.hermandadproject.gestionrecorridos.model.entity",
        "com.hermandadproject.gestionciudad.model.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.hermandadproject.gestionrecorridos.repository",
        "com.hermandadproject.gestionciudad.repository"
})
public class GestionRecorridosApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionRecorridosApplication.class, args);
    }
}
