# ==========================================================
# ETAPA 1: Compilación del proyecto con Maven y Java 21
# ==========================================================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copiamos primero el pom para aprovechar la caché de dependencias.
COPY pom.xml .

# Descarga las dependencias antes de copiar el código fuente.
RUN mvn -B -ntp dependency:go-offline

# Copiamos el código fuente.
COPY src ./src

# Compilamos el Config Server.
RUN mvn -B -ntp clean package -DskipTests

# Localizamos el JAR ejecutable y lo copiamos con un nombre conocido.
RUN JAR_FILE="$(find target \
        -maxdepth 1 \
        -type f \
        -name '*.jar' \
        ! -name '*-sources.jar' \
        ! -name '*-javadoc.jar' \
        ! -name '*.original' \
        | head -n 1)" \
    && test -n "$JAR_FILE" \
    && cp "$JAR_FILE" /workspace/app.jar


# ==========================================================
# ETAPA 2: Ejecución con Java 21
# ==========================================================
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instalamos curl para poder usarlo en el healthcheck de Docker Compose.
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /workspace/app.jar /app/app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app/app.jar"]