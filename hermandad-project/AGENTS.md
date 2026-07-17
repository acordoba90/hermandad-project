# AGENTS.md — Backend de Hermandad Project

## 1. Objetivo

Este repositorio contiene el backend de **Hermandad Project**, un videojuego de gestión de hermandades y cofradías desarrollado mediante servicios Spring Boot.

Codex debe realizar cambios respetando la arquitectura existente, las convenciones de cada servicio y las decisiones técnicas recogidas en este documento.

Antes de modificar código:

1. Analiza la estructura del servicio afectado.
2. Revisa implementaciones similares existentes.
3. Identifica las clases, configuraciones y changelogs relacionados.
4. Evita crear estructuras paralelas o duplicar funcionalidades.
5. Limita los cambios al alcance solicitado.

---

## 2. Tecnologías principales

El backend utiliza principalmente:

* Java 21.
* Spring Boot.
* Spring Web.
* Spring Data JPA.
* Hibernate.
* Spring Validation.
* Spring Security.
* Spring Cloud Config.
* Liquibase.
* MySQL.
* Maven.
* Docker.

Antes de utilizar una funcionalidad propia de una versión concreta, comprueba la versión declarada en el `pom.xml` del servicio.

No actualices versiones de Java, Spring Boot, dependencias o plugins salvo que se solicite expresamente.

---

## 3. Estructura del proyecto

El backend está compuesto por varios servicios, entre ellos:

* `gestion-usuarios`
* `gestion-hermandades`
* `gestion-personajes`
* `gestion-inventario`
* `gestion-recompensa`
* `gestion-recorridos`

Cada servicio puede tener convenciones propias. Antes de implementar cambios, revisa:

* Paquetes existentes.
* Nombres de clases.
* Interfaces de servicio.
* DTOs.
* Converters o mappers.
* Repositorios.
* Excepciones.
* Controladores.
* Changelogs de Liquibase.
* Pruebas existentes.

No asumas que todos los servicios tienen exactamente la misma estructura.

---

## 4. Principios generales de implementación

### 4.1. Respetar el código existente

* Mantén el estilo y las convenciones existentes.
* Reutiliza utilidades, excepciones y componentes ya disponibles.
* No introduzcas una segunda forma de resolver un problema que ya está resuelto en el servicio.
* No renombres clases, métodos, endpoints, tablas o columnas fuera del alcance solicitado.
* No realices refactorizaciones generales no relacionadas con la tarea.
* No elimines código aparentemente no utilizado sin comprobar previamente su función.

### 4.2. Cambios mínimos y coherentes

Realiza únicamente los cambios necesarios para completar la petición.

Cuando una modificación afecte a varias capas, actualiza todas las que correspondan:

1. Entidad JPA.
2. DTO de entrada.
3. DTO de salida.
4. Converter o mapper.
5. Repositorio.
6. Servicio.
7. Implementación del servicio.
8. Controlador.
9. Validaciones.
10. Gestión de errores.
11. Liquibase.
12. Pruebas.

No es obligatorio modificar todas las capas en cada tarea. Solo deben tocarse las que realmente estén afectadas.

---

## 5. Convenciones Java

### 5.1. Código

* Utiliza nombres descriptivos en castellano cuando el módulo ya siga esa convención.
* Mantén los nombres técnicos estándar en inglés cuando pertenezcan al framework o sean convenciones ampliamente aceptadas.
* Evita abreviaturas ambiguas.
* Utiliza tipos concretos y evita `Object` cuando pueda definirse un tipo específico.
* Evita métodos excesivamente largos.
* Extrae métodos privados cuando mejoren la legibilidad.
* Evita lógica de negocio dentro de los controladores.
* Evita lógica de acceso a datos fuera de repositorios o servicios especializados.
* No utilices campos públicos.
* Prioriza la inyección por constructor.
* Evita la inyección mediante `@Autowired` en atributos.
* No utilices variables estáticas mutables.
* Gestiona correctamente los valores nulos.
* No utilices `Optional` como atributo de una entidad o DTO.
* No captures `Exception` de forma genérica salvo que exista una justificación clara.

### 5.2. Lombok

Antes de utilizar Lombok, comprueba si el servicio ya lo emplea.

* No añadas Lombok como nueva dependencia sin que se solicite.
* Si ya se utiliza, mantén las anotaciones coherentes con el resto del servicio.
* Evita `@Data` en entidades JPA cuando pueda generar problemas con `equals`, `hashCode` o relaciones bidireccionales.

### 5.3. Fechas

* Utiliza las clases de `java.time`.
* Prioriza `LocalDate`, `LocalDateTime`, `Instant` u `OffsetDateTime` según el uso existente.
* No introduzcas `java.util.Date` en código nuevo.
* Mantén una estrategia coherente para las zonas horarias.
* No conviertas fechas a texto antes de tiempo.

---

## 6. Documentación del código

Todo código generado debe quedar adecuadamente documentado.

### 6.1. JavaDoc

Añade JavaDoc a:

* Clases públicas.
* Interfaces públicas.
* Métodos públicos.
* DTOs cuando su finalidad no sea evidente.
* Excepciones personalizadas.
* Configuraciones relevantes.

La documentación debe explicar:

* La responsabilidad de la clase o método.
* Los parámetros relevantes.
* El valor devuelto.
* Las excepciones que pueda producir.
* Las decisiones funcionales importantes.

No añadas JavaDoc vacío ni comentarios que se limiten a repetir el nombre del método.

### 6.2. Comentarios

Añade comentarios únicamente cuando expliquen:

* Reglas de negocio.
* Decisiones técnicas no evidentes.
* Compatibilidad con sistemas externos.
* Soluciones adoptadas para evitar un problema conocido.
* Comportamientos que puedan parecer incorrectos, pero sean intencionados.

No comentes cada línea del código.

---

## 7. Arquitectura por capas

### 7.1. Controladores

Los controladores deben:

* Exponer la API REST.
* Validar la entrada mediante Bean Validation.
* Delegar la lógica en los servicios.
* Devolver códigos HTTP adecuados.
* No contener lógica de negocio.
* No acceder directamente a repositorios.
* No exponer entidades JPA directamente salvo que el servicio ya siga expresamente ese patrón.

Utiliza DTOs para las entradas y salidas de la API.

### 7.2. Servicios

Los servicios deben:

* Contener la lógica de negocio.
* Coordinar repositorios, converters y servicios externos.
* Aplicar las reglas funcionales.
* Gestionar las transacciones cuando corresponda.
* Lanzar excepciones de negocio específicas.

Utiliza `@Transactional` de manera consciente:

* En operaciones de escritura, cuando sea necesario.
* En operaciones de lectura, utiliza `readOnly = true` cuando resulte apropiado.
* No abras transacciones innecesariamente largas.
* No realices llamadas externas lentas dentro de una transacción salvo que sea imprescindible.

### 7.3. Repositorios

Los repositorios deben:

* Limitarse al acceso a datos.
* Utilizar Spring Data JPA cuando sea suficiente.
* Utilizar consultas derivadas cuando sean legibles.
* Utilizar `@Query` cuando una consulta derivada sea excesivamente compleja.
* Evitar consultas nativas salvo que exista una necesidad real.
* Evitar problemas N+1 mediante una estrategia de carga adecuada.

No implementes reglas de negocio en repositorios.

### 7.4. Converters y mappers

Cuando el servicio utilice converters:

* Mantén separados los modelos de persistencia y los modelos de API.
* No mezcles conversiones complejas dentro de controladores.
* Gestiona explícitamente los campos opcionales.
* No sobrescribas campos persistentes con `null` durante actualizaciones parciales.
* Evita ciclos infinitos al convertir relaciones bidireccionales.
* Reutiliza converters existentes.

---

## 8. Entidades JPA

### 8.1. Identificadores

El proyecto utiliza identificadores UUID.

Cuando corresponda:

* Utiliza `UUID` en el modelo Java.
* Respeta la estrategia existente de generación.
* Mantén la representación de base de datos establecida en el servicio.
* En MySQL, los UUID pueden almacenarse como `CHAR(36)` cuando esa sea la convención existente.
* No cambies el tipo de una clave primaria sin una petición expresa.

### 8.2. Relaciones

Al crear relaciones JPA:

* Define correctamente la cardinalidad.
* Configura la columna de unión de manera explícita.
* Evita `FetchType.EAGER` salvo que esté justificado.
* No utilices `CascadeType.ALL` de forma automática.
* Analiza el ciclo de vida real de las entidades antes de aplicar cascadas.
* Evita relaciones bidireccionales cuando no aporten valor.
* Controla los posibles ciclos durante la serialización.

### 8.3. Igualdad y representación

* No incluyas relaciones completas en `toString`.
* Evita ciclos en `equals` y `hashCode`.
* No bases `equals` y `hashCode` en campos mutables.
* No expongas información sensible desde la entidad.

### 8.4. Auditoría

Cuando el servicio ya disponga de campos de auditoría, reutiliza el mecanismo existente:

* `fechaCreacion`
* `fechaModificacion`
* `createdAt`
* `updatedAt`
* Usuario creador o modificador, si existe

No dupliques mecanismos de auditoría.

---

## 9. DTOs y validación

### 9.1. DTOs

Diferencia, cuando sea necesario, entre:

* DTO de creación.
* DTO de actualización.
* DTO de respuesta.
* DTO resumido.
* DTO de filtros.

No reutilices automáticamente un único DTO para todas las operaciones si esto permite modificar campos que deberían ser de solo lectura.

### 9.2. Bean Validation

Utiliza las anotaciones adecuadas:

* `@NotNull`
* `@NotBlank`
* `@Size`
* `@Email`
* `@Positive`
* `@PositiveOrZero`
* `@Min`
* `@Max`
* `@Pattern`

Los mensajes de validación deben ser claros.

No confíes únicamente en la validación del frontend.

Las reglas que dependan del estado de la base de datos o de varias entidades deben validarse en el servicio.

---

## 10. API REST

### 10.1. Endpoints

Mantén los endpoints existentes y sus convenciones.

Al crear nuevos endpoints:

* Utiliza sustantivos para los recursos.
* Evita verbos innecesarios en la URL.
* Utiliza el método HTTP apropiado.
* Mantén una nomenclatura coherente.
* No rompas contratos existentes.
* Versiona la API únicamente si el proyecto ya utiliza versionado.

### 10.2. Métodos HTTP

Utiliza normalmente:

* `GET` para consultas.
* `POST` para creación o acciones que no sean idempotentes.
* `PUT` para sustituciones completas.
* `PATCH` para actualizaciones parciales.
* `DELETE` para eliminaciones.

### 10.3. Códigos HTTP

Devuelve códigos adecuados:

* `200 OK` para operaciones correctas con respuesta.
* `201 Created` para creaciones.
* `204 No Content` para operaciones correctas sin contenido.
* `400 Bad Request` para datos inválidos.
* `401 Unauthorized` cuando falta autenticación.
* `403 Forbidden` cuando no existe autorización.
* `404 Not Found` cuando el recurso no existe.
* `409 Conflict` para conflictos de estado o duplicados.
* `422 Unprocessable Entity` únicamente si el proyecto ya utiliza este criterio.
* `500 Internal Server Error` para errores inesperados.

No devuelvas siempre `200 OK`.

---

## 11. Gestión de errores

* Reutiliza el manejador global de excepciones existente.
* Crea excepciones de negocio específicas cuando sea necesario.
* No expongas trazas, consultas SQL ni información interna al cliente.
* Los mensajes destinados al usuario deben ser comprensibles.
* Los logs pueden contener información técnica adicional.
* No utilices excepciones como flujo normal de control.
* Conserva la causa original cuando encapsules una excepción.

Ejemplos de excepciones funcionales:

* Recurso no encontrado.
* Recurso duplicado.
* Operación no permitida.
* Estado de cuenta inválido.
* Token inválido.
* Token expirado.
* Conflicto de integridad.

---

## 12. Liquibase y base de datos

### 12.1. Reglas generales

Toda modificación estructural de base de datos debe gestionarse mediante Liquibase.

* No modifiques manualmente la base de datos como solución definitiva.
* No utilices `spring.jpa.hibernate.ddl-auto` para crear o actualizar el esquema.
* No modifiques un changeSet ya ejecutado en entornos compartidos.
* Crea un nuevo changeSet para cada modificación.
* Utiliza identificadores únicos y descriptivos.
* Incluye un autor coherente con el proyecto.
* Respeta el orden y la estructura de changelogs existente.
* Añade el nuevo fichero al changelog maestro cuando sea necesario.

### 12.2. Compatibilidad

No utilices funcionalidades exclusivas de Liquibase Commercial.

En particular, comprueba que los cambios utilizados estén soportados por la versión instalada.

Cuando un cambio no esté disponible, utiliza una alternativa compatible, como SQL explícito, siempre que sea portable y esté justificado.

### 12.3. Columnas

Al crear columnas:

* Define el tipo correcto.
* Define expresamente si permiten `NULL`.
* Añade valores por defecto solo cuando exista una regla funcional.
* Mantén la nomenclatura usada por el esquema.
* Añade restricciones `UNIQUE` cuando corresponda.
* Añade claves externas cuando exista una relación persistente.
* Añade índices para columnas utilizadas habitualmente en filtros, relaciones o búsquedas.

### 12.4. Claves externas

Las claves externas deben:

* Tener un nombre explícito.
* Referenciar la tabla y columna correctas.
* Definir una política de borrado consciente.
* Evitar borrados en cascada no solicitados.

### 12.5. Datos iniciales

No insertes datos iniciales salvo que se solicite expresamente.

Cuando la tarea indique que solo deben declararse campos o estructuras, no añadas registros de catálogo, usuarios, personajes ni datos de prueba al changelog.

### 12.6. Rollback

Incluye rollback cuando la estructura de changelogs existente lo contemple o cuando sea razonablemente seguro hacerlo.

No definas un rollback destructivo si puede provocar pérdida de información sin advertirlo.

---

## 13. Configuración

### 13.1. Formato

Hermandad Project utiliza archivos de configuración `.properties`.

Utiliza:

* `application.properties`
* `bootstrap.properties`
* Ficheros remotos `<servicio>.properties`
* Ficheros remotos `<servicio>-<entorno>.properties`

No crees archivos YAML de configuración.

No generes:

* `application.yml`
* `application.yaml`
* `bootstrap.yml`
* `bootstrap.yaml`

### 13.2. Spring Cloud Config

La configuración compartida o dependiente del entorno debe gestionarse, cuando corresponda, mediante Spring Cloud Config.

El repositorio de configuración se encuentra separado del backend:

```text
C:\Users\Antonio\IdeaProjects\hermandad-project-config
```

El servidor de configuración se encuentra en:

```text
C:\Users\Antonio\IdeaProjects\hermandad-project-config-server
```

Antes de añadir una propiedad:

1. Comprueba si pertenece al servicio.
2. Comprueba si depende del entorno.
3. Comprueba si debe estar en el repositorio remoto de configuración.
4. Revisa si ya existe una propiedad equivalente.

No dupliques propiedades locales y remotas sin necesidad.

### 13.3. Información sensible

Nunca incluyas en el repositorio:

* Contraseñas.
* Tokens.
* Secretos de cliente.
* Claves privadas.
* Credenciales SMTP reales.
* Credenciales de base de datos de producción.
* Certificados privados.
* API keys.

Utiliza variables de entorno o mecanismos de secretos.

---

## 14. Seguridad

* Respeta la configuración de Spring Security existente.
* No desactives seguridad globalmente para resolver una incidencia.
* No añadas `permitAll()` a endpoints sensibles sin justificación.
* Mantén una política de mínimo privilegio.
* Valida tanto autenticación como autorización.
* No confíes en identificadores de usuario enviados por el frontend cuando puedan obtenerse del contexto autenticado.
* No registres contraseñas, tokens completos ni credenciales.
* No devuelvas hashes de contraseña.
* No almacenes contraseñas en texto plano.
* Utiliza el codificador de contraseñas existente.
* Los tokens deben tener expiración y validación adecuada.
* Evita revelar si una cuenta existe cuando pueda producir enumeración de usuarios, salvo que la funcionalidad existente determine lo contrario.

Cuando se modifique la seguridad:

1. Revisa el `SecurityFilterChain`.
2. Revisa CORS.
3. Revisa CSRF.
4. Revisa roles y autoridades.
5. Revisa endpoints públicos.
6. Añade pruebas de acceso permitido y denegado.

---

## 15. Correo electrónico

Cuando se modifiquen funcionalidades de correo:

* Reutiliza el servicio de correo existente.
* Mantén las plantillas fuera de la lógica Java cuando esa sea la estructura actual.
* No incluyas URLs fijas en el código.
* Obtén las URLs desde configuración.
* No registres tokens completos.
* Gestiona los errores de envío sin dejar datos persistentes en un estado incoherente.
* Evita enviar correos dentro de una transacción larga cuando pueda producir bloqueos.
* Mantén los mensajes preparados para futuras modificaciones o internacionalización.

---

## 16. Logging

Utiliza el sistema de logging existente, normalmente SLF4J.

Registra:

* Inicio y final de operaciones relevantes.
* Identificadores técnicos necesarios para diagnosticar errores.
* Cambios importantes de estado.
* Errores de integración.
* Excepciones inesperadas.

No registres:

* Contraseñas.
* Hashes de contraseña.
* Tokens completos.
* Cookies.
* Cabeceras de autorización.
* Datos personales innecesarios.
* Cuerpos completos de peticiones sensibles.

Utiliza el nivel adecuado:

* `TRACE`: información extremadamente detallada.
* `DEBUG`: diagnóstico durante desarrollo.
* `INFO`: eventos funcionales importantes.
* `WARN`: situaciones anómalas recuperables.
* `ERROR`: errores que impiden completar la operación.

No utilices `System.out.println`.

---

## 17. Pruebas

Todo cambio debe incluir o actualizar las pruebas necesarias.

### 17.1. Tipos de pruebas

Según el alcance, utiliza:

* Pruebas unitarias de servicios.
* Pruebas de converters.
* Pruebas de validación.
* Pruebas de repositorios.
* Pruebas de controladores.
* Pruebas de integración.
* Pruebas de seguridad.

### 17.2. Principios

* Prueba el comportamiento, no la implementación interna.
* Incluye casos correctos e incorrectos.
* Incluye límites y valores nulos cuando corresponda.
* Comprueba duplicados y conflictos.
* Comprueba recursos inexistentes.
* Comprueba estados no permitidos.
* No dependas de datos externos inestables.
* Evita pruebas que requieran un orden concreto de ejecución.
* Mantén los datos de prueba claros y mínimos.

### 17.3. Verificación final

Antes de concluir una tarea:

1. Compila el servicio afectado.
2. Ejecuta sus pruebas.
3. Revisa el resultado.
4. Corrige imports no utilizados.
5. Comprueba que no haya errores de formato.
6. Comprueba que Liquibase pueda cargar los changelogs.
7. Comprueba que no se hayan añadido secretos.

Si no es posible ejecutar alguna comprobación, indícalo expresamente en el resumen final.

---

## 18. Maven

* Reutiliza las dependencias existentes.
* No añadas una dependencia cuando Java o Spring ya proporcionen la funcionalidad.
* No dupliques dependencias transitivas sin motivo.
* No cambies el alcance de una dependencia sin analizar el impacto.
* No actualices el `parent` ni el BOM salvo petición expresa.
* Mantén el orden y estilo del `pom.xml`.
* Evita dependencias abandonadas o innecesariamente pesadas.

Cuando añadas una dependencia, explica su necesidad en el resumen final.

---

## 19. Integraciones externas

Cuando el servicio consuma APIs externas:

* Mantén los clientes separados de la lógica de negocio.
* Configura URLs y credenciales externamente.
* Define timeouts.
* Gestiona errores HTTP.
* No asumas que la respuesta siempre contiene todos los campos.
* Valida datos externos antes de persistirlos.
* No repitas automáticamente operaciones no idempotentes.
* Mantén trazabilidad sin registrar información sensible.
* Utiliza DTOs específicos para la integración.

No acoples las entidades JPA a los modelos de una API externa.

---

## 20. Docker

Cuando se modifiquen archivos Docker:

* Mantén imágenes base compatibles con la arquitectura actual.
* No incluyas secretos en `Dockerfile` o `docker-compose`.
* Utiliza variables de entorno.
* Mantén los puertos existentes salvo petición expresa.
* Añade o conserva healthchecks cuando correspondan.
* Evita ejecutar el contenedor como `root` cuando pueda configurarse un usuario no privilegiado.
* No copies archivos innecesarios en la imagen.
* Respeta `.dockerignore`.

No cambies simultáneamente la infraestructura de todos los servicios si la tarea afecta solo a uno.

---

## 21. Reglas específicas de Hermandad Project

### 21.1. Dominio

El dominio representa elementos relacionados con hermandades y cofradías, entre ellos:

* Usuarios.
* Estados de usuario.
* Hermandades.
* Tipos de hermandad.
* Personajes.
* Avatares.
* Inventario.
* Recompensas.
* Cartas y sobres.
* Recorridos.
* Pasos procesionales.
* Miembros y cargos.
* Recursos de la hermandad.

Utiliza terminología coherente con el dominio.

No sustituyas términos propios del proyecto por conceptos genéricos si se pierde significado funcional.

### 21.2. Usuarios

En funcionalidades de usuario:

* Comprueba el estado de la cuenta.
* Valida la vigencia de tokens.
* Evita reutilizar tokens expirados.
* Invalida los tokens después de su uso cuando corresponda.
* Gestiona duplicados mediante respuestas de conflicto.
* Mantén separados el registro, activación y restablecimiento de contraseña.
* No expongas `hashContrasena`.
* Conserva correctamente las fechas de creación, actualización y vigencia.

### 21.3. Personajes

En `gestion-personajes`:

* Diferencia entre personajes predefinidos y personalizados.
* Mantén separados el personaje, el avatar y el usuario propietario.
* No insertes personajes por defecto salvo petición expresa.
* Evita almacenar datos derivados cuando puedan calcularse, salvo decisión funcional.
* Valida la propiedad del personaje antes de modificarlo.
* No permitas que un usuario modifique personajes pertenecientes a otro usuario.

### 21.4. Catálogos

Para tablas de catálogo:

* Utiliza códigos estables.
* No utilices nombres visibles como identificadores funcionales.
* Conserva los registros históricos cuando puedan estar referenciados.
* Utiliza un campo de activo o vigencia cuando sea necesario.
* No elimines físicamente valores ya utilizados sin analizar referencias.
* Define un orden explícito cuando el frontend necesite mostrar los elementos ordenados.

---

## 22. Acciones prohibidas sin autorización expresa

Codex no debe:

* Eliminar tablas o columnas.
* Renombrar endpoints públicos.
* Cambiar contratos de API existentes.
* Modificar changelogs ya ejecutados.
* Insertar datos iniciales no solicitados.
* Cambiar de MySQL a otra base de datos.
* Cambiar archivos `.properties` por YAML.
* Actualizar Java o Spring Boot.
* Añadir nuevas tecnologías o frameworks.
* Cambiar el sistema de autenticación.
* Desactivar controles de seguridad.
* Añadir credenciales al código.
* Realizar refactorizaciones masivas.
* Modificar todos los servicios por coherencia si solo uno está afectado.
* Crear código placeholder o métodos sin implementar.
* Dejar bloques `TODO` como sustitución de una implementación solicitada.
* Ignorar errores de compilación o pruebas.
* Sobrescribir trabajo existente del usuario sin revisar los cambios.

---

## 23. Forma de trabajar ante una petición

Para cada tarea, sigue este proceso:

### Paso 1. Comprensión

* Identifica el servicio afectado.
* Identifica el comportamiento actual.
* Identifica el comportamiento esperado.
* Delimita los archivos que probablemente estén relacionados.

### Paso 2. Inspección

* Revisa implementaciones similares.
* Revisa entidades y DTOs relacionados.
* Revisa la configuración.
* Revisa los changelogs.
* Revisa las pruebas.
* Revisa posibles cambios sin confirmar del usuario.

### Paso 3. Implementación

* Realiza cambios pequeños y coherentes.
* Mantén compatibilidad con el código existente.
* Actualiza todas las capas afectadas.
* Añade documentación.
* Añade o actualiza pruebas.

### Paso 4. Validación

* Compila.
* Ejecuta pruebas.
* Revisa Liquibase.
* Revisa seguridad.
* Revisa configuración.
* Revisa que no existan secretos.
* Revisa el diff completo.

### Paso 5. Resumen

Al finalizar, indica:

1. Qué comportamiento se ha implementado.
2. Qué archivos se han creado.
3. Qué archivos se han modificado.
4. Qué decisiones técnicas se han tomado.
5. Qué pruebas se han ejecutado.
6. El resultado de las pruebas.
7. Cualquier limitación o comprobación pendiente.

No afirmes que una prueba se ha ejecutado si no se ha ejecutado realmente.

---

## 24. Criterios de finalización

Una tarea solo se considera finalizada cuando:

* El código solicitado está implementado.
* La arquitectura existente se ha respetado.
* No hay código duplicado innecesario.
* Las entradas están validadas.
* Los errores están gestionados.
* La persistencia está actualizada cuando corresponde.
* Liquibase contiene los cambios necesarios.
* La configuración utiliza `.properties`.
* El código público está documentado.
* Las pruebas relevantes están creadas o actualizadas.
* El servicio compila.
* Las pruebas ejecutadas finalizan correctamente.
* No se han introducido credenciales.
* Se proporciona un resumen claro del trabajo realizado.
