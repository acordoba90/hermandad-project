# Hermandad Project

Repositorio de trabajo para el backend, frontend e infraestructura de configuracion centralizada de Hermandad Project.

## Estructura

```text
IdeaProjects/
├── hermandad-project/
├── hermandad-proyect-frontend/
├── hermandad-config-server/
├── hermandad-project-config/
├── docker/
├── docs/
└── README.md
```

El directorio de frontend existente se mantiene como `hermandad-proyect-frontend` porque esa es la ruta presente en el workspace.

## Spring Cloud Config Server

`hermandad-config-server` es una aplicacion Spring Boot independiente con Spring Cloud Config Server. Escucha en el puerto `8888` y lee las propiedades desde un repositorio Git externo.

Configurar la URL del repositorio de configuracion:

```powershell
$env:CONFIG_REPO_URI="https://github.com/usuario/hermandad-project-config"
```

Arrancar el servidor:

```powershell
cd hermandad-config-server
mvn spring-boot:run
```

No se debe hardcodear la URL real del repositorio en `application.properties`; siempre debe llegar por `CONFIG_REPO_URI`.

## Repositorio de configuracion

`hermandad-project-config` contiene solo ficheros `.properties` centralizados:

- `application.properties`: propiedades comunes para todos los servicios, zona horaria, Jackson, logging y endpoints de Actuator.
- `gestion-usuarios.properties`: base de base de datos, JPA, Liquibase, correo y politicas de usuario.
- `gestion-usuarios-dev.properties`: overrides de desarrollo para usuarios.
- `gestion-usuarios-prod.properties`: overrides de produccion para usuarios.
- `gestion-hermandades*.properties`: configuracion inicial de gestion de hermandades.
- `gestion-partidas*.properties`: configuracion inicial de gestion de partidas.
- `gestion-inventario*.properties`: configuracion inicial de gestion de inventario.
- `gestion-recompensas*.properties`: configuracion inicial de gestion de recompensas.
- `gestion-recorridos*.properties`: configuracion inicial de gestion de recorridos.
- `gestion-personajes*.properties`: configuracion inicial de gestion de personajes.
- `gestion-eventos*.properties`: configuracion inicial de gestion de eventos.
- `frontend*.properties`: configuracion publica para el frontend.

Los ficheros `frontend*.properties` no deben contener contrasenas, tokens privados, client secrets, SMTP, datos de base de datos ni ningun otro secreto.

## gestion-usuarios con Config Server

`gestion-usuarios` queda configurado como cliente de Spring Cloud Config con:

```properties
spring.application.name=gestion-usuarios
spring.config.import=optional:configserver:http://localhost:8888
spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}
```

Arranque recomendado:

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
cd hermandad-project\gestion-usuarios
mvn spring-boot:run
```

El Config Server debe estar arrancado antes si se quiere cargar la configuracion centralizada. La importacion es `optional` para permitir arranques locales controlados durante desarrollo.

## Variables de entorno

Ejemplo para desarrollo local:

```powershell
$env:CONFIG_REPO_URI="https://github.com/usuario/hermandad-project-config"
$env:DB_URL="jdbc:mysql://localhost:3306/hermandad_usuarios"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="password"
$env:MAIL_HOST="smtp-relay.brevo.com"
$env:MAIL_PORT="587"
$env:MAIL_USERNAME="usuario"
$env:MAIL_PASSWORD="password"
$env:SPRING_PROFILES_ACTIVE="dev"
```

Para el resto de servicios se han preparado nombres especificos como `GESTION_HERMANDADES_DB_URL`, `GESTION_PARTIDAS_DB_URL`, `GESTION_INVENTARIO_DB_URL`, `GESTION_RECOMPENSAS_DB_URL`, `GESTION_RECORRIDOS_DB_URL`, `GESTION_PERSONAJES_DB_URL` y `GESTION_EVENTOS_DB_URL`, junto con sus variantes `_DB_USERNAME` y `_DB_PASSWORD`.

## Anadir un nuevo microservicio

1. Definir `spring.application.name` en el microservicio.
2. Anadir Spring Cloud Config Client al POM del microservicio.
3. Configurar `spring.config.import=optional:configserver:http://localhost:8888`.
4. Crear en `hermandad-project-config` los ficheros `<nombre-servicio>.properties`, `<nombre-servicio>-dev.properties` y `<nombre-servicio>-prod.properties`.
5. Usar variables de entorno para credenciales, URLs privadas y secretos.
6. Crear clases `@ConfigurationProperties` cuando haya propiedades propias del dominio.

## Seguridad

No subir secretos reales a GitHub. Estan ignorados `.env`, `*.env`, `application-local.properties`, `application-secret.properties` y `application-secrets.properties`, pero la responsabilidad principal es mantener credenciales y tokens fuera de los ficheros versionados.
