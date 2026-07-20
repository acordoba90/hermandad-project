# API Gateway

Punto de entrada HTTP único de Hermandad Project. Escucha en `8080`, obtiene sus rutas de Spring Cloud Config y reenvía las rutas `/api/**` sin reescritura.

## Arranque local

1. Arrancar Config Server en `http://localhost:8888`.
2. Configurar las variables de los servicios si no se usan los valores locales.
3. Ejecutar `mvn -pl api-gateway spring-boot:run` desde el proyecto padre.

Variables disponibles: `CONFIG_SERVER_URL`, `SPRING_PROFILES_ACTIVE`, `API_GATEWAY_PORT`, `CORS_ALLOWED_ORIGINS`, `GESTION_USUARIOS_URL`, `GESTION_PERSONAJES_URL`, `GESTION_HERMANDADES_URL`, `GESTION_INVENTARIO_URL`, `GESTION_RECOMPENSAS_URL`, `GESTION_RECORRIDOS_URL`, `GESTION_CIUDAD_URL` y `GESTION_PASOS_URL`. Solo usuarios (8081) y personajes (8084) tienen un puerto local documentado; para el resto, las variables `GESTION_*_URL` son obligatorias al ejecutar todos los servicios localmente para evitar colisiones con el puerto Spring predeterminado.

El estado se consulta en `GET http://localhost:8080/actuator/health`.

## Docker

La imagen utiliza una compilación multi-stage, por lo que no necesita un JAR creado previamente. El fichero `docker-compose.gateway.yml` conecta el Gateway a la red `hermandad-project_default` del Config Server y crea `hermandad-network` para los microservicios dockerizados. Arrancar con `docker compose -f docker-compose.gateway.yml up --build`.

En el entorno híbrido actual, donde `gestion-usuarios` y `gestion-personajes` se ejecutan desde IntelliJ, sus valores predeterminados son `http://host.docker.internal:8081` y `http://host.docker.internal:8084`. Si los servicios también se dockerizan, deben sobrescribirse con `GESTION_USUARIOS_URL=http://gestion-usuarios:8081` y `GESTION_PERSONAJES_URL=http://gestion-personajes:8084`, además de conectarlos a `hermandad-network`. Ningún contenedor debe usar `localhost` para comunicarse con otro proceso.

Para añadir una ruta, incorpora su URI configurable y su predicado `Path` en `api-gateway.properties`, sin cambiar la ruta pública salvo necesidad documentada.
